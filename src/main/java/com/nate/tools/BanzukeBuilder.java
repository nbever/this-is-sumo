package com.nate.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
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
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.basho.Rank.RankSide;
import com.nate.sumo.model.common.Height;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;
import com.nate.sumo.model.common.Record;
import com.nate.sumo.model.common.Weight;
import com.nate.sumo.model.rikishi.Heya;
import com.nate.sumo.model.rikishi.RikishiInfo;

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
		
		Document engPage = Jsoup.connect( ENGLISH_QUERY + YEAR + year + "&" + MONTH + month ).timeout( 30000 ).get();
		
		Elements enElms = engPage.select( "table.record>tbody>tr" );
		
		// starts at 2
		for ( int i = 649; i < enElms.size(); i++ ){
			
			Element enTr = enElms.get( i );
			
			Elements enTds = enTr.select( "td" );
			
			String rikishiUrl = enTds.get( 0 ).select( "a" ).get( 0 ).attr( "href" );
			Rank currentRank = null;
			
			// get current rank
			if ( enTds.size() > 10 ){
				currentRank = Rank.parseRank( enTds.get( 10 ).text() );
			}
			
			Document enPage = Jsoup.connect( MAIN_URL + "/" + rikishiUrl ).timeout( 30000 ).get();
			Document jpPage = Jsoup.connect( MAIN_URL + "/" + rikishiUrl + "&l=j" ).timeout( 30000 ).get();
			
			Long rikishiId = Long.parseLong( rikishiUrl.split( "=" )[1] );
			
			System.out.println( "Row: " + i + " Rikishi ID: " + rikishiId + " URL: " + rikishiUrl );
			
			if ( currentRank == null ){
				System.out.println( "This rikishi has not rank so will be skipped." );
				continue;
			}
			
			RikishiInfo rInfo = buildRikishiInfo( rikishiId, enPage, jpPage );
			rInfo.setCurrentRank( currentRank );
			
			List<String> bashoUrls = new ArrayList<String>();
			
			// how many basho back we want to go
			for ( int j = 0; j < 3; j++ ){
				int tMonth = month - (j*2);
				int tYear = year;
				
				if ( tMonth < 0 ){
					tMonth = 11;
					tYear--;
				}
				
				String sMonth = Integer.toString( tMonth );
				
				if ( sMonth.length() == 1 ){
					sMonth = "0" + sMonth;
				}
				
				String bashoUrl = BASHO_QUERY + "r=" + rInfo.getId() + "&b=" + tYear + sMonth;
				bashoUrls.add( bashoUrl );
			}
			
			List<List<MatchResult>> bashoResults = buildBashoResults( bashoUrls );
			
			writeRecord( i, year, month, rInfo, bashoResults );
		}
	}
	
	private RikishiInfo buildRikishiInfo( Long id, Document enPage, Document jpPage ) throws IOException{
		
		RikishiInfo rinf = new RikishiInfo();
		rinf.setId( id );
		
		// set up the indices since they change with whether or not the rikishi went to college.
		int HIGH_RANK = 0;
		int REAL_NAME = 1;
		int BIRTHDAY = 2;
		int SHUSSIN = 3;
		int H_AND_W = 4;
		int UNIVERSITY = -1;
		int HEYA = 5;
		int SHIKONA = 6;
		int HATSU = 7;
		int CAREER = 9;
		
		Name shikona = new Name();
		Name realName = new Name();
		
		Elements enTrs = enPage.select( "table.rikishidata table.rikishidata tr" );
		Elements jpTrs = jpPage.select( "table.rikishidata table.rikishidata tr" );
		
		// they went to university
		if ( enTrs.select( "td:matches(University)" ).size() > 0 ){
			HEYA = 6;
			SHIKONA = 7;
			HATSU = 8;
			CAREER = 10;
			UNIVERSITY = 5;
		}
		
		// there's an intai... have to add one to career
		if ( enTrs.select( "td:matches(Intai)" ).size() > 0 ){
			CAREER++;
		}
		
		// there's a kabu... have to add one to career
		if ( enTrs.select( "td:matches(Kabu)" ).size() > 0 ){
			CAREER++;
		}
		
		// highest rank
		String highRankText = enTrs.get( HIGH_RANK ).select( "td" ).get( 1 ).text();
		String[] tokens = highRankText.split( " " );
		RankClass rClass = RankClass.valueOf( tokens[0].toUpperCase() );

		if ( tokens.length > 1 && !tokens[1].startsWith( "(" ) ){
			Integer rInt = Integer.parseInt( tokens[1] );
			rinf.setHighestRank( new Rank( rClass, rInt ) );
		}
		else {
			rinf.setHighestRank( new Rank( rClass ) );
		}
		
		// real name
		String[] enName = enTrs.get( REAL_NAME ).select( "td" ).get( 1 ).text().split( " " );
		String[] jpName = jpTrs.get( REAL_NAME ).select( "td" ).get( 1 ).text().split( "\u3000" );
		
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
		String dString = enTrs.get( BIRTHDAY ).select( "td" ).get( 1 ).text();
		
		if ( dString.lastIndexOf( "(" ) != -1 ){
			dString = dString.substring( 0, dString.lastIndexOf( "(" ) - 1 );
		}
		
		rinf.setBirthday( parseDate( dString ) );
		
		// shusshin
		String enShusshin = enTrs.get( SHUSSIN ).select( "td" ).get( 1 ).text();
		String[] enShusshins = enShusshin.split( ", " );
		
		if ( enShusshins.length > 1 ){
			enShusshins[1] = enShusshins[1].replaceAll( " - Mongolia", "" );
		}
		
		Location l = Location.getKnownLocations().get( enShusshins[0] + enShusshins[1] );
		
		rinf.setHometown( l );
		
		// height and weight
		String[] handw = enTrs.get( H_AND_W ).select( "td" ).get( 1 ).text().split( " " );
		Float fWeight = Float.parseFloat( handw[2] );
		Float fHeight = Float.parseFloat( handw[0] );
		rinf.setWeight( new Weight( fWeight.intValue() ) );
		rinf.setHeight( new Height( fHeight.intValue() ) );
		
		// heya name
		String heyaKey = enTrs.get( HEYA ).select( "td" ).get( 1 ).text();
		
		Heya heya = Heya.getKnownHeya().get( heyaKey );
		rinf.setHeya( heya );
		
		// university
		if ( UNIVERSITY != -1 ){
			Name university = new Name();
			university.setFirstName_en( enTrs.get( UNIVERSITY ).select( "td" ).get( 1 ).text() );
			university.setFirstName_kanji( jpTrs.get( UNIVERSITY ).select( "td" ).get( 1 ).text() );
			rinf.setuniversity( university );
		}
		
		// shikona
		String[] enShik = enTrs.get( SHIKONA ).select( "td" ).get( 1 ).text().split( "\\s+" );
		String[] jpShik = jpTrs.get( SHIKONA ).select( "td" ).get( 1 ).text().split( "\\s+" ); 
				
//		if ( jpShik.length < 2 ) {
//			jpShik = jpTrs.get( 6 ).select( "td" ).get( 1 ).text().split( "\u3000" );
//		}
		jpShik = jpShik[ jpShik.length - 1 ].split( "\u3000" );
		
		if ( enShik.length > 1 ){
			shikona.setFirstName_en( enShik[ enShik.length-2] );
			shikona.setLastName_en( enShik[enShik.length-1] );
		}
		else {
			shikona.setFirstName_en( enShik[enShik.length - 1]);
		}
		
		if ( jpShik.length > 1 ){
			shikona.setFirstName_kanji( jpShik[jpShik.length - 2] );
			shikona.setLastName_kanji( jpShik[jpShik.length - 1] );
		}
		else {
			shikona.setFirstName_kanji( jpShik[jpShik.length - 1]);
		}
		//the hiragana
		String hiragana = jpPage.select( "table.layout td h2" ).text();
		hiragana = hiragana.substring( hiragana.indexOf("\uff08") + 1, hiragana.lastIndexOf("\uff09") );
		String[] hNames = hiragana.split( "\u3000" );
		shikona.setFirstName_jp( hNames[0] );
		
		if ( hNames.length > 1 ){
			shikona.setLastName_jp( hNames[1] );
		}
		
		rinf.setShikona( shikona );
		
		System.out.println( shikona.getFirstName_en() + " " + shikona.getLastName_en() + " : " + shikona.getFirstName_kanji() + " " + shikona.getLastName_kanji() );
		
		// hatsu basho
		String[] hatsuBasho = enTrs.get( HATSU ).select( "td" ).get( 1 ).text().split( "\\." );
		Calendar c = Calendar.getInstance();
		
		if ( hatsuBasho[1].indexOf( " " ) != -1 ){
			hatsuBasho[1] = hatsuBasho[1].substring( 0, hatsuBasho[1].indexOf(" ") );
		}
		
		c.set( Integer.parseInt( hatsuBasho[0] ), Integer.parseInt( hatsuBasho[1] ), 1, 0, 0, 0 );
		rinf.setHatsuBasho( c.getTime() );
		
		// career record
		String[] careerStr = enTrs.get( CAREER ).select( "td" ).get( 1 ).text().split( "\\-" );
		Record record = new Record();
		
		Integer wins = Integer.parseInt( careerStr[0] );
		Integer loses = 0;
		Integer forfeits = 0;
		
		// this means no forfeits yet
		if ( careerStr.length == 2 ){
			loses = Integer.parseInt( careerStr[1].substring( 0, careerStr[1].indexOf( "/" ) ) );
		}
		// they have forfeited
		else {
			loses = Integer.parseInt( careerStr[1] );
			
			if ( careerStr[2].indexOf( "/") != -1 ){
				forfeits = Integer.parseInt( careerStr[2].substring(0, careerStr[2].indexOf("/") ) );
			}	
		}

		record.setWins( wins );
		record.setLoses( loses );
		record.setForfeits( forfeits );
		
		rinf.setCareerRecord( record );
		
		// find yusho stuffs
		String[] makuStats = enTrs.select( "td:matches(In Makuuchi) + td").text().split( "," );
		
		rinf.setMakuuchiYusho( getStatValue( makuStats, "Yusho" ) );
		rinf.setMakuuchiJunYusho( getStatValue( makuStats, "Jun-Yusho" ) );
		rinf.setGinoSho( getStatValue( makuStats, "Gino-Sho" ) );
		rinf.setShukunSho( getStatValue( makuStats, "Shukun-Sho" ) );
		rinf.setKantoSho( getStatValue( makuStats, "Kanto-Sho" ) );
		
		String[] juryoStats = enTrs.select( "td:matches(In Juryo) + td").text().split( "," );
		
		rinf.setJuryoYusho( getStatValue( juryoStats, "Yusho" ) );
		rinf.setJuryoJunYusho( getStatValue( juryoStats, "Jun-Yusho" ) );
		
		String[] makushitaStats = enTrs.select( "td:matches(In Makushita) + td").text().split( "," );
		
		rinf.setMakushitaYusho( getStatValue( makushitaStats, "Yusho" ) );
		rinf.setMakushitaJunYusho( getStatValue( makushitaStats, "Jun-Yusho" ) );
		
		String[] sandanmeStats = enTrs.select( "td:matches(In Sandanme) + td").text().split( "," );
		
		rinf.setSandanmeYusho( getStatValue( sandanmeStats, "Yusho" ) );
		rinf.setSandanmeJunYusho( getStatValue( sandanmeStats, "Jun-Yusho" ) );
		
		String[] jonidanStats = enTrs.select( "td:matches(In Jonidan) + td").text().split( "," );
		
		rinf.setJonidanYusho( getStatValue( jonidanStats, "Yusho" ) );
		rinf.setJonidanJunYusho( getStatValue( jonidanStats, "Jun-Yusho" ) );
		
		String[] jonokuchiStats = enTrs.select( "td:matches(In Jonokuchi) + td").text().split( "," );
		
		rinf.setJonokuchiYusho( getStatValue( jonokuchiStats, "Yusho" ) );
		rinf.setJonokuchiJunYusho( getStatValue( jonokuchiStats, "Jun-Yusho" ) );
		
		String[] maeZumoStats = enTrs.select( "td:matches(In Mae-Zumo) + td").text().split( "," );
		
		rinf.setMaeZumoYusho( getStatValue( maeZumoStats, "Yusho" ) );
		
		return rinf;
	}
	
	private Integer getStatValue( String[] statElements, String tag ){
		
		for( String element : statElements ){
			
			if ( element.indexOf( tag ) != -1 ){
				String[] vals = element.trim().split( " " );
				
				return Integer.parseInt( vals[0] );
			}
		}
		
		return 0;
	}
	
	private List<List<MatchResult>> buildBashoResults( List<String> bashoUrls ){
	
		List<List<MatchResult>> results = new ArrayList<List<MatchResult>>();
		
		for ( String url : bashoUrls ){
			List<MatchResult> matches = new ArrayList<MatchResult>();
			
			try
			{
				Document bashoPage = Jsoup.connect( url ).timeout( 30000 ).get();
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
					
					String kText = fields.get( 2 ).text();
					
					if  ( kText.trim() != "" && kText.trim().length() > 4 ){
						kimarite = Kimarite.valueOf( fields.get( 2 ).text().toUpperCase() );
					}
					
					Element matchInfo = fields.get( 3 ).select( "a" ).get( 0 );
					
					String oppUrl = matchInfo.attr( "href" );
					opponentId = Long.parseLong( oppUrl.substring( oppUrl.indexOf( "=" ) + 1 ) );
					String[] infoBits = matchInfo.text().split( " " );
					opponentRank = Rank.parseRank( infoBits[0] );
					
					String[] recordInfo = fields.get( 3 ).select( "a" ).get( 1 ).text().split( " " );
					
					int recordToUse = 1;
					
					if ( recordInfo.length < 2 ){
						recordToUse = 0;
					}
					
					String rStr = recordInfo[recordToUse];

					if ( rStr.indexOf( "(" ) != -1 ){
						rStr = rStr.substring( 1, rStr.length() - 1 );
					}
					
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
					result.setWin( win );
					
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
	
	/**
	 * Write this info into a file for computations to take place on.
	 * 
	 * @param info
	 * @param bashoResults
	 * @throws IOException 
	 */
	private void writeRecord( Integer rowIndex, Integer year, Integer basho,  RikishiInfo info, List<List<MatchResult>> bashoResults ) throws IOException{
		
		File crunchFile = new File( year + "-" + basho + ".crunch" );
		
		if ( !crunchFile.exists() ){
			crunchFile.createNewFile();
		}
		
		try ( FileWriter writer = new FileWriter( crunchFile, true ) ){
			
			writer.write( rowIndex + ":" );
			
			writeHelper( writer, Long.toString( info.getId() ) );
			writeHelper( writer, info.getRealName().getFirstName_en() );
			writeHelper( writer, info.getRealName().getFirstName_jp() );
			writeHelper( writer, info.getRealName().getFirstName_kanji() );
			writeHelper( writer, info.getRealName().getLastName_en() );
			writeHelper( writer, info.getRealName().getLastName_jp() );
			writeHelper( writer, info.getRealName().getLastName_kanji() );
			writeHelper( writer, info.getShikona().getFirstName_en() );
			writeHelper( writer, info.getShikona().getFirstName_jp() );
			writeHelper( writer, info.getShikona().getFirstName_kanji() );
			writeHelper( writer, info.getShikona().getLastName_en() );
			writeHelper( writer, info.getShikona().getLastName_jp() );
			writeHelper( writer, info.getShikona().getLastName_kanji() );
			
			writeHelper( writer, info.getCurrentRank().getRankClass().name() );
			
			if ( info.getCurrentRank().getRankSide() == null ){
				writeHelper( writer, RankSide.EAST.name() );
			}
			else {
				writeHelper( writer, info.getCurrentRank().getRankSide().name() );
			}
			
			if ( info.getCurrentRank().getRankNumber() == null ){
				writeHelper( writer, "0" );
			}
			else {
				writeHelper( writer, info.getCurrentRank().getRankNumber().toString() );
			}
			
			if ( info.getUniversity() != null ){
				writeHelper( writer, info.getUniversity().getFirstName_en() );
				writeHelper( writer, info.getUniversity().getFirstName_kanji() );
			}
			else {
				writeHelper( writer, "" );
				writeHelper( writer, "" );
			}
			
			writeHelper( writer, Long.toString( info.getBirthday().getTime() ) );
			writeHelper( writer, Long.toString( info.getHatsuBasho().getTime() ) );
			writeHelper( writer, info.getHeight().getValue().toString() );
			writeHelper( writer, info.getWeight().getValue().toString() );
			
			if ( info.getHeya() == null ){
				writeHelper( writer, "-1" );
			}
			else {
				writeHelper( writer, info.getHeya().getId().toString() );
			}
			
			if ( info.getHometown() == null ){
				writeHelper( writer, "-1" );
			}
			else {
				writeHelper( writer, info.getHometown().getId().toString() );
			}
			
			writeHelper( writer, info.getHighestRank().getRankClass().name() );
			
			if ( info.getHighestRank().getRankNumber() == null ){
				writeHelper( writer, "0" );
			}
			else {
				writeHelper( writer, info.getHighestRank().getRankNumber().toString() );
			}
			
			if ( info.getHighestRank().getRankSide() == null ){
				writeHelper( writer, Rank.RankSide.EAST.name() );
			}
			else {
				writeHelper( writer, info.getHighestRank().getRankSide().name() );
			}
			
			writeHelper( writer, info.getCareerRecord().getWins().toString() );
			writeHelper( writer, info.getCareerRecord().getLoses().toString() );
			writeHelper( writer, info.getCareerRecord().getForfeits().toString() );
			
			writeHelper( writer, info.getMakuuchiYusho().toString() );
			writeHelper( writer, info.getMakuuchiJunYusho().toString() );
			writeHelper( writer, info.getGinoSho().toString() );
			writeHelper( writer, info.getKantoSho().toString() );
			writeHelper( writer, info.getShukunSho().toString() );
			writeHelper( writer, info.getJuryoYusho().toString() );
			writeHelper( writer, info.getJuryoJunYusho().toString() );
			writeHelper( writer, info.getMakushitaYusho().toString() );
			writeHelper( writer, info.getMakushitaJunYusho().toString() );
			writeHelper( writer, info.getSandanmeYusho().toString() );
			writeHelper( writer, info.getSandanmeJunYusho().toString() );
			writeHelper( writer, info.getJonidanYusho().toString() );
			writeHelper( writer, info.getJonidanJunYusho().toString() );
			writeHelper( writer, info.getJonokuchiYusho().toString() );
			writeHelper( writer, info.getJonokuchiJunYusho().toString() );
			writeHelper( writer, info.getMaeZumoYusho().toString() );
			
			writer.write( "-_-" );
			
			for ( List<MatchResult> bashoResult : bashoResults ){
				
				for ( MatchResult match : bashoResult ){
					
					writeHelper( writer, match.getWin().toString() );
					
					if ( match.getKimarite() != null ){
						writeHelper( writer, match.getKimarite().name() );
					}
					else {
						writeHelper( writer, "" );
					}
					
					writeHelper( writer, match.getOpponenId().toString() );
					writeHelper( writer, match.getOpponentRank().getRankClass().name() );
					
					if ( match.getOpponentRank().getRankNumber() != null ){
						writeHelper( writer, match.getOpponentRank().getRankNumber().toString() );
					}
					else {
						writeHelper( writer, "0" );
					}
					
					if ( match.getOpponentRank().getRankSide() == null ){
						writeHelper( writer, Rank.RankSide.EAST.name() );
					}
					else {
						writeHelper( writer, match.getOpponentRank().getRankSide().name() );
					}
					
					writeHelper( writer, match.getOpponentRecord().getWins().toString() );
					writeHelper( writer, match.getOpponentRecord().getLoses().toString() );
					writeHelper( writer, match.getOpponentRecord().getForfeits().toString() );
					writer.write( "=" );
				}
				
				writer.write( "***" );
			}
			
			writer.write( '\n' );
		}
		
	}
	
	private void writeHelper( FileWriter writer, String item ) throws IOException{
		
		if ( item != null ){
			writer.write( item );			
		}
		writer.write( "," );
	}

}
