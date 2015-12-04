package com.nate.sumo.model.fight.scenario;

import com.nate.sumo.model.fight.RikishiStatus;

public abstract class Scenario {

	private String name;
	
	public abstract Double getWeightMatch( RikishiStatus me, RikishiStatus them );
}
