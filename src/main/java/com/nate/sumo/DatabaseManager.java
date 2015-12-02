package com.nate.sumo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		
		List<List<Object>> results = query( "SELECT * FROM sys.systable" );
		
		if ( results.size() == 0 ){
			
			logger.info( "Creating tables..." );
			URL resourceUrl = DatabaseManager.class.getResource( "/sumo.ddl" );
			loadSql( new File( resourceUrl.getFile() ) ); 
			
			logger.info( "Loading necessary SQL into the database..." );
			JapaneseFontTranslator.generateJapaneseSqlMap();
		}
	}
	
	private Connection getConnection() throws SQLException{
		if ( connection == null ){
			DriverManager.registerDriver( new org.apache.derby.jdbc.EmbeddedDriver() );
			connection = DriverManager.getConnection( "jdbc:derby:db/sumo.db;create=true" );
		}
		return connection;
	}
	
	public List<List<Object>> query( String sql ) {
		
		List<List<Object>> typedResults = new ArrayList<List<Object>>();
		
		try {
			Statement stmnt = getConnection().createStatement();
			
			ResultSet results = stmnt.executeQuery( sql );
			
			int columns = results.getMetaData().getColumnCount();
			
			while( results.next() == true ){
				
				List<Object> row = new ArrayList<Object>();
				
				for ( int i = 0; i < columns; i++ ){
					
					int type = results.getMetaData().getColumnType( i );
					
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
		}
		catch( Exception e ){
			
		}
		
		return typedResults;
	}
	
	public boolean insert( String sql ){
		
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
				st.addBatch( s );
			}
			
			results = st.executeBatch();
			
			for ( int i = 0; i < results.length; i++ ){
				String r = "SUCCESS";
				
				if ( results[i] < 0 ){
					r = "FAIL " + r;
				}
				
				logger.info(  r + " " + batch.get( i ) );
			}
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
