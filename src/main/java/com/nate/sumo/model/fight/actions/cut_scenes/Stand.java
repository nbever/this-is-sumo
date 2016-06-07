package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.RikishiStatus;

public class Stand extends NonInteractionAction {

	public Stand(RikishiStatus myStatus, FightKnowledgeIf callback) {
		super( myStatus, callback );
	}

	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public MD5Animation getAnimation() {
		
		if ( animation == null ){
			animation = AnimationManager.getInstance().loadAnimation( "sumo-idle1.md5anim" );
		}
		
		return animation;
	}

}
