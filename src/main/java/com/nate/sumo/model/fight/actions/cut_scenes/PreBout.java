package com.nate.sumo.model.fight.actions.cut_scenes;

import java.util.ArrayList;
import java.util.List;

import com.nate.sumo.model.fight.ActionInstructions;
import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.Pathway;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.Route;

public class PreBout extends Scene {

	public PreBout(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
		// TODO Auto-generated constructor stub
	}

	private List<FightAction> sequence;
	
	@Override
	public List<FightAction> getActions() {
		
		if ( sequence == null ){
			sequence = new ArrayList<FightAction>();
			
			Route route = new Route();
			Pathway p = new Pathway( 0.0f, 0.0f, Pathway.DESTINATION, 90.0f );
			route.addPathway( p );
			
			Stand stand = new Stand( getMyStatus(), getCallback(), 3000L );
			SlowWalk walk = new SlowWalk( getMyStatus(), getCallback(), route );
			PrepAction prep = new PrepAction( getMyStatus(), getCallback() );
			
			sequence.add( stand );
			sequence.add( walk );
			sequence.add( prep );
		}
		
		return sequence;
	}

}
