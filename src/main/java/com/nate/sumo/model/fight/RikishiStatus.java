package com.nate.sumo.model.fight;

import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.model.MD5Model;
import com.nate.sumo.model.animation.ModelAnimationInfo;
import com.nate.sumo.model.fight.Fight.PHASE;
import com.nate.sumo.model.fight.actions.cut_scenes.PreBout;
import com.nate.sumo.model.fight.actions.cut_scenes.SlowWalk;
import com.nate.sumo.model.fight.actions.cut_scenes.Stand;
import com.nate.sumo.model.rikishi.Rikishi;

public class RikishiStatus {

	public enum PLAYER_CONTROL{PLAYER_1,PLAYER_2,CPU};
	
	private Rikishi rikishi;
	private Float medialBalance = 0.0f;
	private Float lateralBalance = 0.0f;
	private Float balanceFactor = 1.0f;
	private Float energy = 1000.0f;
	private Float maxEnergy = 1000.0f;
	private boolean east = false;
	
	private PLAYER_CONTROL control = PLAYER_CONTROL.CPU;
	
	private FightCoordinates spot;
	
	private FightAction currentAction;
	
	private FightKnowledgeIf fight;
	
	private List<FightAction> actionHistory;
	
	private ModelAnimationInfo modelInfo;
	
	public RikishiStatus( Rikishi rikishi, Boolean east, FightKnowledgeIf fight ){
		this.rikishi = rikishi;
		this.fight = fight;
		this.east = east;
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
//					setCurrentAction( new Stand( this, getFight() ) );
					setCurrentAction( new PreBout( this, getFight() ) );
					resetAnimation = true;
				}
				break;
			default:
		}
		
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
	
	private void setEnergy( Float val ){
		energy = val;
	}
	
	public Float getMaxEnergy(){
		return maxEnergy;
	}
	
	private void setMaxEnergy( Float val ){
		maxEnergy = val;
	}
	
	public Float getMedialBalance(){
		return medialBalance;
	}
	
	private void setMedialBalance( Float val ){
		medialBalance = val;
	}
	
	public Float getLateralBalance(){
		return lateralBalance;
	}
	
	private void setLateralBalance( Float val ){
		lateralBalance = val;
	}
	
	public Float getBalanceFactor(){
		return balanceFactor;
	}
	
	public void setBalanceFactor( Float val ){
		balanceFactor = val;
	}
	
	public void setControl( PLAYER_CONTROL control ){
		this.control = control;
	}
	
	public PLAYER_CONTROL getControl(){
		return this.control;
	}
	
	public FightKnowledgeIf getFight(){
		return fight;
	}
	
	public FightCoordinates getFightCoordinates(){
		return spot;
	}
	
	private void setFightCoordinates( FightCoordinates val ){
		spot = val;
	}
	
	public boolean isEast(){
		return this.east;
	}
	
	public void computeResults( ActionResult result ){
		
		if ( result == null ){
			return;
		}
		
		setEnergy( getEnergy() + result.getPowerEffect() );
		setMaxEnergy( getMaxEnergy() + result.getMaxPowerEffect() );
		setMedialBalance( getMedialBalance() + result.getMedialBalanceEffect() );
		setLateralBalance( getLateralBalance() + result.getLateralBalanceEffect() );
		
		FightCoordinates coords = new FightCoordinates();
		
		coords.setX( getFightCoordinates().getX() + result.getxPositionEffect() );
		coords.setY( getFightCoordinates().getY() + result.getyPositionEffect() );
		coords.setFacing( (getFightCoordinates().getFacing() + result.getPositionDirection()) % 360.0f );
		setFightCoordinates( coords );
	
	}
}
