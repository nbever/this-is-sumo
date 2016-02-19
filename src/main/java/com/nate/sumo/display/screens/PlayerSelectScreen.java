package com.nate.sumo.display.screens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.widgets.BanzukeSelector;
import com.nate.sumo.model.basho.Banzuke;
import com.nate.sumo.model.basho.Division;
import com.nate.sumo.model.basho.Rank.RankClass;

import static org.lwjgl.opengl.GL11.*;

public abstract class PlayerSelectScreen extends SwipeScreen
{
	
	public static final String PLAYER_LIST = "player_list";
	
	private BanzukeSelector banzukeSelector;
	
	public PlayerSelectScreen(Map<String, Object> initData) {
		super( initData );
	}

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
			banzukeSelector = new BanzukeSelector( (Banzuke)getInitData().get( PLAYER_LIST ));
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
