package com.nate.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.nate.sumo.DatabaseManager;

public class BanzukeBuilder
{
	private static final String ENGLISH_QUERY = "http://sumodb.sumogames.de/Query.aspx?show_form=0&columns=1&rowcount=5&show_sansho=on&showheya=on" + 
			"&showshusshin=on&showbirthdate=on&showhatsu=on&showintai=on&showheight=on&showweight=on&showhighest=on&";
	private static final String JAPANESE_QUERY = "http://sumodb.sumogames.de/Query.aspx?show_form=0&columns=1&rowcount=5&show_sansho=on&showheya=on" + 
			"&showshusshin=on&showbirthdate=on&showhatsu=on&showintai=on&showheight=on&showweight=on&showhighest=on&l=j&";
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
		Document japPage = Jsoup.connect( JAPANESE_QUERY + YEAR + year + "&" + MONTH + month ).get();
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
