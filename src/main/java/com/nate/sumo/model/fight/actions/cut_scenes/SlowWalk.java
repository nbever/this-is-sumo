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
	
	public SlowWalk(RikishiStatus myStatus, FightKnowledgeIf callback, Route route ) {
		super( myStatus, callback );
		
		this.route = route;
		currentPathway = this.route.getNextPath();
	}
	
	@Override
	public MD5Animation getAnimation() {
	
		if ( animation == null ){
			animation = AnimationManager.getInstance().loadAnimation( "slowwalk.md5anim" );
			animation.setRepeat( true );
		}
		
		return animation;
	}
	
	@Override
	public ActionResult getSuccessfulActionResults(long elapsedTime) {
		
		ActionResult rez = new ActionResult();
		
		//TODO: Calculate rate based on speed
		Float rate = elapsedTime * (1.0f / 1000.0f);
		
		double angle = Math.atan2( getCurrentPathway().getxDest() - getMyStatus().getFightCoordinates().getX(), 
			getCurrentPathway().getyDest() - getMyStatus().getFightCoordinates().getY() );
		
		double x = rate * Math.sin( angle );
		double y = rate * Math.cos( angle );
		
		boolean right = (getCurrentPathway().getxDest() - getStartingSpot().getX() > 0);
		boolean up = (getCurrentPathway().getyDest() - getStartingSpot().getY() > 0);
		
		if ( (getCurrentPathway().getxDest() - (getMyStatus().getFightCoordinates().getX() + x) > 0) != right ){
			x = getCurrentPathway().getxDest();
		}
		
		if ( (getCurrentPathway().getyDest() - (getMyStatus().getFightCoordinates().getY() + y) > 0) != up ){
			y = getCurrentPathway().getyDest();
		}
		
		rez.setxPositionEffect( (float)x );
		rez.setyPositionEffect( (float)y );
		
		return rez;
	}
	
	@Override
	public void advance() {
		
		if ( didIArrive() ){
			setCurrentStatus( STATUS.DONE );
		}
		
		super.advance();
	}
	
	private boolean didIArrive(){
		
		System.out.println( getMyStatus().getFightCoordinates().getX() + ", " + getMyStatus().getFightCoordinates().getY() +
			" vs. " + getCurrentPathway().getxDest() + ", " + getCurrentPathway().getyDest() );
		
		if ( 
			getCurrentPathway() == null || 
			(getMyStatus().getFightCoordinates().getX() == getCurrentPathway().getxDest() &&
			getMyStatus().getFightCoordinates().getY() == getCurrentPathway().getyDest() )){
			
			System.out.println( "They are equal" );
			
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
