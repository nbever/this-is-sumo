package com.nate.sumo.model.fight.actions.cut_scenes;

import java.util.List;

import com.nate.sumo.model.fight.ActionInstructions;
import com.nate.sumo.model.fight.ActionResult;
import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;

public abstract class Scene extends FightAction {

	private int index = 0;
	
	public Scene(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
		// TODO Auto-generated constructor stub
	}
	
	public abstract List<FightAction> getActions();

	@Override
	protected void advancePhase() {
		
	}
	
	@Override
	public void advance() {
		
		FightAction action = getActions().get( getIndex() );
		
		if ( action.getAnimation().isFinished() || action.getCurrentStatus().equals( STATUS.DONE )){
			// go to the next action
			
			if ( (index+1) == getActions().size() ){
				setCurrentStatus( STATUS.DONE );
				return;
			}
			
			index++;
			action.stop();
			action = getActions().get( getIndex() );
			action.start();
		}
		
		action.advance();
	}
	
	@Override
	public long getElapsedTime() {
	
		return getActions().get( getIndex() ).getElapsedTime();
	}
	
	private int getIndex(){
		return index;
	}

}
