package com.nate.sumo.model.common;

public class Place {
	
	// constant places
	public static final Place KOKUGIKAN = new Place( null, null );
	public static final Place AICHI_PREFECTURAL_GYMNASIUM = new Place( null, null );
	public static final Place FUKUOKA_CENTER = new Place( null, null );

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
	
	
}
