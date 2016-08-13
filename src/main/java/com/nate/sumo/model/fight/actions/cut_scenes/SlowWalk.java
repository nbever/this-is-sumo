package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.ActionResult;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.Pathway;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.Route;
import com.nate.util.MathHelper;

public class SlowWalk extends NonInteractionAction{

	private Route route;
	private Pathway currentPathway;
	
	private static final Double TURN_SIZE = 1.0;
	
	public SlowWalk(RikishiStatus myStatus, FightKnowledgeIf callback, Route route ) {
		super( myStatus, callback );
		
		this.route = route;
		
		if ( route != null ){
			currentPathway = this.route.getNextPath();
		}
	}
	
	@Override
	protected MD5Animation buildAnimation() {
	
		MD5Animation animation = AnimationManager.getInstance().loadAnimation( AnimationManager.DEFAULT_FOLDER + "/slowwalk.md5anim" );
		
		return animation;
	}
	
	@Override
	public ActionResult getSuccessfulActionResults(long elapsedTime) {
		
		ActionResult rez = new ActionResult();
		
		//TODO: Calculate rate based on speed
		Float rate = getWalkRate( elapsedTime );
		Double turnRate = getTurnRate();
		
		double angle = getAngleToDest();
		
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
		
		double wannaFace = getFacingDesire();
		double dist = getDistanceFromDestination();
		
		if ( dist < (rate*30) ){
			wannaFace = getCurrentPathway().getArrivalFacing();
			turnRate = 2.0;
		}
		
		// need wannaFace to be positive to simplify comparisons
		if ( wannaFace < 0 ){
			wannaFace += 360.0;
		}
		
		float turnDiff = 0.0f;
		
		if ( Math.abs( wannaFace - getMyStatus().getFightCoordinates().getFacing() ) > 0.001 ){
			
			turnDiff = turn( wannaFace, getMyStatus().getFightCoordinates().getFacing(), turnRate );
			rez.setPositionDirection( turnDiff );
		}
		
		rez.setxPositionEffect( (float)x );
		rez.setyPositionEffect( (float)y );
		
		return rez;
	}
	
	protected double getDistanceFromDestination(){
		
		// if you are close to the destination we will change the facing desire
		double xDiff = getCurrentPathway().getxDest() - getMyStatus().getFightCoordinates().getX();
		double yDiff = getCurrentPathway().getyDest() - getMyStatus().getFightCoordinates().getY();
		
		double dist = Math.abs( Math.hypot( xDiff, yDiff ) );

		return dist;
	}
	
	protected double getFacingDesire(){
		
		double angle = getAngleToDest();
		
		double wannaFace = (angle / (2*Math.PI)) * 360.0;
		
		if ( getCurrentPathway().getMoveFacing() != Pathway.DESTINATION ){
			wannaFace = getCurrentPathway().getMoveFacing();
		}
		
		return wannaFace;
	}
	
	protected double getAngleToDest(){
		
		double y = getCurrentPathway().getyDest() - getMyStatus().getFightCoordinates().getY();
		double x = getCurrentPathway().getxDest() - getMyStatus().getFightCoordinates().getX();
		double angle = MathHelper.atan_full( x, y );
			
		return angle;
	}
	
	protected float getWalkRate( long elapsedTime ){
		return elapsedTime * (1.5f / 1000.0f);
	}
	
	protected Double getTurnRate(){
		return 1.0;
	}
	
	/**
	 * Returns the amount to turn
	 * 
	 * right is -
	 * left is +
	 * 
	 * Calculations are in degrees!
	 * 
	 * @param wannFace
	 * @param facing
	 * @return
	 */
	protected float turn( double wannaFace, double facing, double turnRate ){
		
		if ( MathHelper.equals( wannaFace, facing ) ){
			return 0.0f;
		}
		
		// turn the negatives into positive
		double diff = wannaFace - facing;
		double absDiff = Math.abs( diff );
		double otherWay = 360.0 - absDiff;
		
		double wayToGo = turnRate;
		
		if ( absDiff < otherWay && diff < 0 ){
			wayToGo *= -1.0;
		}
		else if ( absDiff > otherWay && diff > 0 ){
			wayToGo *= -1.0;
		}
		
		return (float)wayToGo;
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
