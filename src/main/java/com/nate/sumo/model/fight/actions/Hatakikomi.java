package com.nate.sumo.model.fight.actions;

import java.util.function.Function;

import com.nate.sumo.model.fight.Consequence;
import com.nate.sumo.model.fight.FightAction;

public class Hatakikomi extends FightAction{

	public Hatakikomi( Float energy, Float medialBalance, Float lateralBalance,
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
