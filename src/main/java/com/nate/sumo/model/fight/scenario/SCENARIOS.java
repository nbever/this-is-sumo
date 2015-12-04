package com.nate.sumo.model.fight.scenario;

import com.nate.sumo.model.fight.RikishiStatus;

public enum SCENARIOS {

	WINNING( new WinningScenario()),
	LOSING( new LosingScenario()),
	EDGE_DANGER( new EdgeDangerScenario()),
	EDGE_VICTORY( new EdgeVictoryScenario()),
	TIRED( new TiredScenario()),
	OPPONENT_TIRED( new OpponentTiredScenario()),
	HAVE_PREFERRED_GRIP( new PreferredGridScenario()),
	OPPONENT_FAVORITE_GRIP( new OpponentPreferredGripScenario());
	
	private Scenario scenarioImpl;
	
	private SCENARIOS( Scenario aScenario ){
		scenarioImpl = aScenario;
	}
	
	public Double getMatch( RikishiStatus me, RikishiStatus them ){
		return scenarioImpl.getWeightMatch( me, them );
	}
	
}
