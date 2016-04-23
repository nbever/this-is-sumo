package com.nate.sumo.model.fight;

import com.nate.sumo.model.basho.Match;

public class FightStatus {

	public static final String EAST_RIKISHI = "E_RIKISHI";
	public static final String WEST_RIKISHI = "W_RIKISHI";
	
	private RikishiStatus eastStatus;
	private RikishiStatus westStatus;
	
	private PHASE phase;
	
	//location
	
	private long elapsedTime;
	
	public FightStatus( Match match ){
		
		eastStatus = new RikishiStatus( match.getEastRikishi() );
		westStatus = new RikishiStatus( match.getWestRikishi() );
	}
	
	public RikishiStatus getEastStatus(){
		return eastStatus;
	}
	
	public RikishiStatus getWestStatus(){
		return westStatus;
	}
	
	public PHASE getPhase(){
		return phase;
	}
	
	public enum PHASE{
		PRE_FIGHT, PREP, TACHI_AI, FIGHT, FINISH, POST_FIGHT;
	}
}
