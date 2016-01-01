package com.nate.sumo.model.fight.actions.tachiai;

import java.util.function.Function;

import com.nate.sumo.model.fight.Consequence;

public class Yotsu extends TachiAiAction
{

	public Yotsu( Float energy, Float medialBalance, Float lateralBalance,
			Function<Consequence, Void> callback )
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
