package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.RikishiStatus;

public class Stand extends NonInteractionAction {

	private long duration = -1L;
	
	public Stand(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
	}

	public Stand(RikishiStatus myStatus, FightKnowledgeIf callback, long duration) {
		this( myStatus, callback );
		this.duration = duration;
	}
	
	@Override
	protected void advancePhase() {
	}
	
	@Override
	public MD5Animation getAnimation() {
		
		if ( animation == null ){
			animation = AnimationManager.getInstance().loadAnimation( AnimationManager.DEFAULT_FOLDER + "/sumo-idle1.md5anim" );
			animation.setRepeat( true );
		}
		
		return animation;
	}
	
	@Override
	public long getActionTime() {
		return this.duration;
	}
	
	@Override
	public long getTryTime() {
		return 0L;
	}
	
	@Override
	public long getRecoveryTime() {
		return 0L;
	}

}
