package com.nate.sumo.model.fight.actions;

import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;

public class Move extends FightAction
{

	public Move( RikishiStatus myStatus, FightKnowledgeIf callback )
	{
		super(myStatus, callback);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase()
	{
		// TODO Auto-generated method stub

	}
}
