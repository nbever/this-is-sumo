package com.nate.sumo.display.screens;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nate.sumo.display.Screen;
import com.nate.sumo.model.fight.FightStatus;

public class MatchSetupScreen extends Screen {

	private FightStatus fight;
	
	public MatchSetupScreen( Map<String, Object> initData ){
		super( initData );
		
		fight = (FightStatus)initData.get( FightStatus.class.getSimpleName() );
	}
	
	@Override
	public List<String> getTextureNames() {

		return Arrays.asList( getFight().getEastStatus().getRikishi().getAppearenceMap().getPortrait(), 
			getFight().getWestStatus().getRikishi().getAppearenceMap().getPortrait() );
	}

	@Override
	public void drawScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawClosing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLoading() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public ScreenInitData getNextScreenData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		// TODO Auto-generated method stub

	}
	
	private FightStatus getFight(){
		return fight;
	}

}
