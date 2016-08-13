package com.nate.sumo.model.fight;

import java.time.Instant;

import com.nate.sumo.display.KeyHandler;
import com.nate.sumo.model.basho.Match;
import com.nate.sumo.model.fight.FightAction.STATUS;
import com.nate.sumo.model.fight.actions.cut_scenes.CrouchIdle;
import com.nate.sumo.model.fight.actions.cut_scenes.Scene;
import com.nate.sumo.model.fight.actions.cut_scenes.Stand;
import com.nate.sumo.model.rikishi.Rikishi;

public class Fight implements FightKnowledgeIf, KeyHandler{

	public enum PHASE{
		PRE_FIGHT, TACHI_AI, FIGHT, FINISH, POST_FIGHT;
	}
	
	
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
		
		if ( readyForTachai() == true ){
			setPhase( PHASE.TACHI_AI );
			System.out.println( "Starting Tachi Ai..." );
		}
		
		getEastStatus().advance();
		getWestStatus().advance();
	}
	
	protected boolean readyForTachai(){
		if ( !getPhase().equals( PHASE.PRE_FIGHT ) ){
			return false;
		}
		
		if ( getEastStatus().getCurrentAction() == null || getWestStatus().getCurrentAction() == null ){
			return false;
		}

		if ( getEastStatus().getCurrentAction().getCurrentStatus().equals( STATUS.WAITING ) &&
			getWestStatus().getCurrentAction().getCurrentStatus().equals( STATUS.WAITING ) ){
			return true;
		}
		
		return false;
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
	
	private void setPhase( PHASE aPhase ){
		phase = aPhase;
	}

	public long getElapsedTime(){
		return Instant.now().toEpochMilli() - getStartTime();
	}
	
	private long getStartTime(){
		return startTime;
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

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		// TODO Auto-generated method stub
		
	}

}
