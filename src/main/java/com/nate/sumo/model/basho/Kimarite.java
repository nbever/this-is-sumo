package com.nate.sumo.model.basho;

import com.nate.sumo.model.common.Name;

public enum Kimarite {

	ABISETAOSHI( new Name( "Abisetaoshi", "")),
	OSHIDASHI( new Name( "Oshidashi", "")),
	YORIKIRI( new Name( "Yorikiri", "")),
	OSHITAOSHI( new Name( "Oshitaoshi", "")),
	YORITAOSHI( new Name( "Yoritaoshi", "")),
	UWATENAGE( new Name( "Uwatenage", "")),
	SHITATENAGE( new Name( "Shitatenage", "")),
	SUKUINAGE( new Name( "Sukuinage", "")),
	HATAKIKOMI( new Name( "Hatakikomi", "")),
	FUSENSHO( new Name( "Fusensho", "")),
	SOTOGAKE( new Name( "Sotogake", "")),
	UCHIGAKE( new Name( "Uchigake", "")),
	TSUKIOTAOSHI( new Name( "Tsukiotoshi", "")),
	TSUKITAOSHI( new Name( "Tsukiotaoshi", "")),
	KIMIDASHI( new Name( "Kimidasho", "")),
	OKURIDASHI( new Name( "Okuridashi", "")),
	UWATEDASHINAGE( new Name( "Uwatedashinage", "")),
	SHITATEDASHINAGE( new Name( "Shitatedashinage", "")),
	KUBINAGE( new Name( "Kubinage", "")),
	UCHARI( new Name( "Uchari", ""));
	
	private Name name;
	
	private Kimarite( Name aName ){
		this.name = aName;
	}
	
	public Name getName(){
		return name;
	}
}
