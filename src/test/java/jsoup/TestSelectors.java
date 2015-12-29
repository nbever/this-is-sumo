package jsoup;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestSelectors
{

	@Test
	public void test() throws IOException
	{
		Document doc = Jsoup.connect( "http://sumodb.sumogames.de/Rikishi.aspx?r=1219" ).get();
//		Document doc = Jsoup.connect( "http://sumodb.sumogames.de/Rikishi.aspx?r=6445" ).get();
		
		Elements enTrs = doc.select( "table.rikishidata table.rikishidata tr" );
		
		String[] makuStats = enTrs.select( "td:matches(In Makuuchi) + td").text().split( "," );
		
		int yusho = getStatValue( makuStats, "Yusho" );
		int junYusho = getStatValue( makuStats, "Jun-Yusho" );
		int ginoSho = getStatValue( makuStats, "Gino-Sho" );
		int shukunSho = getStatValue( makuStats, "Shukun-Sho" );
		int kantoSho = getStatValue( makuStats, "Kanto-Sho" );
		
		String[] juryoStats = enTrs.select( "td:matches(In Juryo) + td").text().split( "," );
		String[] makushitaStats = enTrs.select( "td:matches(In Makushita) + td").text().split( "," );
		String[] sandanmeStats = enTrs.select( "td:matches(In Sandanme) + td").text().split( "," );
		String[] jonidanStats = enTrs.select( "td:matches(In Jonidan) + td").text().split( "," );
		String[] jonokuchiStats = enTrs.select( "td:matches(In Jonokuchi) + td").text().split( "," );
		String[] maeZumoStats = enTrs.select( "td:matches(In Mae-Zumo) + td").text().split( "," );
		
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

}
