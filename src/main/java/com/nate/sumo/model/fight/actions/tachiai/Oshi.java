package com.nate.sumo.model.fight.actions.tachiai;

import java.util.Arrays;
import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.tachiai.TachiAiAction;

public class Oshi extends TachiAiAction{

	public Oshi(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super(  myStatus, callback );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected MD5Animation buildAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SEQUENCE_KEY> getSequence() {
		return Arrays.asList( SEQUENCE_KEY.TOWARD, SEQUENCE_KEY.RANDOM, SEQUENCE_KEY.TOWARD, SEQUENCE_KEY.UP, SEQUENCE_KEY.UP );
	}
}
