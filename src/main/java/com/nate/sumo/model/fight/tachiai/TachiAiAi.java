package com.nate.sumo.model.fight.tachiai;

import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.actions.tachiai.Oshi;

public class TachiAiAi {

	public TachiAiAi(){}
	
	public TachiAiAction chooseTachiAi( FightKnowledgeIf fightKnowledge, RikishiStatus myStatus ){
		
		return new Oshi( myStatus, fightKnowledge );
	}
	
}
