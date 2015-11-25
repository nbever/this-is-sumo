package com.nate.sumo.model.basho;

import java.util.Arrays;

import com.nate.sumo.model.common.Name;

public enum Division {

	MAKUNOUCHI( new Name( "Makunouchi", Arrays.asList( 1 ))),
	JURYO( new Name( "Juryo", Arrays.asList( 2 ))),
	MAKUSHITA( new Name( "Makushita", Arrays.asList( 3 ))),
	SANDANME( new Name( "Sandanme", Arrays.asList( 3 ))),
	JONIDAN( new Name( "Jonidan", Arrays.asList( 2 ))),
	JONOKUCHI( new Name( "Jonokuchi", Arrays.asList( 1 ))),
	MAE_ZUMO( new Name( "Mae-zumo", Arrays.asList( 3 )));
	
	private Name name;
	
	private Division( Name aName ){
		name = aName;
	}
	
	public Name getName(){
		return name;
	}
}
