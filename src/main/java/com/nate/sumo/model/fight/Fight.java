package com.nate.sumo.model.fight;

import com.nate.sumo.model.basho.Match;
import com.nate.sumo.model.rikishi.Rikishi;

public class Fight implements FightKnowledgeIf{

	public static final String EAST_RIKISHI = "E_RIKISHI";
	public static final String WEST_RIKISHI = "W_RIKISHI";
	
	private RikishiStatus eastStatus;
	private RikishiStatus westStatus;
	
	private PHASE phase;
	
	//location
	
	private long elapsedTime;
	
	public Fight( Match match ){
		
		eastStatus = new RikishiStatus( match.getEastRikishi() );
		westStatus = new RikishiStatus( match.getWestRikishi() );
	}
	
	public void advance(){
		
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
	
	public long getElapsedTime(){
		return elapsedTime;
	}

	@Override
	public FightStatus getOpponentStatus(Rikishi me) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reaction reportSuccess() {
		// TODO Auto-generated method stub
		return null;
	}
}
