package com.nate.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nate.sumo.DatabaseManager;
import com.nate.sumo.model.basho.Kimarite;
import com.nate.sumo.model.basho.Match;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Record;
import com.nate.sumo.model.common.Weight;
import com.nate.sumo.model.rikishi.Heya;
import com.nate.sumo.model.rikishi.RikishiInfo;
import com.nate.sumo.model.rikishi.RikishiStats;
import com.nate.sumo.model.rikishi.RikishiTemperment;

public class BanzukeBuilder
{
	private static final String MAIN_URL = "http://sumodb.sumogames.de";
	private static final String ENGLISH_QUERY = MAIN_URL + "/Query.aspx?show_form=0&columns=1&rowcount=5&show_sansho=on&showheya=on" + 
			"&showshusshin=on&showbirthdate=on&showhatsu=on&showintai=on&showheight=on&showweight=on&showhighest=on&";
	private static final String BASHO_QUERY = MAIN_URL + "/Rikishi_basho.aspx?";
	
	private static final String YEAR = "form2_year=";
	private static final String MONTH = "form2_month=";
	
	public static void main( String[] args )
	{
		
		if ( args.length < 1 ){
			System.out.println( "At least one basho must be specified in the format:  <year>.<month>" );
			System.exit( 0 );
		}
		
		BanzukeBuilder main = new BanzukeBuilder();
		
		for ( String arg : args ){
			try
			{
				main.buildBanzuke( arg );
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public BanzukeBuilder(){
		
	}
	
	public void buildBanzuke( String basho ) throws IOException{
		
		String[] tokens = basho.split( "\\." );
		
		if ( tokens.length < 2 ){
			System.out.println( "Invalid basho syntax.  Expected <year>.<month> not: " + basho );
			return;
		}
		
		Integer year = -1;
		Integer month = -1;
				
		try {
			year = Integer.parseInt( tokens[0] );
			month = Integer.parseInt( tokens[1] );
		}
		catch ( NumberFormatException e ){
			System.out.println( "Both year and month must be integers.\n" + e.getLocalizedMessage() );
			return;
		}
		
		DatabaseManager.getInstance();
//		String tableName = "APP.BANZUKE_" + year + "_" + month;
//		boolean exists = true;//checkForExistence( tableName );
//				
//		if ( exists == true ){
//			System.out.println( "Banzuke for " + month + "." + year + " already exists.  Do you want to delete it? [y|n]");
//			
//			char answer = (char)System.in.read();
//			
//			if ( answer != 'y' ){
//				return;
//			}
//			else {
//				DatabaseManager.getInstance().execute( "DROP TABLE BANZUKE_" + month + "_" + year );
//			}
//		}
//		
//		createBanzukeTable( tableName );
		
		Document engPage = Jsoup.connect( ENGLISH_QUERY + YEAR + year + "&" + MONTH + month ).get();
		
		Elements enElms = engPage.select( "table.record>tbody>tr" );
		
		for ( int i = 2; i < 3/*enElms.size()*/; i++ ){
			
			Element enTr = enElms.get( i );
			
			Elements enTds = enTr.select( "td" );
			
			String rikishiUrl = enTds.get( 0 ).select( "a" ).get( 0 ).attr( "href" );
			
			Document enPage = Jsoup.connect( MAIN_URL + "/" + rikishiUrl ).get();
			Document jpPage = Jsoup.connect( MAIN_URL + "/" + rikishiUrl + "&l=j" ).get();
			
			Long rikishiId = Long.parseLong( rikishiUrl.split( "=" )[1] );
			
			RikishiInfo rInfo = buildRikishiInfo( rikishiId, enPage, jpPage );
			
			List<String> bashoUrls = new ArrayList<String>();
			
			// how many basho back we want to go
			for ( int j = 0; j < 3; j++ ){
				int tMonth = month - (j*2);
				int tYear = year;
				
				if ( tMonth < 0 ){
					tMonth = 11;
					tYear--;
				}
				
				String bashoUrl = BASHO_QUERY + "r=" + rInfo.getId() + "&b=" + year + "." + month;
				bashoUrls.add( bashoUrl );
			}
			
			List<List<MatchResult>> bashoResults = buildBashoResults( bashoUrls );
			RikishiStats stats = buildRikishiStats( bashoResults );
			RikishiTemperment temperment = buildRikishiTemperment( bashoResults );
		}
	}
	
	private RikishiInfo buildRikishiInfo( Long id, Document enPage, Document jpPage ) throws IOException{
		
		RikishiInfo rinf = new RikishiInfo();
		rinf.setId( id );
		
		Name shikona = new Name();
		Name realName = new Name();
		Location hometown = new Location();
		
		Elements enTrs = enPage.select( "table.rikishidata table.rikishidata tr" );
		Elements jpTrs = jpPage.select( "table.rikishidata table.rikishidata tr" );
		
		// highest rank
		String highRankText = enTrs.get( 0 ).select( "td" ).get( 1 ).text();
		String[] tokens = highRankText.split( " " );
		RankClass rClass = RankClass.valueOf( tokens[0].toUpperCase() );

		if ( !tokens[1].startsWith( "(" ) ){
			Integer rInt = Integer.parseInt( tokens[1] );
			rinf.setHighestRank( new Rank( rClass, rInt ) );
		}
		else {
			rinf.setHighestRank( new Rank( rClass ) );
		}
		
		// real name
		String[] enName = enTrs.get( 1 ).select( "td" ).get( 1 ).text().split( " " );
		String[] jpName = jpTrs.get( 1 ).select( "td" ).get( 1 ).text().split( "\u3000" );
		
		realName.setLastName_en( enName[0].substring( 0, 1 ) + enName[0].toLowerCase().substring( 1 ) );
		realName.setLastName_kanji( jpName[0] );
		
		if ( enName.length > 1 ){
			realName.setFirstName_en( enName[1] );
		}
		
		if ( jpName.length > 1 ){
			realName.setFirstName_kanji( jpName[1] );
		}
		
		rinf.setRealName( realName );
		
		// birthday
		String dString = enTrs.get( 2 ).select( "td" ).get( 1 ).text();
		dString = dString.substring( 0, dString.lastIndexOf( "(" ) - 1 );
		rinf.setBirthday( parseDate( dString ) );
		
		// shusshin
		String enShusshin = enTrs.get( 3 ).select( "td" ).get( 1 ).text();
		String jpShusshin = jpTrs.get( 3 ).select( "td" ).get( 1 ).text();
		String[] enShusshins = enShusshin.split( ", " );
		
		if ( enShusshins.length > 1 ){
			enShusshins[1] = enShusshins[1].replaceAll( " - Mongolia", "" );
		}
		
		Location l = Location.getKnownLocations().get( enShusshins[0] + enShusshins[1] );
		
		rinf.setHometown( l );
		
		// height and weight
		String[] handw = enTrs.get( 4 ).select( "td" ).get( 1 ).text().split( " " );
		rinf.setWeight( new Weight( Integer.parseInt( handw[2] ) ) );
		rinf.setHeight( new Height( Integer.parseInt( handw[0] ) ) );
		
		// heya name
		String heyaKey = enTrs.get( 5 ).select( "td" ).get( 1 ).text();
		
		Heya heya = Heya.getKnownHeya().get( heyaKey );
		rinf.setHeya( heya );
		
		// shikona
		String[] enShik = enTrs.get( 6 ).select( "td" ).get( 1 ).text().split( "\\s+" );
		String[] jpShik = jpTrs.get( 6 ).select( "td" ).get( 1 ).text().split( "\\s+" ); 
				
		if ( jpShik.length < 2 ) {
			jpShik = jpTrs.get( 6 ).select( "td" ).get( 1 ).text().split( "\u3000" );
		}
		
		shikona.setFirstName_en( enShik[0] );
		shikona.setLastName_en( enShik[1] );
		shikona.setFirstName_kanji( jpShik[0] );
		shikona.setLastName_kanji( jpShik[1] );
		
		//the hiragana
		String hiragana = jpPage.select( "table.layout td h2" ).text();
		hiragana = hiragana.substring( hiragana.indexOf("\uff08") + 1, hiragana.lastIndexOf("\uff09") );
		String[] hNames = hiragana.split( "\u3000" );
		shikona.setFirstName_jp( hNames[0] );
		shikona.setLastName_jp( hNames[1] );
		
		rinf.setShikona( shikona );
		
		// hatsu basho
		String[] hatsuBasho = enTrs.get( 7 ).select( "td" ).get( 1 ).text().split( "\\." );
		Calendar c = Calendar.getInstance();
		c.set( Integer.parseInt( hatsuBasho[0] ), Integer.parseInt( hatsuBasho[1] ), 1, 0, 0, 0 );
		rinf.setHatsuBasho( c.getTime() );
		
		// career record
		String[] careerStr = enTrs.get( 9 ).select( "td" ).get( 1 ).text().split( "\\-" );
		Record record = new Record();
		Integer wins = Integer.parseInt( careerStr[0] );
		Integer loses = Integer.parseInt( careerStr[1] );
		Integer forfeits = 0;
		
		if ( careerStr[2].indexOf( "/") != -1 ){
			forfeits = Integer.parseInt( careerStr[2].substring(0, careerStr[2].indexOf("/") ) );
		}
		
		record.setWins( wins );
		record.setLoses( loses );
		record.setForfeits( forfeits );
		
		rinf.setCareerRecord( record );
		
		return rinf;
	}
	
	private List<List<MatchResult>> buildBashoResults( List<String> bashoUrls ){
	
		List<List<MatchResult>> results = new ArrayList<List<MatchResult>>();
		
		for ( String url : bashoUrls ){
			List<MatchResult> matches = new ArrayList<MatchResult>();
			
			try
			{
				Document bashoPage = Jsoup.connect( url ).get();
				Elements rows = bashoPage.select( "table.rb_torikumi tr" );
				
				Iterator<Element> eleIt = rows.iterator();
				
				while( eleIt.hasNext() ){
					Element row = eleIt.next();
					Long opponentId = -1L;
					Rank opponentRank = new Rank( RankClass.JONIDAN, 1 );
					Record opponentRecord = new Record();
					Boolean win = true;
					Kimarite kimarite = Kimarite.OSHIDASHI;
					
					Elements fields = row.select( "td" );
					
					String wlImg = fields.get( 1 ).select( "img" ).get( 0 ).attr( "src" );
					
					if ( wlImg.indexOf( "kuro" ) != -1 ){
						win = false;
					}
					else {
						win = true;
					}
					
					kimarite = Kimarite.valueOf( fields.get( 2 ).text().toUpperCase() );
					
					Element matchInfo = fields.get( 3 ).select( "a" ).get( 0 );
					
					String oppUrl = matchInfo.attr( "href" );
					opponentId = Long.parseLong( oppUrl.substring( oppUrl.indexOf( "=" ) + 1 ) );
					String[] infoBits = matchInfo.text().split( " " );
					opponentRank = Rank.parseRank( infoBits[0] );
					
					String[] recordInfo = fields.get( 3 ).select( "a" ).get( 1 ).text().split( " " );
					String rStr = recordInfo[1];
					rStr = rStr.substring( 1, rStr.length() - 2 );
					String[] nums = rStr.split( "-" );
					
					opponentRecord.setWins( Integer.parseInt( nums[0] ) );
					opponentRecord.setLoses( Integer.parseInt( nums[1] ) );
					
					if ( nums.length > 2 ){
						opponentRecord.setForfeits( Integer.parseInt( nums[2] ) );
					}
					
					MatchResult result = new MatchResult();
					result.setOpponenId( opponentId );
					result.setKimarite( kimarite );
					result.setOpponentRank( opponentRank );
					result.setOpponentRecord( opponentRecord );
					
					matches.add( result );
				}
			}
			catch (IOException e)
			{
				System.out.println( "Page didn't exists: " + url );
				System.out.println( "Assuming this rikishi wans't there - moving on." );
			}
			
			results.add( matches );
		}
		
		return results;
	}
	
	private RikishiStats buildRikishiStats( List<List<MatchResult>> bashoResults ){
		return null;
	}
	
	private RikishiTemperment buildRikishiTemperment( List<List<MatchResult>> bashoResults ){
		return null;
	}
	
	private Date parseDate( String d ){
		
		String month = d.substring( 0, d.indexOf( " " ) );
		String day = d.substring( d.indexOf( " " )+1, d.indexOf( "," ) );
		String year = d.substring( d.lastIndexOf( " " ) + 1 );
		
		Calendar c = Calendar.getInstance();
		c.set( Calendar.MONTH, Month.valueOf( month.toUpperCase() ).getValue() - 1 );
		c.set( Calendar.DAY_OF_MONTH, Integer.parseInt( day ) );
		c.set( Calendar.YEAR, Integer.parseInt( year ) );
		c.set( Calendar.HOUR_OF_DAY, 0 );
		c.set( Calendar.SECOND, 0 );
		c.set( Calendar.MINUTE, 0 );
		c.set( Calendar.MILLISECOND, 0 );
		
		return new Date( c.getTimeInMillis() );
	}
	
	private boolean checkForExistence( String tableName ){
		boolean exists = DatabaseManager.getInstance().tableExists( tableName );
		
		return exists;
	}
	
	private void createBanzukeTable( String tableName ){
		
		BufferedReader reader = null;
		
		try {
			URL ddlUrl = BanzukeBuilder.class.getResource( "/banzuke.ddl" );
			reader = new BufferedReader( new FileReader( ddlUrl.getFile() ) );
			
			String ddl = "";
			String line = null;
			
			while( (line = reader.readLine()) != null ){
				ddl += line;
			}
			

			ddl.replace( "APP.BANZUKE", tableName );
			
			DatabaseManager.getInstance().execute( ddl );
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if ( reader != null ){
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class MatchResult{
		
		private Long opponenId = -1L;
		private Boolean win = false;
		private Record opponentRecord;
		private Kimarite kimarite;
		private Rank opponentRank;
		
		public MatchResult(){}

		public Long getOpponenId()
		{
			return opponenId;
		}

		public void setOpponenId( Long opponenId )
		{
			this.opponenId = opponenId;
		}

		public Boolean getWin()
		{
			return win;
		}

		public void setWin( Boolean win )
		{
			this.win = win;
		}

		public Record getOpponentRecord()
		{
			return opponentRecord;
		}

		public void setOpponentRecord( Record opponentRecord )
		{
			this.opponentRecord = opponentRecord;
		}

		public Kimarite getKimarite()
		{
			return kimarite;
		}

		public void setKimarite( Kimarite kimarite )
		{
			this.kimarite = kimarite;
		}

		public Rank getOpponentRank()
		{
			return opponentRank;
		}

		public void setOpponentRank( Rank opponentRank )
		{
			this.opponentRank = opponentRank;
		}
	}

}
