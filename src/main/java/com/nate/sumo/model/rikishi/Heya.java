package com.nate.sumo.model.rikishi;

import com.nate.sumo.model.common.Location;
import com.nate.sumo.model.common.Name;

public class Heya {

	private Long id;
	private Name heyaName;
	private Location location;
	
	public Heya(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
}
