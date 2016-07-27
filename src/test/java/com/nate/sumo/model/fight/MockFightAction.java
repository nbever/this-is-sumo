package com.nate.sumo.model.fight;

import com.nate.model.MD5Animation;

public class MockFightAction extends FightAction{

	public MockFightAction(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected MD5Animation buildAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

}
