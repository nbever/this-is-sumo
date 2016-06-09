package com.nate.sumo.model.fight;

public abstract class NonInteractionAction extends FightAction {

	public NonInteractionAction(RikishiStatus myStatus,
			FightKnowledgeIf callback) {
		super( myStatus, callback );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub

	}

}
