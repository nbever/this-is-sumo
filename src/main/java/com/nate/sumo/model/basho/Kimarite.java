package com.nate.sumo.model.basho;

import java.util.Arrays;

import com.nate.sumo.model.common.Name;

public enum Kimarite {

	ABISETAOSHI( new Name( "Abisetaoshi", Arrays.asList( 2 ))),
	OSHIDASHI( new Name( "Oshidashi", Arrays.asList( 2 ))),
	YORIKIRI( new Name( "Yorikiri", Arrays.asList( 2 ))),
	OSHITAOSHI( new Name( "Oshitaoshi", Arrays.asList( 2 ))),
	YORITAOSHI( new Name( "Yoritaoshi", Arrays.asList( 2 ))),
	UWATENAGE( new Name( "Uwatenage", Arrays.asList( 2 ))),
	SHITATENAGE( new Name( "Shitatenage", Arrays.asList( 2 ))),
	SUKUINAGE( new Name( "Sukuinage", Arrays.asList( 2 ))),
	HATAKIKOMI( new Name( "Hatakikomi", Arrays.asList( 2 ))),
	FUSENSHO( new Name( "Fusensho", Arrays.asList( 2 ))),
	SOTOGAKE( new Name( "Sotogake", Arrays.asList( 2 ))),
	UCHIGAKE( new Name( "Uchigake", Arrays.asList( 2 ))),
	TSUKIOTAOSHI( new Name( "Tsukiotoshi", Arrays.asList( 2 ))),
	TSUKITAOSHI( new Name( "Tsukiotaoshi", Arrays.asList( 2 ))),
	KIMIDASHI( new Name( "Kimidasho", Arrays.asList( 2 ))),
	OKURIDASHI( new Name( "Okuridashi", Arrays.asList( 2 ))),
	UWATEDASHINAGE( new Name( "Uwatedashinage", Arrays.asList( 2 ))),
	SHITATEDASHINAGE( new Name( "Shitatedashinage", Arrays.asList( 2 ))),
	KUBINAGE( new Name( "Kubinage", Arrays.asList( 2 ))),
	UCHARI( new Name( "Uchari", Arrays.asList( 2 )));
	
	private Name name;
	
	private Kimarite( Name aName ){
		this.name = aName;
	}
}
