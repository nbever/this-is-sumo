package com.nate.sumo.display.screens;

import java.util.HashMap;
import java.util.Map;

import com.nate.sumo.display.Screen;

public class ScreenInitData
{

	private Class<? extends Screen> nextScreenClass;
	private Map<String, Object> initData;
	
	public ScreenInitData( Class<? extends Screen> aClass ){
		nextScreenClass = aClass;
	}
	
	public Class<? extends Screen> getNextScreenClass(){
		return nextScreenClass;
	}
	
	public Map<String, Object> getInitData(){
		if ( initData == null ){
			initData = new HashMap<String, Object>();
		}
		
		return initData;
	}
}
