package com.nate.sumo.model.fight.actions.tachiai;

import java.util.function.Function;

import com.nate.sumo.model.fight.Consequence;
import com.nate.sumo.model.fight.TachiAiAction;

public class Harite extends TachiAiAction
{

	public Harite( Float energy, Float medialBalance, Float lateralBalance,
			Function<Consequence, Void> callback )
	{
		super(energy, medialBalance, lateralBalance, callback);
		// TODO Auto-generated constructor stub
	}

}
