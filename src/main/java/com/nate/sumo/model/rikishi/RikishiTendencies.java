package com.nate.sumo.model.rikishi;

import java.util.HashMap;
import java.util.Map;

public class RikishiTendencies {

	private Map<Scenario, Integer> tendencies;
	
	public Map<Scenario, Integer> getTendencies(){
		if ( tendencies == null ){
			tendencies = new HashMap<Scenario, Integer>();
		}
		
		return tendencies;
	}
}
