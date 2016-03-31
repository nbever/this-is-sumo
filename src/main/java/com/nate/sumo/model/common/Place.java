package com.nate.sumo.model.common;

public class Place {
	
	// constant places
	public static final Place KOKUGIKAN = new Place( null, new Name( "Kokugikan", "" ) );
	public static final Place AICHI_PREFECTURAL_GYMNASIUM = new Place( null, new Name( "Aichi Prefectural Gymnasium", "" ) );
	public static final Place FUKUOKA_CENTER = new Place( null, new Name( "Fukuoka Center", "" ) );

	private Location location;
	private Name name;
	
	public Place( Location location, Name name ){
		this.location = location;
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		return getName().getFirstName_en();
	}
}
