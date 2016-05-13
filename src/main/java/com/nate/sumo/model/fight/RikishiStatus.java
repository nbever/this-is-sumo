package com.nate.sumo.model.fight;

import java.util.List;

import com.nate.model.MD5Animation;
import com.nate.model.MD5Model;
import com.nate.sumo.model.animation.ModelAnimationInfo;
import com.nate.sumo.model.fight.Fight.PHASE;
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
	
	private ModelAnimationInfo modelInfo;
	
	public RikishiStatus( Rikishi rikishi ){
		this.rikishi = rikishi;
	}
	
	public void load(){
		modelInfo = new ModelAnimationInfo( getRikishi().getAppearenceMap() );
	}
	
	public void advance(){
		// make decision
		long elapsedTime = 0L;
		
		if ( getCurrentAction() != null ){
			getCurrentAction().advance();
			
			elapsedTime = getCurrentAction().getElapsedTime();
		}
		
		getModelAnimationInfo().update( elapsedTime );
	}
	
	public Rikishi getRikishi(){
		return rikishi;
	}
	
	public ModelAnimationInfo getModelAnimationInfo(){
		return modelInfo;
	}
	
	public FightAction getCurrentAction(){
		return currentAction;
	}
	
	public Float getEnergy(){
		return energy;
	}
	
	public Float getMaxEnergy(){
		return maxEnergy;
	}
	
	public Float getMedialBalance(){
		return medialBalance;
	}
	
	public Float getLateralBalance(){
		return lateralBalance;
	}
}
