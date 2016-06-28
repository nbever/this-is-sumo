package com.nate.sumo.model;

import com.nate.sumo.model.fight.ActionResult;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.rikishi.Rikishi;

public class MockRikishiStatus extends RikishiStatus {

	public MockRikishiStatus(Rikishi rikishi, Boolean east,
			FightKnowledgeIf fight) {
		super( rikishi, east, fight );
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void computeResults(ActionResult result) {

	}

}
