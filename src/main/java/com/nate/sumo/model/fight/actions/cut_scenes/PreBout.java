package com.nate.sumo.model.fight.actions.cut_scenes;

import java.util.ArrayList;
import java.util.List;

import com.nate.model.MD5Animation;
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

			float xcoord = 3.3f;
			float arriveFacing = 180.0f;
			
			if ( getMyStatus().isEast() ){
				xcoord *= -1.0f;
				arriveFacing = 0.0f;
			}
			
			Pathway p = new Pathway( xcoord, 0.0f, Pathway.DESTINATION, arriveFacing );
			route.addPathway( p );
			
			Stand stand = new Stand( getMyStatus(), getCallback(), (long)((Math.random()*2500)+1000) );
			SlowWalk walk = new SlowWalk( getMyStatus(), getCallback(), route );
			Stand stand2 = new Stand( getMyStatus(), getCallback(), (long)((Math.random()*500)+200) );
			PrepAction prep = new PrepAction( getMyStatus(), getCallback() );
			Crouch crouch = new Crouch( getMyStatus(), getCallback() );
			CrouchIdle crouchIdle = new CrouchIdle( getMyStatus(), getCallback() );
			
			sequence.add( stand );
			sequence.add( walk );
			sequence.add( stand2 );
			sequence.add( prep );
			sequence.add(  crouch );
			sequence.add(  crouchIdle );
		}
		
		return sequence;
	}
	
	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub
		super.advancePhase();
		
		if ( getCutSceneAction() instanceof CrouchIdle ){
			setCurrentStatus( STATUS.WAITING );
		}
	}

	@Override
	protected MD5Animation buildAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

}
