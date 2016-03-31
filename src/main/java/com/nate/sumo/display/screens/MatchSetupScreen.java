package com.nate.sumo.display.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.ir.GetSplitState;

import com.jme3.math.Vector4f;
import com.nate.model.Vector3f;
import com.nate.sumo.DatabaseManager;
import com.nate.sumo.display.Screen;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.ButtonInstructions;
import com.nate.sumo.display.widgets.ControllerSelector;
import com.nate.sumo.display.widgets.Spinner;
import com.nate.sumo.display.widgets.Widget;
import com.nate.sumo.model.animation.Animation;
import com.nate.sumo.model.common.Place;
import com.nate.sumo.model.fight.FightStatus;
import com.nate.sumo.model.rikishi.Heya;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class MatchSetupScreen extends SwipeScreen {

	private FightStatus fight;
	private ButtonInstructions buttonInstructions;
	private ControllerSelector player1;
	private ControllerSelector player2;
	private Spinner locationSpinner;
	
	private final String HIGASHI = "higashi";
	private final String NISHI = "nishi";
	private final String NONE = "none";
	
	private final float c_left = -0.5f;
	private final float c_middle = 0.0f;
	private final float c_right = 0.5f;
	
	private ScreenInitData nextScreen = null;
	
	public MatchSetupScreen( Map<String, Object> initData ){
		super( initData );
		
		fight = (FightStatus)initData.get( FightStatus.class.getSimpleName() );
	}
	
	@Override
	public List<String> getTextureNames() {

		FightStatus fight = (FightStatus)getInitData().get( FightStatus.class.getSimpleName() );
		
		return Arrays.asList( fight.getEastStatus().getRikishi().getAppearenceMap().getPortrait(), 
			fight.getWestStatus().getRikishi().getAppearenceMap().getPortrait() );
	}

	@Override
	public void drawScreen() {
		
		glPushMatrix();
		
			float backPush = -0.3f;
			float picWidth = 0.7f;
			float picHeight = 1.7f;
		
			glEnable( GL_BLEND );
			glEnable( GL_TEXTURE_2D );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		
			glTranslatef( 0.0f, 0.0f, ScreenHelper.SCREEN_DEPTH );
			
			glPushMatrix();
				glTranslatef( -0.8f, -0.7f, 0.0f );
				int texId = TextureManager.getInstance().getTextureId( getFight().getEastStatus().getRikishi().getAppearenceMap().getPortrait() );
				glBindTexture( GL_TEXTURE_2D, texId );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				
				glBegin( GL_QUADS );
					glTexCoord2f( 0.0f, 0.0f );
					glVertex3f( 0.0f, picHeight, 0.0f );
					glTexCoord2f( 1.0f, 0.0f );
					glVertex3f( picWidth, picHeight, backPush );
					glTexCoord2f( 1.0f, 1.0f );
					glVertex3f( picWidth, 0.0f, backPush );
					glTexCoord2f( 0.0f, 1.0f );
					glVertex3f( 0.0f, 0.0f, 0.0f );
					
				glEnd();
				
			glPopMatrix();
			
			glPushMatrix();
				glTranslatef( 0.1f, -0.7f, 0.0f );
				texId = TextureManager.getInstance().getTextureId( getFight().getWestStatus().getRikishi().getAppearenceMap().getPortrait() );
				glBindTexture( GL_TEXTURE_2D, texId );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				
				glBegin( GL_QUADS );
					glTexCoord2f( 0.0f, 0.0f );
					glVertex3f( 0.0f, picHeight, backPush );
					glTexCoord2f( 1.0f, 0.0f );
					glVertex3f( picWidth, picHeight, 0.0f );
					glTexCoord2f( 1.0f, 1.0f );
					glVertex3f( picWidth, 0.0f, 0.0f );
					glTexCoord2f( 0.0f, 1.0f );
					glVertex3f( 0.0f, 0.0f, backPush );
					
				glEnd();
				
			glPopMatrix();
			
			glPushMatrix();
				glTranslatef( -0.9f, 0.7f, 0.0f );
				glScalef( 2.4f, 2.4f, 0.0f );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				drawVerticalText( getFight().getEastStatus().getRikishi().getRikishiInfo().getShikona().getFirstName_kanji() );
				glTranslatef( 0.0f, -0.15f, 0.0f );
				drawVerticalText( getFight().getEastStatus().getRikishi().getRikishiInfo().getShikona().getLastName_kanji() );
			glPopMatrix();
			
			glPushMatrix();
				glTranslatef( 0.65f, 0.7f, 0.0f );
				glScalef( 2.4f, 2.4f, 0.0f );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				drawVerticalText( getFight().getWestStatus().getRikishi().getRikishiInfo().getShikona().getFirstName_kanji() );
				glTranslatef( 0.0f, -0.15f, 0.0f );
				drawVerticalText( getFight().getWestStatus().getRikishi().getRikishiInfo().getShikona().getLastName_kanji() );
			glPopMatrix();
			
			glPushMatrix();
				glTranslatef( -0.01f, 0.0f, 0.0f );
				glScalef( 2.4f, 2.4f, 0.0f );
				glColor3f( 1.0f, 0.0f, 0.0f );
				Font.JAPANESE_CALI.drawJapaneseString( "対" );
				glDisable( GL_TEXTURE_2D );
			glPopMatrix();
			
			glPushMatrix();
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glTranslatef( -0.12f, -0.4f, 0.0f );
				getPlayer1().draw();
				
				glTranslatef( 0.0f, -0.22f, 0.0f );
				getPlayer2().draw();
			glPopMatrix();
			
			glPushMatrix();
				glTranslatef( -0.3f, -0.8f, 0.0f );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				Font.TIMES_NEW_ROMAN.drawString( "Location:" );
				glTranslatef( 0.23f, 0.0f, 0.0f );
				getLocationSpinner().draw();
			glPopMatrix();
			
			glPushMatrix();
				glTranslatef( -0.9f, -0.95f, 0.0f );
				getButtonInstructions().draw();
			glPopMatrix();
				
		glPopMatrix();
	}
	
	private void drawVerticalText( String text ){
		
		for ( int i = 0; i < text.length(); i++ ){
			String s = text.substring( i, i+1 );
			Font.JAPANESE_CALI.drawJapaneseString( s );
			glTranslatef( 0.0f, -0.1f, 0.0f );
		}
	}

	@Override
	public void cleanup() {
		getButtonInstructions().cleanUp();
		getPlayer1().cleanUp();
		getPlayer2().cleanUp();
	}

	@Override
	public ScreenInitData getNextScreenData() {
		// TODO Auto-generated method stub
		return nextScreen;
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		
		if ( action == GLFW_PRESS ){
			if ( key == GLFW_KEY_DOWN ){
				selectNextWidget( true, true );
				return;
			}
			else if ( key == GLFW_KEY_UP ){
				selectNextWidget( true, false );
				return;
			}
			else if ( key == GLFW_KEY_BACKSPACE ){
				ScreenInitData back = new ScreenInitData( PracticePlayerSelect.class );
				back.getInitData().put( PlayerSelectScreen.PLAYER_LIST, DatabaseManager.getInstance().getCurrentBanzuke() );
				setNextScreenData( back );
				close();
			}
		}
		
		getPlayer1SelectedWidget().handleKey( key, scanCode, action, mods );
	}
	
	private void setNextScreenData( ScreenInitData nextScreen ){
		this.nextScreen = nextScreen;
	}
	
	private ButtonInstructions getButtonInstructions(){
		if ( buttonInstructions == null ){
			buttonInstructions = new ButtonInstructions();
		}
		return buttonInstructions;
	}
	
	private ControllerSelector getPlayer1(){
		if ( player1 == null ){
			
			List<Vector3f> posMap = Arrays.asList( 
				new Vector3f( c_left, 0.0f, 0.0f ),
				new Vector3f( c_middle, 0.0f, 0.0f ),
				new Vector3f( c_right, 0.0f, 0.0f ) );
			
			player1 = new ControllerSelector( posMap, 1, new Vector4f( 0.0f, 0.0f, 1.0f, 1.0f ) );
			
			registerWidget( player1 );
			selectWidget( player1, true );
		}
		return player1;
	}
	
	private ControllerSelector getPlayer2(){
		if ( player2 == null ){
			
			List<Vector3f> posMap = Arrays.asList( 
				new Vector3f( c_left, 0.0f, 0.0f ),
				new Vector3f( c_middle, 0.0f, 0.0f ),
				new Vector3f( c_right, 0.0f, 0.0f ) );
			
			player2 = new ControllerSelector( posMap, 1, new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f ) );
			
			registerWidget( player2 );
		}
		
		return player2;
	}
	
	private Spinner getLocationSpinner(){
		if ( locationSpinner == null ){
			
			List<Object> places = new ArrayList<Object>();
			places.add( Place.KOKUGIKAN );
			places.add( Place.FUKUOKA_CENTER );
			places.add( Place.AICHI_PREFECTURAL_GYMNASIUM );
			
			Heya hHeya = getFight().getEastStatus().getRikishi().getRikishiInfo().getHeya();
			Heya nHeya = getFight().getWestStatus().getRikishi().getRikishiInfo().getHeya();
			
			if ( hHeya != null ){
				places.add( hHeya );
			}
			
			if ( nHeya != null && !places.contains( nHeya )){
				places.add( nHeya );
			}
			
			locationSpinner = new Spinner( places );
			registerWidget( locationSpinner );
		}
		
		return locationSpinner;
	}
	
	private FightStatus getFight(){
		return fight;
	}

}
