package com.nate.sumo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseManager {

	private static DatabaseManager instance;
	
	private static Logger logger = LogManager.getLogger();
	
	private Connection connection;
	
	private DatabaseManager(){
		try {
			init();
		} catch (IOException e) {
			logger.error( e );
		} catch (SQLException e) {
			logger.error( e );
		}
	}
	
	public static DatabaseManager getInstance(){
		if ( instance == null ){
			instance = new DatabaseManager();
		}
		
		return instance;
	}
	
	private void init() throws IOException, SQLException{
		loadSql( new File( "db/jpmap.sql" ) );
	}
	
	private Connection getConnection() throws SQLException{
		if ( connection == null ){
			DriverManager.registerDriver( new org.apache.derby.jdbc.EmbeddedDriver() );
			connection = DriverManager.getConnection( "jdbc:derby:db/sumo.db;create=true" );
		}
		return connection;
	}
	
	public void loadSql( File sqlFile ) throws IOException, SQLException{
		
		BufferedReader reader = null;
		
		try {
			logger.info( "Loading DB file: " + sqlFile.getName() );
			reader = new BufferedReader( new FileReader( sqlFile ) );
			int c = 0;
			List<String> batch = new ArrayList<String>();
			String stmt = "";
			
			while( (c = reader.read()) != -1 ){
				
				char ch = (char)c;
				
				if ( ch == ';' ){
					
					batch.add( stmt );
					stmt = "";
					
					if ( batch.size() == 100 ){
						Statement st = getConnection().createStatement();
						
						for ( String s : batch ){
							st.addBatch( s );
						}
						int[] results = st.executeBatch();
						
						for ( int i = 0; i < results.length; i++ ){
							String r = "SUCCESS";
							
							if ( results[i] < 0 ){
								r = "FAIL " + r;
							}
							
							logger.info(  r + " " + batch.get( i ) );
						}
						
						batch.clear();
					}
				}
				else {
					stmt += ch;
				}
			}
		}
		finally {
			if ( reader != null ){
				reader.close();
			}
		}
	}
}
