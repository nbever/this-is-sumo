package com.nate.sumo.display.screens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.BanzukeSelector;
import com.nate.sumo.model.basho.Banzuke;
import com.nate.sumo.model.basho.Division;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.rikishi.Rikishi;

import static org.lwjgl.opengl.GL11.*;

public abstract class PlayerSelectScreen extends SwipeScreen
{
	public static final String PLAYER_LIST = "player_list";

	private final float IMAGE_HEIGHT = 0.6f;
	private final float IMAGE_WIDTH = 0.4f;
	
	private BanzukeSelector banzukeSelector;
	
	public PlayerSelectScreen(Map<String, Object> initData) {
		super( initData );
	}

	@Override
	public void drawScreen()
	{
		glPushMatrix();
			glTranslatef( -0.95f, 0.9f, ScreenHelper.SCREEN_DEPTH );
			glScalef( 0.8f, 0.8f, 0.0f );
			getBanzukeSelector().draw();
		glPopMatrix();
		
		glPushMatrix();
			glTranslatef( 0.1f, 0.9f, ScreenHelper.SCREEN_DEPTH );
			drawRikishiSelection( getBanzukeSelector().getHigashiRikishi() );
		glPopMatrix();
	}
	
	private void drawRikishiSelection( Rikishi rikishi ){
		
		glEnable( GL_TEXTURE_2D );
		glEnable( GL_BLEND );
		
		glPushMatrix();
			glColor3f( 1.0f, 1.0f, 1.0f );
			glTranslatef( 0.0f, -0.15f, 0.0f );
			Font.JAPANESE_CALI.drawJapaneseString( "東" );
			
			glTranslatef( 0.0f, -1.0f*IMAGE_HEIGHT, 0.0f );
			ScreenHelper.getInstance().drawSquare( IMAGE_WIDTH, IMAGE_HEIGHT, true );
			
			glTranslatef( 0.0f, -0.1f, 0.0f );
			glDisable( GL_TEXTURE_2D );
			glColor3f( 1.0f, 1.0f, 1.0f );
			Font.JAPANESE_CALI.drawJapaneseString( rikishi.getRikishiInfo().getShikona().getFirstName_kanji() + " " +  
				rikishi.getRikishiInfo().getShikona().getLastName_kanji() );
			
		glPopMatrix();
		
		glPushMatrix();
		
			glEnable( GL_TEXTURE_2D );
			glTranslatef( IMAGE_WIDTH + 0.05f, -0.2f, 0.0f );
			glScalef( 0.7f, 0.7f, 0.0f );
			
			Font.TIMES_NEW_ROMAN.drawString( "Overall:" );
			
		glPopMatrix();
		
		glPushMatrix();
			glEnable( GL_TEXTURE_2D );
			glTranslatef( IMAGE_WIDTH + 0.2f, -0.2f, 0.0f );
			glScalef( 0.7f, 0.7f, 0.0f );
		
			Font.TIMES_NEW_ROMAN.drawString( "" + rikishi.getRikishiStats().getOverallSkill().intValue() );
		
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
