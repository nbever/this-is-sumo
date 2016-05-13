package com.nate.sumo.display.screens;

import java.util.Map;

import com.nate.sumo.display.Dialog;
import com.nate.sumo.display.Dialog.Type;
import com.nate.sumo.display.widgets.Action;
import com.nate.sumo.display.widgets.ActionHandler;
import com.nate.sumo.model.basho.Match;
import com.nate.sumo.model.fight.Fight;

public class PracticePlayerSelect extends PlayerSelectScreen implements ActionHandler
{
	private ScreenInitData initData;
	private Dialog confirmDialog;

	public PracticePlayerSelect( Map<String, Object> initData )
	{
		super( initData );
	}
	
	public void allUnselected() {
		setNextScreenData( new ScreenInitData( MainScreen.class ) );
		close();
	};
	
	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		
		if ( !getConfirmDialog().isShowing() ){
			super.handleKey( key, scanCode, action, mods );
		}
		else {
			getConfirmDialog().handleKey( key, scanCode, action, mods );
		}
	}
	
	@Override
	public void draw() {
		super.draw();
		
		if ( getBanzukeSelector().isHigashiSelected() && getBanzukeSelector().isNishiSelected() ){
			
			if ( !getConfirmDialog().isShowing() ){
				getConfirmDialog().show();
			}
			else {
				getConfirmDialog().draw();
			}
		}
	}
	
	@Override
	public ScreenInitData getNextScreenData()
	{
		return initData;
	}
	
	private void setNextScreenData( ScreenInitData data ){
		initData = data;
	}
	
	private Dialog getConfirmDialog(){
		if ( confirmDialog == null ){
			confirmDialog = new Dialog( Type.CONFIRM, "Take these two rikishi into the practice ring?", this );
		}
		
		return confirmDialog;
	}

	@Override
	public void actionPerformed(Action action) {
		
		if ( action.getItem().equals( Dialog.CANCEL ) ){
			getBanzukeSelector().deselectLast();
		}
		else if ( action.getItem().equals( Dialog.OK ) ){
			initData = new ScreenInitData( MatchSetupScreen.class );
			
			Match match = new Match(getBanzukeSelector().getHigashiRikishi(), 
				getBanzukeSelector().getNishiRikishi() );
			
			Fight fight = new Fight( match );
			
			initData.getInitData().put( Fight.class.getSimpleName(), fight );
			close();
		}
	}

}
