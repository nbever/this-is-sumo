package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.ActionResult;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.Pathway;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.Route;

public class SlowWalk extends NonInteractionAction{

	private Route route;
	private Pathway currentPathway;
	
	private static final Double TURN_SIZE = 1.0;
	
	public SlowWalk(RikishiStatus myStatus, FightKnowledgeIf callback, Route route ) {
		super( myStatus, callback );
		
		this.route = route;
		currentPathway = this.route.getNextPath();
	}
	
	@Override
	public MD5Animation getAnimation() {
	
		if ( animation == null ){
			animation = AnimationManager.getInstance().loadAnimation( AnimationManager.DEFAULT_FOLDER + "/slowwalk.md5anim" );
			animation.setRepeat( true );
		}
		
		return animation;
	}
	
	@Override
	public ActionResult getSuccessfulActionResults(long elapsedTime) {
		
		ActionResult rez = new ActionResult();
		
		//TODO: Calculate rate based on speed
		Float rate = elapsedTime * (1.0f / 1000.0f);
		
		double angle = Math.atan2( getCurrentPathway().getyDest() - getMyStatus().getFightCoordinates().getY(), 
			getCurrentPathway().getxDest() - getMyStatus().getFightCoordinates().getX() );
		
		double y = rate * Math.sin( angle );
		double x = rate * Math.cos( angle );
		
		boolean right = (getCurrentPathway().getxDest() - getStartingSpot().getX() > 0);
		boolean up = (getCurrentPathway().getyDest() - getStartingSpot().getY() > 0);
		
		if ( (getCurrentPathway().getxDest() - (getMyStatus().getFightCoordinates().getX() + x) > 0) != right ){
			x = getCurrentPathway().getxDest() - getMyStatus().getFightCoordinates().getX();
		}
		
		if ( (getCurrentPathway().getyDest() - (getMyStatus().getFightCoordinates().getY() + y) > 0) != up ){
			y = getCurrentPathway().getyDest() - getMyStatus().getFightCoordinates().getY();
		}
		
		double wannaFace = (angle / (2*Math.PI)) * 360.0;
		
		if ( getCurrentPathway().getMoveFacing() != Pathway.DESTINATION ){
			wannaFace = getCurrentPathway().getMoveFacing();
		}
		
		// need wannaFace to be positive to simplify comparisons
		if ( wannaFace < 0 ){
			wannaFace += 360.0;
		}
		
		if ( Math.abs( wannaFace - getMyStatus().getFightCoordinates().getFacing() ) > 0.001 ){
			
			float turnDiff = turn( wannaFace, getMyStatus().getFightCoordinates().getFacing() );
			rez.setPositionDirection( turnDiff );
		}
		
		rez.setxPositionEffect( (float)x );
		rez.setyPositionEffect( (float)y );
		
		return rez;
	}
	
	/**
	 * Returns the amount to turn
	 * @param wannFace
	 * @param facing
	 * @return
	 */
	private float turn( double wannaFace, double facing ){
		
		double turnRight = Math.abs( (facing + 360.0) - wannaFace );
		double turnLeft = Math.abs( facing - wannaFace );
		
		double turn = turnRight;
		
		if ( turnLeft < turnRight ){
			turn = turnLeft;
		}
		
		if ( Math.abs( turn ) > TURN_SIZE ){
			turn = TURN_SIZE;
		}
		
		if ( turnLeft > turnRight ){
			turn *= -1.0;
		}
		
		return (float)turn;
	}
	
	@Override
	public void advance() {
		
		if ( didIArrive() ){
			setCurrentStatus( STATUS.DONE );
			getMyStatus().getFightCoordinates().setFacing( getCurrentPathway().getArrivalFacing() );
		}
		
		super.advance();
	}
	
	private boolean didIArrive(){
		
		if ( 
			getCurrentPathway() == null || 
			(getMyStatus().getFightCoordinates().getX() == getCurrentPathway().getxDest() &&
			getMyStatus().getFightCoordinates().getY() == getCurrentPathway().getyDest() )){
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public long getTotalActionTime() {
		if ( getCurrentStatus().equals( STATUS.EXECUTE ) ){
			return -1L;
		}
		else {
			return super.getTotalActionTime();
		}
	}
	
	@Override
	public long getTryTime() {
		
		return 0L;
	}
	
	@Override
	public long getRecoveryTime() {
	
		return 0L;
	}
	
	private Route getRoute(){
		return route;
	}
	
	private Pathway getCurrentPathway(){
		return currentPathway;
	}

}
