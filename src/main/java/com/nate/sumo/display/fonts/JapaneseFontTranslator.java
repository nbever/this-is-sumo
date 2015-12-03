package com.nate.sumo.display.fonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nate.sumo.DatabaseConstants;
import com.nate.sumo.DatabaseManager;

public class JapaneseFontTranslator
{
	private static final Logger logger = LogManager.getLogger();

	private static Map<Integer, Integer> characterMap;
	
	/**
	 * This build the database table that maps unicode value to our condensed 
	 * Japanese font texture file
	 * @throws IOException 
	 * @throws  
	 */
	public static void generateJapaneseSqlMap() throws IOException{
		
		InputStream inStream = JapaneseFontTranslator.class.getResourceAsStream( "/unicode_to_tex" );
		BufferedReader reader = new BufferedReader( new InputStreamReader( inStream, "UTF-8" ) );
		int rawChar = -1;
		int index = 65;
		
		while( (rawChar = reader.read()) != -1 ){
			
			if ( rawChar < 10000 ){
				continue;
			}
			
			Integer mappedValue = getCharacterMap().get( rawChar );
			
			if ( mappedValue == null ){
				getCharacterMap().put( rawChar, index );
			}
			else {
				logger.warn( "Found a duplicate character: " + ((int)rawChar) + ": " + rawChar + " index: " + index );
			}
			
			index++;
		}
	}
	
	public static char[] convert( String jStr ){
		
		char[] cList = new char[jStr.length()];
		
		for ( int i = 0; i < jStr.length(); i++ ){
			char jChar = jStr.charAt( i );
			Integer mappedValue = getCharacterMap().get( (int)jChar );
			
			// null means a space
			if ( mappedValue == null ){
				mappedValue = (int)(" ".charAt( 0 ));
			}
			
			cList[i] = (char)mappedValue.intValue();
		}
		
		return cList;
	}
	
	private static Map<Integer, Integer> getCharacterMap(){
		if ( characterMap == null ){
			characterMap = new HashMap<Integer, Integer>();
		}
		
		return characterMap;
	}
}
