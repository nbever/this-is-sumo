package com.nate.sumo.model.fight;

import java.time.Instant;
import com.nate.model.MD5Animation;

public abstract class FightAction {

	public enum STATUS {
		ATTEMPT,
		EXECUTE,
		RECOVER,
		FAILURE,
		NONE,
		DONE,
		WAITING
	}
	
	public enum DIRECTION {
		MIGI,
		HIDARI,
		FORWARD,
		BACKWARD
	}
	
	private RikishiStatus status;
	private FightKnowledgeIf callback;
	
	private Float startingMedialBalance;
	private Float startingLateralBalance;
	private Float startingEnergy;
	private Float startingMaxEnergy;
	
	private Long startTime = 0L;
	private Long lastTime = 0L;
	private Long currentTime = 0L;
	private Long phaseStartTime;
	
	private STATUS currentStatus = STATUS.ATTEMPT;
	private DIRECTION actionDirection;
	
	private FightCoordinates startingSpot;
	
	private MD5Animation animation;
	private MD5Animation transitionAnimation;
	
	public FightAction( RikishiStatus myStatus, FightKnowledgeIf callback ){
		this.status = myStatus;
		this.callback = callback;
		

	}
	
	public void start(){

		setStartTime();
		initializeActionTimers();
		
		MD5Animation newAnimation = getAnimation();
		
		if ( getMyStatus().getModelAnimationInfo() != null ){
			getMyStatus().getModelAnimationInfo().getModel().setAnimation( newAnimation );
		}
	}
	
	private void initializeActionTimers(){

		setPhaseStartTime();
		currentStatus = STATUS.ATTEMPT;
		currentTime = Instant.now().toEpochMilli();
		lastTime = currentTime;
		this.startingEnergy = getMyStatus().getEnergy();
		this.startingLateralBalance = getMyStatus().getLateralBalance();
		this.startingMedialBalance = getMyStatus().getMedialBalance();
		this.startingMaxEnergy = getMyStatus().getMaxEnergy();
		this.setStartingSpot( getMyStatus().getFightCoordinates() );
	}
	
	public void stop(){
		setCurrentStatus( STATUS.RECOVER );
	}
	
	/**
	 * This is only the time that has elapsed since the 
	 * last time the action was advanced
	 * @return
	 */
	public long getElapsedTime(){
		return currentTime - lastTime;
	}
	
	public long getTotalActionTime(){
		return currentTime - getStartTime();
	}
	
	public void advance(){
		
		lastTime = currentTime;
		currentTime = System.currentTimeMillis();
		
		if ( getStartTime() == 0L ){
			start();
		}
		
		if ( getMyStatus().getModelAnimationInfo().getModel().hasAnimation() && 
			 getMyStatus().getModelAnimationInfo().getModel().getAnimation().isTransitioning() ){
			return;
		}
		
		if ( getMyStatus().getModelAnimationInfo().getModel().hasAnimation() &&
			 getMyStatus().getModelAnimationInfo().getModel().getAnimation().getAnimationTime() == 0.0f ){
			getMyStatus().getModelAnimationInfo().getModel().getAnimation().setAnimationTime( 1.0f );
			initializeActionTimers();
		}
		
		if ( getCurrentStatus().equals( STATUS.ATTEMPT ) &&
			getTotalActionTime() > getTryTime() ){
			
			boolean rez = doSuccessTest();
			
			if ( rez == true ){
				getCallback().reportSuccess();
				setCurrentStatus( STATUS.EXECUTE );
			}
			else {
				setCurrentStatus( STATUS.FAILURE );
			}
		}
		else if ( getCurrentStatus().equals( STATUS.EXECUTE ) ){
			
//			System.out.println( "Total Time: " + getTotalActionTime() + " To enter recovery: " + (getTryTime() + getActionTime() ) );
			
			if( getTotalActionTime() > getTryTime() + getActionTime() ){
				setCurrentStatus( STATUS.RECOVER );				
			}

		}
		else if ( getCurrentStatus().equals(  STATUS.RECOVER ) ){
			
//			System.out.println( "Total Time: " + getTotalActionTime() + " To be done: " + (getTryTime() + getActionTime() + getRecoveryTime() ) );
			
			if ( getTotalActionTime() > getTryTime() + getActionTime() + getRecoveryTime() ){
				setCurrentStatus( STATUS.DONE );				
			}
		}
		
		computeCurrentStatus();
	}
	
	private void computeCurrentStatus(){
		
		ActionResult rez = new ActionResult();
		
		switch( getCurrentStatus() ){
		case ATTEMPT:
			rez = getAttemptActionResult( getElapsedTime() );
			break;
		case EXECUTE:
			rez = getSuccessfulActionResults( getElapsedTime() );
			break;
		default:
			break;
		}
		
		getMyStatus().computeResults( rez );
	}
	
	private void done(){
		
	}
	
	/** Overwrite these ***/
	
	public long getTryTime(){
//		return 2000L;
		
		return (long)getAnimation().getAnimationDuration();
	}
	
	public long getActionTime(){
		return 0L;
	}
	
	public long getRecoveryTime(){
		return 0L;
	}
	
	/**
	 * This is the effect incurred based on the time elapsed.
	 * This value is in terms of differences from current.
	 * @return
	 */
	public ActionResult getAttemptActionResult( long elapsedTime ){
		return null;
	}
	
	public ActionResult getSuccessfulActionResults( long elapsedTime ){
		return null;
	}
	
	
	public boolean doSuccessTest(){
		return true;
	}
	
	protected abstract void advancePhase();
	protected abstract MD5Animation buildAnimation();
	
	// get/sets
	public MD5Animation getAnimation(){
		
		if ( animation == null ){
			animation = buildAnimation();
		}
		
		return animation;
	}
	
	protected RikishiStatus getMyStatus(){
		return status;
	}
	
	private Float getStartingMedialBalance() {
		return startingMedialBalance;
	}

	private Float getStartingLateralBalance() {
		return startingLateralBalance;
	}

	private Float getStartingEnergy() {
		return startingEnergy;
	}

	private Long getStartTime() {
		return startTime;
	}
	
	private void setStartTime() {
		startTime = Instant.now().toEpochMilli();
	}
	
	private Long getPhaseStartTime(){
		return phaseStartTime;
	}
	
	private void setPhaseStartTime(){
		phaseStartTime = Instant.now().toEpochMilli();
	}
	
	public STATUS getCurrentStatus() {
		return currentStatus;
	}

	protected void setCurrentStatus(STATUS currentStatus) {
		this.currentStatus = currentStatus;

		setPhaseStartTime();
	}

	public FightCoordinates getStartingSpot() {
		return startingSpot;
	}

	private void setStartingSpot(FightCoordinates startingSpot) {
		this.startingSpot = startingSpot;
	}

	public DIRECTION getActionDirection()
	{
		return actionDirection;
	}

	public void setActionDirection( DIRECTION actionDirection )
	{
		this.actionDirection = actionDirection;
	}
	
	public FightKnowledgeIf getCallback(){
		return callback;
	}
}
