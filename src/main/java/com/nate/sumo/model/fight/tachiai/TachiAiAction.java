package com.nate.sumo.model.fight.tachiai;

import com.nate.sumo.display.widgets.SequenceIf;
import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;

public abstract class TachiAiAction extends FightAction implements SequenceIf
{

	public TachiAiAction( RikishiStatus status, FightKnowledgeIf callback )
	{
		super( status, callback );
	}

	@Override
	protected void advancePhase()
	{
		// TODO Auto-generated method stub

	}

}
