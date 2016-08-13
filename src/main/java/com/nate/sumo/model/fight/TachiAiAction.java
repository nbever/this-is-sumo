package com.nate.sumo.model.fight;

import com.nate.sumo.display.widgets.SequenceIf;

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
