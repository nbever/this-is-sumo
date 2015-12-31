package com.nate.sumo.model.rikishi;

public class RikishiTemperment {

	// This effects how the rikishi deals with pressure
	private Double focus = 500.0;
	
	// this is an x-factor that can either proved a boost or 
	// cause bad decisions
	private Double anger = 500.0;
	
	// How hard they work to get better
	private Double drive = 500.0;
	
	// How emotionally fragile they are.  
	// This one helps know how this rikishi responds
	// to Oyakata.
	private Double emotions = 500.0;
	
	// this drives the rikishi's ability to make good decisions in the fight.
	// basically it means the information they get about the opponents status
	// will be more correct.
	private Double iq = 500.0;
	
	public RikishiTemperment(){}

	public Double getFocus() {
		return focus;
	}

	public void setFocus(Double focus) {
		this.focus = focus;
	}

	public Double getAnger() {
		return anger;
	}

	public void setAnger(Double anger) {
		this.anger = anger;
	}

	public Double getDrive() {
		return drive;
	}

	public void setDrive(Double drive) {
		this.drive = drive;
	}

	public Double getIq() {
		return iq;
	}

	public void setIq(Double iq) {
		this.iq = iq;
	}

	public Double getEmotions()
	{
		return emotions;
	}

	public void setEmotions( Double emotions )
	{
		this.emotions = emotions;
	}
	
	
	
}
