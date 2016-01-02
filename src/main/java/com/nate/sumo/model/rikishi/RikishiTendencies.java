package com.nate.sumo.model.rikishi;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.actions.DashiNage;
import com.nate.sumo.model.fight.actions.Defense;
import com.nate.sumo.model.fight.actions.Gake;
import com.nate.sumo.model.fight.actions.Hatakikomi;
import com.nate.sumo.model.fight.actions.Move;
import com.nate.sumo.model.fight.actions.Nage;
import com.nate.sumo.model.fight.actions.Oshi;
import com.nate.sumo.model.fight.actions.Tsuki;
import com.nate.sumo.model.fight.actions.Utchari;
import com.nate.sumo.model.fight.actions.Yotsu;
import com.nate.sumo.model.fight.actions.tachiai.Harite;
import com.nate.sumo.model.fight.actions.tachiai.Henka;
import com.nate.sumo.model.fight.actions.tachiai.Kachiage;
import com.nate.sumo.model.fight.actions.tachiai.Ketaguri;
import com.nate.sumo.model.fight.actions.tachiai.Nodowa;
import com.nate.sumo.model.fight.scenario.Scenario;
import com.nate.sumo.model.fight.scenario.TachiAiScenario;

public class RikishiTendencies {

	// Map of actions we take and the number out of 100 that we take them.
	private Map<Scenario, Map<Class<? extends FightAction>, Integer>> tendencies;
	
	public Map<Scenario, Map<Class<? extends FightAction>, Integer>> getTendencies(){
		if ( tendencies == null ){
			tendencies = new HashMap<Scenario, Map<Class<? extends FightAction>, Integer>>();
		}
		
		return tendencies;
	}
	
	public List<Class<? extends FightAction>> getClassOrder( Scenario scenario ){
		
		List<Class<? extends FightAction>> classOrder;
		
		if ( scenario instanceof TachiAiScenario ){
			classOrder = Arrays.asList( com.nate.sumo.model.fight.actions.tachiai.Defense.class,
				Harite.class, Henka.class, Kachiage.class, Ketaguri.class, Nodowa.class,
				com.nate.sumo.model.fight.actions.tachiai.Oshi.class,
				com.nate.sumo.model.fight.actions.tachiai.Tsuki.class,
				com.nate.sumo.model.fight.actions.tachiai.Yotsu.class );
		}
		else {
			classOrder = Arrays.asList( Oshi.class, Yotsu.class, Move.class, Nage.class, Tsuki.class, Defense.class, 
				Gake.class, DashiNage.class, Hatakikomi.class, Utchari.class
			);
		}
		
		return classOrder;
	}
	
	public String convertScenarioToString( Scenario scenario ){
		
		Map<Class<? extends FightAction>, Integer> map = getTendencies().get( scenario );

		List<Class<? extends FightAction>> classOrder = getClassOrder( scenario );
		String output = "";
		
		for ( Class<? extends FightAction> clazz : classOrder ){
			output += map.get( clazz ) + ",";
		}
		
		return output;
	}
	
	public Map<Class<? extends FightAction>, Integer> buildMapFromString( Scenario scenario, String input ){
		
		Map<Class<? extends FightAction>, Integer> map = 
			new HashMap<Class<? extends FightAction>, Integer>();
		
		List<Class<? extends FightAction>> classOrder = getClassOrder( scenario );
		
		String[] vals = input.split(",");
		
		for ( int i = 0; i < vals.length; i++ ){
			
			map.put( classOrder.get( i ), Integer.parseInt( vals[i] ) );
		}
		
		return map;
	}
}
