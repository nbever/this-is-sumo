package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.RikishiStatus;

public class Stand extends NonInteractionAction {

	public Stand(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
	}

	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub

	}

}
