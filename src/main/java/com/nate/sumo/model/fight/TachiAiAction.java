package com.nate.sumo.model.fight;

import java.util.function.Function;

public abstract class TachiAiAction extends FightAction
{

	public TachiAiAction( Float energy, Float medialBalance,
			Float lateralBalance, Function<Consequence, Void> callback )
	{
		super(energy, medialBalance, lateralBalance, callback);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected PHASE_STATUS getPhaseStatus()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
