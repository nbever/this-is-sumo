package com.nate.sumo.display.fonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nate.sumo.DatabaseConstants;
import com.nate.sumo.DatabaseManager;

public class JapaneseFontTranslator
{
	private static final Logger logger = LogManager.getLogger();

	/**
	 * This build the database table that maps unicode value to our condensed 
	 * Japanese font texture file
	 * @throws IOException 
	 * @throws  
	 */
	public static void generateJapaneseSqlMap() throws IOException{
		
		List<String> batchSet = new ArrayList<String>();
		
		InputStream inStream = JapaneseFontTranslator.class.getResourceAsStream( "/unicode_to_tex" );
		BufferedReader reader = new BufferedReader( new InputStreamReader( inStream, "UTF-8" ) );
		int rawChar = -1;
		int index = 65;
		
		while( (rawChar = reader.read()) != -1 ){
			
			if ( rawChar < 10000 ){
				continue;
			}
			
			String sql = "insert into " + DatabaseConstants.TBL_JPMAP + "(" + 
				DatabaseConstants.C_UNICODE + ", " + DatabaseConstants.C_NATECODE + ") values (" +
				rawChar + ", " + index + ")";

			logger.info( sql );
			
			DatabaseManager.getInstance().insert( sql );
//			batchSet.add( sql );
//			
//			if ( batchSet.size() >= 100 ){
//				DatabaseManager.getInstance().executeBatchInsert( batchSet );
//				batchSet.clear();
//			}
			
			index++;
		}
		
		DatabaseManager.getInstance().executeBatchInsert( batchSet );
		batchSet.clear();
	}
}
