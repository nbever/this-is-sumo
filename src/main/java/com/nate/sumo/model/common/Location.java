package com.nate.sumo.model.common;

public class Location {

	private Name name;
	private Latitude latitude;
	private Longitude longitude;
	
	public Location( Name name, Latitude latitude, Longitude longitude ){
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Name getName(){
		return name;
	}
	
	public Latitude getLatitude(){
		return latitude;
	}
	
	public Longitude getLongitude(){
		return longitude;
	}
	
	@Override
	public String toString() {
	
		return getName().getFirstName_en() + ":" + getLongitude().toString() + " " + getLatitude().toString();
	}
}
