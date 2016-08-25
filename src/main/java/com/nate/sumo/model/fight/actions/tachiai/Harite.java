package com.nate.sumo.model.fight.actions.tachiai;

import java.util.Arrays;
import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.tachiai.TachiAiAction;

public class Harite extends TachiAiAction
{

	public Harite( RikishiStatus myStatus, FightKnowledgeIf callback )
	{
		super( myStatus, callback);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MD5Animation buildAnimation() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public List<SEQUENCE_KEY> getSequence() {
		return Arrays.asList( SEQUENCE_KEY.TOWARD, SEQUENCE_KEY.UP, SEQUENCE_KEY.TOWARD, SEQUENCE_KEY.UP, SEQUENCE_KEY.DOWN, SEQUENCE_KEY.RANDOM,
			SEQUENCE_KEY.RANDOM, SEQUENCE_KEY.DOWN, SEQUENCE_KEY.RANDOM );
	}	

}
