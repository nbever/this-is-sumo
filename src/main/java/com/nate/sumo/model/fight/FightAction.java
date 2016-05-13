package com.nate.sumo.model.fight;

import java.time.Instant;
import java.util.function.Function;

import com.nate.model.MD5Animation;

public abstract class FightAction {

	public enum STATUS {
		ATTEMPT,
		EXECUTE,
		RECOVER,
		FAILURE,
		NONE
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
	
	private Long startTime;
	private Long lastTime = 0L;
	private Long currentTime = 0L;
	private Long phaseStartTime;
	
	private STATUS currentStatus;
	private DIRECTION actionDirection;
	
	private FightCoordinates startingSpot;
	
	private MD5Animation animation;
	
	public FightAction( RikishiStatus myStatus, FightKnowledgeIf callback ){
		this.status = myStatus;
		this.callback = callback;
		
		this.startingEnergy = myStatus.getEnergy();
		this.startingLateralBalance = myStatus.getLateralBalance();
		this.startingMedialBalance = myStatus.getMedialBalance();
		this.startingMaxEnergy = myStatus.getMaxEnergy();
	}
	
	public void start(){
		setStartTime();
		setPhaseStartTime();
		currentStatus = STATUS.ATTEMPT;
	}
	
	public void stop(){
		setCurrentStatus( STATUS.RECOVER );
	}
	
	public long getElapsedTime(){
		return currentTime - lastTime;
	}
	
	public void advance(){
		lastTime = currentTime;
		currentTime = System.currentTimeMillis();
		
		if ( getCurrentStatus().equals( STATUS.ATTEMPT ) &&
			getElapsedTime() > getTryTime() ){
			boolean rez = doSuccessTest();
			
			if ( rez == true ){
				getCallback().reportSuccess();
				setCurrentStatus( STATUS.EXECUTE );
			}
			else {
				setCurrentStatus( STATUS.FAILURE );
			}
		}
		else if ( getCurrentStatus().equals( STATUS.EXECUTE ) &&
			getElapsedTime() > getTryTime() + getActionTime() &&
			!( this instanceof NonInteractionAction )){
			setCurrentStatus( STATUS.RECOVER );
		}
	}
	
	private void done(){
		
	}
	
	/** Overwrite these ***/
	
	public long getTryTime(){
		return 2000L;
	}
	
	public long getActionTime(){
		return 3000L;
	}
	
	public long getRecoveryTime(){
		return 1000L;
	}
	
	/**
	 * This is the effect in curred based on the time elapsed.
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
	
	// get/sets
	public MD5Animation getAnimation(){
		return animation;
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
	
	private STATUS getCurrentStatus() {
		return currentStatus;
	}

	private void setCurrentStatus(STATUS currentStatus) {
		this.currentStatus = currentStatus;
		setPhaseStartTime();
	}

	private FightCoordinates getStartingSpot() {
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
