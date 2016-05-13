package com.nate.sumo.model.fight;

import com.nate.sumo.model.rikishi.Rikishi;

public interface FightKnowledgeIf {

	public FightStatus getOpponentStatus( Rikishi me );
	public Reaction reportSuccess();
}
