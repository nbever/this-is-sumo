package com.nate.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.nate.sumo.display.fonts.JapaneseFontTranslator;

public class CharacterFinder {

	private static final String PAGE = "http://www.sumo.or.jp/honbasho/banzuke/index";
	
	public static void main( String[] args ){
		try {
			new CharacterFinder();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CharacterFinder() throws Exception{
		
		URL pageUrl = new URL( PAGE );
		String result = "";
		
		InputStream stream = pageUrl.openStream();
		
		BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) );
		
		int cInt = -1;
		
		while( (cInt = reader.read()) != -1 ){
			if ( cInt < 10000 ){
				continue;
			}
			
			if ( JapaneseFontTranslator.isUnique( cInt ) ){
				
				if ( result.indexOf(  (char)cInt ) == -1 ){
					result += (char)cInt;
				}
			}
		}
		
		File outFile = new File( "unique.txt" );
		FileWriter writer = new FileWriter( outFile );
		
		writer.write( result );
		writer.flush();
		writer.close();
		reader.close();
	}
}
