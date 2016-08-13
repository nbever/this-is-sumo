package com.nate.sumo.display.widgets;

import com.nate.sumo.display.Screen;
import com.nate.sumo.display.ScreenManager;

public class TestScreenManager extends ScreenManager{

	private Screen testScreen;
	
	public TestScreenManager( Screen screen ){
		testScreen = screen;
	}
	
	@Override
	public void initialize() {
		setCurrentScreen( getTestScreen() );
	}
	
	private Screen getTestScreen(){
		return testScreen;
	}
}
