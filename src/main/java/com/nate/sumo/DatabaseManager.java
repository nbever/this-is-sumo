package com.nate.sumo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nate.sumo.display.fonts.JapaneseFontTranslator;

public class DatabaseManager {

	private static DatabaseManager instance;
	
	private static Logger logger = LogManager.getLogger();
	
	private Connection connection;
	
	private DatabaseManager(){
	}
	
	public static DatabaseManager getInstance(){
		if ( instance == null ){
			instance = new DatabaseManager();
			
			try {
				instance.init();
			} catch (IOException e) {
				logger.error( e );
			} catch (SQLException e) {
				logger.error( e );
			}
		}
		
		return instance;
	}
	
	private void init() throws IOException, SQLException{
		
		JapaneseFontTranslator.generateJapaneseSqlMap();
		
		if ( !tableExists( "RIKISHI_INFO" ) ){
			
			logger.info( "Creating tables..." );
			URL resourceUrl = DatabaseManager.class.getResource( "/sumo.ddl" );
			loadSql( new File( resourceUrl.getFile() ) ); 

			logger.info( "Loading necessary SQL into the database..." );
			logger.info( "Adding locations..." );
			loadSql( new File( DatabaseManager.class.getResource( "/locations.sql" ).getFile() ) );
			logger.info(  "Adding ichimon..." );
			loadSql( new File( DatabaseManager.class.getResource( "/ichimon.sql" ).getFile() ) );
			logger.info( "Adding heya..." );
			loadSql( new File( DatabaseManager.class.getResource( "/heya.sql" ).getFile() ) );
		}
	}
	
	private Connection getConnection() throws SQLException{
		if ( connection == null ){
			DriverManager.registerDriver( new org.apache.derby.jdbc.EmbeddedDriver() );
			connection = DriverManager.getConnection( "jdbc:derby:db/sumo.db;create=true" );
		}
		return connection;
	}
	
	public boolean tableExists( String tableName ) {
		
		try
		{
			ResultSet set = getConnection().getMetaData().getTables( "APP", null, tableName, null );
			List<List<Object>> results = parseResults( set );
			
			return (results.size() > 0);
		}
		catch (SQLException e)
		{
			logger.error( "Error checking for table existence.", e );
		}
		
		return true;
		
	}
	
	public List<List<Object>> query( String sql ) {
		
		List<List<Object>> typedResults = new ArrayList<List<Object>>();
		
		try {
			Statement stmnt = getConnection().createStatement();
			
			ResultSet results = stmnt.executeQuery( sql );
			
			typedResults = parseResults(results);
		}
		catch( Exception e ){
			logger.error( e );
		}
		
		return typedResults;
	}
	
	private List<List<Object>> parseResults( ResultSet results ) throws SQLException{
		
		List<List<Object>> typedResults = new ArrayList<List<Object>>();
		int columns = results.getMetaData().getColumnCount();
		
		while( results.next() == true ){
			
			List<Object> row = new ArrayList<Object>();
			ResultSetMetaData metaData = results.getMetaData();
			
			for ( int i = 1; i <= columns; i++ ){
				
				int type = metaData.getColumnType( i );
				
				switch( type ){
					case Types.INTEGER:
						row.add( results.getInt( i ) );
						break;
					case Types.DOUBLE:
						row.add( results.getDouble( i ) );
						break;
					case Types.BOOLEAN:
						row.add( results.getBoolean( i ) );
						break;
					case Types.FLOAT:
						row.add( results.getFloat( i ) );
						break;
					case Types.CHAR:
					case Types.VARCHAR:
						row.add( results.getString( i ) );
						break;
					default:
						row.add( results.getObject( i ) );
						break;
				}
			}// for
			
			typedResults.add( row );
		}
		
		return typedResults;
	}
	
	public boolean execute( String sql ){
		
		boolean result = false;
		
		try {
			Statement stmnt = getConnection().createStatement();
			result = stmnt.execute( sql );
		}
		catch( Exception e ){
			logger.error( e );
			result = false;
		}
		
		return result;
	}
	
	public int[] executeBatchInsert( List<String> batch ){
		
		int[] results = new int[1];
		
		try {
			Statement st = getConnection().createStatement();

			for ( String s : batch ){
				logger.info( "Executing: " + s );
				st.execute( s );
			}
//			for ( String s : batch ){
//				st.addBatch( s );
//			}
//			
//			results = st.executeBatch();
//			
//			for ( int i = 0; i < results.length; i++ ){
//				String r = "SUCCESS";
//				
//				if ( results[i] < 0 ){
//					r = "FAIL " + r;
//				}
//				
//				logger.info(  r + " " + batch.get( i ) );
//			}
		}
		catch( SQLException se ){
			logger.error( "Problem inserting records.", se );
		}
		
		return results;
	}
	
	public void loadSql( File sqlFile ) throws IOException{
		
		BufferedReader reader = null;
		
		try {
			logger.info( "Loading DB file: " + sqlFile.getName() );
			reader = new BufferedReader( new FileReader( sqlFile ) );
			int c = 0;
			List<String> batch = new ArrayList<String>();
			String stmt = "";
			
			while( (c = reader.read()) != -1 ){
				
				char ch = (char)c;
				
				if ( ch == '\t' || ch == '\r' || ch == '\n' ){
					continue;
				}
				
				if ( ch == ';' ){
					
					batch.add( stmt );
					stmt = "";
					
					if ( batch.size() == 100 ){
						executeBatchInsert( batch );
						
						batch.clear();
					}
				}
				else {
					stmt += ch;
				}
			}
			
			executeBatchInsert( batch );
			batch.clear();
		}
		finally {
			if ( reader != null ){
				reader.close();
			}
		}
	}
}
