package com.nate.sumo.model.fight;

import com.nate.sumo.model.basho.Match;

public class FightStatus {

	private RikishiStatus eastStatus;
	private RikishiStatus westStatus;
	
	private long elapsedTime;
	
	public FightStatus( Match match ){
		
	}
	
	public RikishiStatus getEastStatus(){
		return eastStatus;
	}
	
	public RikishiStatus getWestStatus(){
		return westStatus;
	}
}
