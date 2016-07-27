package com.nate.sumo.model.fight.actions.tachiai;

import com.nate.model.MD5Animation;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.TachiAiAction;

public class Defense extends TachiAiAction{

	public Defense( RikishiStatus myStatus, FightKnowledgeIf callback )
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
