package com.nate.sumo.model.fight.actions;

import com.nate.model.MD5Animation;
import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;

public class Nage extends FightAction {

	public Nage( RikishiStatus myStatus, FightKnowledgeIf callback )
	{
		super( myStatus, callback);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected MD5Animation buildAnimation() {
		// TODO Auto-generated method stub
		return null;
	}
}
