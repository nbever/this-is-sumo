package com.nate.sumo.model.rikishi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.DatabaseManager;
import com.nate.sumo.model.Entity;
import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;

public class Heya extends Entity<Heya>{

	private Name heyaName;
	private Location location;
	private Name prefix;
	private int prefixLiklihood;
	private static Map<String, Heya> knownHeya;
	
	public Heya(){}

	public static Map<String, Heya> getKnownHeya(){
		
		if ( knownHeya == null ){
			knownHeya = new HashMap<String, Heya>();
			
			List<List<Object>> results = DatabaseManager.getInstance().query( "SELECT ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS FROM APP.HEYA" );
			
			for ( List<Object> result : results ){
				Heya heya = new Heya();
				heya.setId( Long.parseLong( result.get( 0 ).toString() ) );
				
				Name name = new Name();
				name.setFirstName_en( result.get( 1 ).toString() );
				name.setFirstName_kanji( result.get( 2 ).toString() );
				heya.setHeyaName( name );
				
				Long locationId = Long.parseLong( result.get( 3 ).toString() );
				heya.setLocation( Location.getById( locationId ) );
				
				Name prefix = new Name();
				prefix.setFirstName_en( result.get( 4 ).toString() );
				prefix.setFirstName_jp( result.get( 5 ).toString() );
				prefix.setFirstName_kanji( result.get( 6 ).toString () );
				
				heya.setPrefix( prefix );
				
				heya.setPrefixLiklihood( Integer.parseInt( result.get( 7 ).toString () ) );
				
				knownHeya.put( heya.getHeyaName().getFirstName_en(), heya );
			}
		}
		
		return knownHeya;
	}
	
	public Name getHeyaName() {
		return heyaName;
	}

	public void setHeyaName(Name heyaName) {
		this.heyaName = heyaName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Name getPrefix(){
		return prefix;
	}
	
	public void setPrefix( Name name ){
		this.prefix = name;
	}
	
	public Integer getPrefixLiklihood(){
		return prefixLiklihood;
	}
	
	public void setPrefixLiklihood( Integer liklihood ){
		this.prefixLiklihood = liklihood;
	}

	public static Heya getById( Long id )
	{
		// TODO Auto-generated method stub
		return null;
	}
}
