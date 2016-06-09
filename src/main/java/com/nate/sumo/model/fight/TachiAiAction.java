package com.nate.sumo.model.fight;

public abstract class TachiAiAction extends FightAction
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
