package com.nate.sumo.model.fight;

import java.time.Instant;
import java.util.function.Function;

public abstract class FightAction {

	public enum STATUS {
		ATTEMPT,
		EXECUTE,
		RECOVER,
		NONE
	}
	
	public enum PHASE_STATUS {
		IN_PROGRESS,
		SUCCESS,
		FAILURE
	}
	
	public enum DIRECTION {
		MIGI,
		HIDARI,
		FORWARD,
		BACKWARD
	}
	
	private Float startingMedialBalance;
	private Float startingLateralBalance;
	private Float startingEnergy;
	private Long startTime;
	private Long phaseStartTime;
	
	private STATUS currentStatus;
	private DIRECTION actionDirection;
	
	private FightCoordinates startingSpot;
	
	private Function<Consequence, Void> callback;
	
	public FightAction( Float energy, Float medialBalance, Float lateralBalance, Function<Consequence, Void> callback ){
		this.startingEnergy = new Float( energy );
		this.startingLateralBalance = new Float( lateralBalance );
		this.startingMedialBalance = new Float( medialBalance );
	}
	
	public void start(){
		setStartTime();
	}
	
	public void stop(){
		setCurrentStatus( STATUS.RECOVER );
	}
	
	private void done(){
		
	}
	
	protected abstract void advancePhase();
	protected abstract PHASE_STATUS getPhaseStatus();
	
	// get/sets	
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

	private Function<Consequence, Void> getCallback() {
		return callback;
	}

	private void setCallback(Function<Consequence, Void> callback) {
		this.callback = callback;
	}

	public DIRECTION getActionDirection()
	{
		return actionDirection;
	}

	public void setActionDirection( DIRECTION actionDirection )
	{
		this.actionDirection = actionDirection;
	}
}
