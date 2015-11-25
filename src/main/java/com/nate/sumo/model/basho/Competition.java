package com.nate.sumo.model.basho;

import com.nate.sumo.model.common.Place;

public enum Competition {

	HATSU( Place.KOKUGIKAN),
	HARU( Place.KOKUGIKAN ),
	NAGOYA( Place.AICHI_PREFECTURAL_GYMNASIUM),
	NATSU( Place.KOKUGIKAN ),
	AKI( Place.KOKUGIKAN ),
	KYUSHUU( Place.FUKUOKA_CENTER );
	
	private Place place;
	
	private Competition( Place place ){
		this.place = place;
	}
	
	public Place getPlace(){
		return this.place;
	}
}
