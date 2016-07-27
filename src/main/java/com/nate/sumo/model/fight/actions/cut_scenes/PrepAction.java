package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.FightAction;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.RikishiStatus;

public class PrepAction extends FightAction {

	public PrepAction( RikishiStatus myStatus, FightKnowledgeIf callback ) {
		super( myStatus, callback );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void advancePhase() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected MD5Animation buildAnimation() {
		
		MD5Animation animation = AnimationManager.getInstance().loadAnimation( AnimationManager.DEFAULT_FOLDER + "/pretachai.md5anim" );
		
		return animation;
	}
}
