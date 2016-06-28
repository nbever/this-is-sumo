package com.nate.sumo.model;

import com.nate.sumo.model.fight.Fight.PHASE;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.FightStatus;
import com.nate.sumo.model.fight.Reaction;
import com.nate.sumo.model.rikishi.Rikishi;

public class MockFight implements FightKnowledgeIf {

	@Override
	public FightStatus getOpponentStatus(Rikishi me) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PHASE getPhase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reaction reportSuccess() {
		// TODO Auto-generated method stub
		return null;
	}

}
