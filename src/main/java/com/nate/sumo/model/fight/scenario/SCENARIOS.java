package com.nate.sumo.model.fight.scenario;

import com.nate.sumo.model.fight.RikishiStatus;

public enum SCENARIOS {

	WINNING( new WinningScenario()),
	LOSING( new LosingScenario()),
	EDGE_DANGER( new EdgeDangerScenario()),
	EDGE_VICTORY( new EdgeVictoryScenario()),
	TACHI_AI( new TachiAiScenario());
	
	private Scenario scenarioImpl;
	
	private SCENARIOS( Scenario aScenario ){
		scenarioImpl = aScenario;
	}
	
	public Scenario getScenario(){
		return scenarioImpl;
	}
	
	public Double getMatch( RikishiStatus me, RikishiStatus them ){
		return scenarioImpl.getWeightMatch( me, them );
	}
	
}
