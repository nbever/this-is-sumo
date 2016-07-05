package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.RikishiStatus;

public class CrouchIdle extends NonInteractionAction {

	public CrouchIdle(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MD5Animation getAnimation() {
		if ( animation == null ){
			animation = AnimationManager.getInstance().loadAnimation( AnimationManager.DEFAULT_FOLDER + "/crouch.idle.md5anim" );
			animation.setRepeat( true );
		}
		return super.getAnimation();
	}
	
	@Override
	public long getTotalActionTime() {
	
		return -1L;
	}

}
