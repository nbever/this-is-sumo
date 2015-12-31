package com.nate.sumo.model.rikishi;

import java.util.HashMap;
import java.util.Map;

import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.scenario.Scenario;

public class RikishiTendencies {

	// Map of actions we take and the number out of 100 that we take them.
	private Map<Scenario, Map<Class<? extends FightAction>, Integer>> tendencies;
	
	public Map<Scenario, Map<Class<? extends FightAction>, Integer>> getTendencies(){
		if ( tendencies == null ){
			tendencies = new HashMap<Scenario, Map<Class<? extends FightAction>, Integer>>();
		}
		
		return tendencies;
	}
}
