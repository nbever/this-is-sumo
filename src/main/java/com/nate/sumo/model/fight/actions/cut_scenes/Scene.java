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

	protected FightAction getCutSceneAction(){
		return getActions().get( getIndex() );
	}
	
	@Override
	protected void advancePhase() {
		
	}
	
	@Override
	public void advance() {
		
		advancePhase();
		
		FightAction action = getCutSceneAction();
		
		if ( action.getAnimation().isFinished() || action.getCurrentStatus().equals( STATUS.DONE )){
			// go to the next action
			
			if ( (index+1) == getActions().size() ){
				setCurrentStatus( STATUS.DONE );
				return;
			}
			
			index++;
			action.stop();
			action = getActions().get( getIndex() );
			System.out.println( "Starting: " + action.getClass().getName() );
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
