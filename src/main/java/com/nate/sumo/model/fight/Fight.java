package com.nate.sumo.model.fight;

import java.time.Instant;

import com.nate.sumo.model.basho.Match;
import com.nate.sumo.model.fight.actions.cut_scenes.Stand;
import com.nate.sumo.model.rikishi.Rikishi;

public class Fight implements FightKnowledgeIf{

	public static final String EAST_RIKISHI = "E_RIKISHI";
	public static final String WEST_RIKISHI = "W_RIKISHI";
	
	private RikishiStatus eastStatus;
	private RikishiStatus westStatus;
	
	private PHASE phase;
	
	//location
	private long startTime = 0L;
	
	public Fight( Match match ){
		
		phase = PHASE.PRE_FIGHT;
		eastStatus = new RikishiStatus( match.getEastRikishi(), true, this );
		westStatus = new RikishiStatus( match.getWestRikishi(), false, this );
	}
	
	public void advance(){
		
		if ( startTime == 0L ){
			startTime = Instant.now().toEpochMilli();
		}
//		
//		if ( getElapsedTime() > 3000L && bothRikishiAreStill() && phase != PHASE.POST_FIGHT ){
//			phase = PHASE.values()[getPhase().ordinal()+1];
//		}
//		
		getEastStatus().advance();
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
		return Instant.now().toEpochMilli() - getStartTime();
	}
	
	private long getStartTime(){
		return startTime;
	}
	
	private boolean bothRikishiAreStill(){
		
		if ( getEastStatus().getCurrentAction() instanceof Stand ){//&& 
//			getWestStatus().getCurrentAction() instanceof Stand ){
			return true;
		}
		
		return false;
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
