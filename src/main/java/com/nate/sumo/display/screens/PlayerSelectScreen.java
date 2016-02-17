package com.nate.sumo.display.screens;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.widgets.BanzukeSelector;

import static org.lwjgl.opengl.GL11.*;

public abstract class PlayerSelectScreen extends SwipeScreen
{
	private BanzukeSelector banzukeSelector;
	
	@Override
	public void drawScreen()
	{
		glPushMatrix();
		glTranslatef( -0.95f, 0.9f, -0.01f );
		getBanzukeSelector().draw();
		
		glPopMatrix();
	}
	
	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		getBanzukeSelector().handleKey(key, scanCode, action, mods);
	}
	
	private BanzukeSelector getBanzukeSelector(){
		
		if ( banzukeSelector == null ){
			banzukeSelector = new BanzukeSelector();
		}
		
		return banzukeSelector;
	}
	
	@Override
	public List<String> getTextureNames()
	{
		if ( textureNames == null ){
			textureNames = Arrays.asList();
		}
		
		return textureNames;
	}
	
	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
		
	}
	
}
