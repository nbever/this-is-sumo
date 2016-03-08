package com.nate.sumo.model.fight;

import java.util.List;

import com.nate.sumo.model.rikishi.Rikishi;

public class RikishiStatus {

	private Rikishi rikishi;
	private Float medialBalance;
	private Float lateralBalance;
	private Float energy;
	private Float maxEnergy;
	
	private FightCoordinates spot;
	
	private FightAction currentAction;
	
	private List<FightAction> actionHistory;
	
	public RikishiStatus( Rikishi rikishi ){
		this.rikishi = rikishi;
	}
	
	public Rikishi getRikishi(){
		return rikishi;
	}
}
