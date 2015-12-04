package com.nate.sumo.model.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.DatabaseManager;
import com.nate.sumo.model.Entity;

public class Location extends Entity{

	private Name country;
	private Name area;
	private Latitude latitude;
	private Longitude longitude;
	
	private static Map<String, Location> knownLocations;
	
	public Location(){
		
	}
	
	public Location( Name name, Name area, Latitude latitude, Longitude longitude ){
		this.country = name;
		this.area = area;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public static Map<String, Location> getKnownLocations(){
		
		if ( knownLocations == null ){
			knownLocations = new HashMap<String, Location>();
			
			List<List<Object>> results = DatabaseManager.getInstance().query( "SELECT ID, EN_COUNTRY, JP_COUNTRY, EN_AREA, JP_AREA, LONGITUDE, LATITUDE FROM APP.LOCATIONS"  );
			
			for( List<Object> row : results ){
				Location location = new Location();
				location.setId( (Long)row.get( 0 ) );
				Name country = new Name();
				Name area = new Name();
				
				country.setFirstName_en( row.get( 1 ).toString() );
				country.setFirstName_kanji( row.get( 2 ).toString()  );
				area.setFirstName_en( row.get( 3 ).toString() );
				area.setFirstName_kanji( row.get( 4 ).toString() );
				
				location.setArea( area );
				location.setCountry( country );
				
				location.setLongitude( new Longitude( (Double)row.get( 5 ) ) );
				location.setLatitude( new Latitude( (Double)row.get( 6 ) ) );
				
				knownLocations.put( country.getFirstName_en() + area.getFirstName_en(), location );
			}
		}
		
		return knownLocations;
	}
	
	public Name getCountry(){
		return country;
	}
	
	public void setCountry( Name name ){
		this.country = name;
	}
	
	public Name getArea(){
		return area;
	}
	
	public void setArea( Name name ){
		area = name;
	}
	
	public Latitude getLatitude(){
		return latitude;
	}
	
	public void setLatitude( Latitude latitude ){
		this.latitude = latitude;
	}
	
	public Longitude getLongitude(){
		return longitude;
	}
	
	public void setLongitude( Longitude longitude ){
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
	
		return getCountry().getFirstName_en() + ", " + getArea().getFirstName_en() + ":" + getLongitude().toString() + " " + getLatitude().toString();
	}
}
