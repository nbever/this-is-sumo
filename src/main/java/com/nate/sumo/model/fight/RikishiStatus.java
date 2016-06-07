package com.nate.sumo.model.fight;

import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.model.MD5Model;
import com.nate.sumo.model.animation.ModelAnimationInfo;
import com.nate.sumo.model.fight.Fight.PHASE;
import com.nate.sumo.model.fight.actions.cut_scenes.SlowWalk;
import com.nate.sumo.model.fight.actions.cut_scenes.Stand;
import com.nate.sumo.model.rikishi.Rikishi;

public class RikishiStatus {

	private Rikishi rikishi;
	private Float medialBalance;
	private Float lateralBalance;
	private Float energy;
	private Float maxEnergy;
	
	private FightCoordinates spot;
	
	private FightAction currentAction;
	
	private FightKnowledgeIf fight;
	
	private List<FightAction> actionHistory;
	
	private ModelAnimationInfo modelInfo;
	
	public RikishiStatus( Rikishi rikishi, Boolean east, FightKnowledgeIf fight ){
		this.rikishi = rikishi;
		this.fight = fight;
		this.spot = new FightCoordinates();
		
		if ( east ){
			this.spot.setEastPreFight();
		}
		else {
			this.spot.setWestPreFight();
		}
	}
	
	public void load(){
		modelInfo = new ModelAnimationInfo( getRikishi().getAppearenceMap() );
	}
	
	public void advance(){
		// make decision
		
		// action = decide
		// if action == null - continue
		// else replace
		boolean resetAnimation = false;
		
		switch( getFight().getPhase() ){
			case PRE_FIGHT:
				if ( getCurrentAction() == null ){
					setCurrentAction( new Stand( this, getFight() ) );
					resetAnimation = true;
				}
				break;
			case PREP:
				if ( getCurrentAction() instanceof Stand ){
					setCurrentAction( new SlowWalk( this, getFight(), new Route() ) );
					resetAnimation = true;
				}
			default:
		}
		
//		if ( resetAnimation == true ){
//			
//			modelInfo.getModel().setAnimation( getCurrentAction().getAnimation() );
//		}
		
		long elapsedTime = 0L;
		
		if ( getCurrentAction() != null ){
			getCurrentAction().advance();
			
			elapsedTime = getCurrentAction().getElapsedTime();
		}
		
		getModelAnimationInfo().update( elapsedTime );
	}
	
	/**
	 * This allows the status to inject some variables into the real drawing.
	 */
	public void draw(){
		getModelAnimationInfo().draw( getFightCoordinates() );
	}
	
	public Rikishi getRikishi(){
		return rikishi;
	}
	
	public ModelAnimationInfo getModelAnimationInfo(){
		return modelInfo;
	}
	
	public FightAction getCurrentAction(){
		return currentAction;
	}
	
	private void setCurrentAction( FightAction action ){
		currentAction = action;
	}
	
	public Float getEnergy(){
		return energy;
	}
	
	public Float getMaxEnergy(){
		return maxEnergy;
	}
	
	public Float getMedialBalance(){
		return medialBalance;
	}
	
	public Float getLateralBalance(){
		return lateralBalance;
	}
	
	public FightKnowledgeIf getFight(){
		return fight;
	}
	
	public FightCoordinates getFightCoordinates(){
		return spot;
	}
}
