package com.nate.sumo.model.fight;

public class ActionInstructions {

	public static final long FULL = -1L;
	
	private FightAction action;
	private long duration = FULL;
	
	public ActionInstructions( FightAction action, long duration ){
		this.action = action;
		this.duration = duration;
	}
	
	public FightAction getAction() {
		return action;
	}
	
	public void setAction(FightAction action) {
		this.action = action;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
