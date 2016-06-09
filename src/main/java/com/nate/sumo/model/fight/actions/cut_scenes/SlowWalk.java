package com.nate.sumo.model.fight.actions.cut_scenes;

import com.nate.model.MD5Animation;
import com.nate.sumo.display.AnimationManager;
import com.nate.sumo.model.fight.FightKnowledgeIf;
import com.nate.sumo.model.fight.NonInteractionAction;
import com.nate.sumo.model.fight.RikishiStatus;
import com.nate.sumo.model.fight.Route;

public class SlowWalk extends NonInteractionAction{

	private Route route;
	
	public SlowWalk(RikishiStatus myStatus, FightKnowledgeIf callback, Route route ) {
		super( myStatus, callback );
		
		this.route = route;
	}
	
	@Override
	public MD5Animation getAnimation() {
	
		if ( animation == null ){
			animation = AnimationManager.getInstance().loadAnimation( "slowwalk.md5anim" );
		}
		
		return animation;
	}
	
	private Route getRoute(){
		return route;
	}

}
