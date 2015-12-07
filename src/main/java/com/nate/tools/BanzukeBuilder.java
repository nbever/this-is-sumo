package com.nate.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nate.sumo.DatabaseManager;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Weight;
import com.nate.sumo.model.rikishi.Heya;
import com.nate.sumo.model.rikishi.RikishiInfo;

public class BanzukeBuilder
{
	private static final String MAIN_URL = "http://sumodb.sumogames.de";
	private static final String ENGLISH_QUERY = MAIN_URL + "/Query.aspx?show_form=0&columns=1&rowcount=5&show_sansho=on&showheya=on" + 
			"&showshusshin=on&showbirthdate=on&showhatsu=on&showintai=on&showheight=on&showweight=on&showhighest=on&";
	
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
		
		String tableName = "APP.BANZUKE_" + year + "_" + month;
		boolean exists = checkForExistence( tableName );
				
		if ( exists == true ){
			System.out.println( "Banzuke for " + month + "." + year + " already exists.  Do you want to delete it? [y|n]");
			
			char answer = (char)System.in.read();
			
			if ( answer != 'y' ){
				return;
			}
			else {
				DatabaseManager.getInstance().execute( "DROP TABLE BANZUKE_" + month + "_" + year );
			}
		}
		
		createBanzukeTable( tableName );
		
		Document engPage = Jsoup.connect( ENGLISH_QUERY + YEAR + year + "&" + MONTH + month ).get();
		
		Elements enElms = engPage.select( "table.record>tbody>tr" );
		
		for ( int i = 2; i < enElms.size(); i++ ){
			
			Element enTr = enElms.get( i );
			
			Elements enTds = enTr.select( "td" );
			
			String rikishiUrl = enTds.get( 0 ).select( "a" ).get( 0 ).attr( "href" );
			
			RikishiInfo rInfo = buildRikishiInfo( rikishiUrl );
		}
	}
	
	private RikishiInfo buildRikishiInfo( String url ) throws IOException{
		
		RikishiInfo rinf = new RikishiInfo();
		
		Name shikona = new Name();
		Name realName = new Name();
		Location hometown = new Location();
		
		rinf.setId( Long.parseLong( url.split( "=" )[1] ) );
		
		Document enPage = Jsoup.connect( MAIN_URL + "/" + url ).get();
		Document jpPage = Jsoup.connect( MAIN_URL + "/" + url + "&l=j" ).get();
		
		Elements enTrs = enPage.select( "table.rikishidata table.rikishidata tr" );
		Elements jpTrs = jpPage.select( "table.rikishidata table.rikishidata tr" );
		
		// highest rank
		String highRankText = enTrs.get( 0 ).select( "td" ).get( 1 ).text();
		String[] tokens = highRankText.split( " " );
		RankClass rClass = RankClass.valueOf( tokens[0] );

		if ( !tokens[1].startsWith( "(" ) ){
			Integer rInt = Integer.parseInt( tokens[1] );
			rinf.setHighestRank( new Rank( rClass, rInt ) );
		}
		else {
			rinf.setHighestRank( new Rank( rClass ) );
		}
		
		// real name
		String[] enName = enTrs.get( 1 ).select( "td" ).get( 1 ).text().split( " " );
		String[] jpName = jpTrs.get( 1 ).select( "td" ).get( 1 ).text().split( " " );
		
		realName.setFirstName_en( enName[0].substring( 0, 1 ) + enName[0].toLowerCase().substring( 1 ) );
		realName.setFirstName_kanji( jpName[0] );
		
		if ( enName.length > 1 ){
			realName.setLastName_en( enName[1] );
		}
		
		if ( jpName.length > 1 ){
			realName.setLastName_kanji( jpName[1] );
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
		
		// shikona
		String[] enShik = enTrs.get( 6 ).select( "td" ).get( 1 ).text().split( " " );
		String[] jpShik = jpTrs.get( 6 ).select( "td" ).get( 1 ).text().split( " " );
		
		shikona.setFirstName_en( enShik[0] );
		shikona.setLastName_en( enShik[1] );
		shikona.setFirstName_kanji( jpShik[0] );
		shikona.setLastName_kanji( jpShik[1] );
		
		//the hiragana
		String hiragana = jpPage.select( "table.layout td h2" ).text();
		String[] hNames = hiragana.substring( hiragana.indexOf("(") + 1, hiragana.lastIndexOf(")") ).split( " " );
		shikona.setFirstName_jp( hNames[0] );
		shikona.setLastName_jp( hNames[1] );
		
		rinf.setShikona( shikona );
		
		// hatsu basho
		String[] hatsuBasho = enTrs.get( 7 ).select( "td" ).get( 1 ).text().split( "\\." );
		Calendar c = Calendar.getInstance();
		c.set( Integer.parseInt( hatsuBasho[0] ), Integer.parseInt( hatsuBasho[1] ), 1, 0, 0, 0 );
		rinf.setHatsuBasho( c.getTime() );
		
		return rinf;
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

}
