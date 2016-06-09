package com.nate.sumo.display.screens;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.DatabaseManager;
import com.nate.sumo.KeyMapper;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.menu.Menu;
import com.nate.sumo.display.widgets.menu.MenuItem;

public class MainScreen extends SwipeScreen
{
	private Menu menu;
	private MenuItem practiceMenu;
	
	public MainScreen(){
		
		menu = new Menu( Arrays.asList( new MenuItem( "Quick Match" ),
									    new MenuItem( "Play Tournament" ),
									    new MenuItem( "Start Career" ),
									    getPracticeMenu(), 
									    new MenuItem( "Load Game" )) );


	}
	
	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( key == KeyMapper.A_BUTTON && action == GLFW_PRESS ){
			close();
		}
		else {
			getMenu().handleKey(key, scanCode, action, mods);
		}
	}
	

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		getMenu().handleDirections( lateral, vertical, action );
	}

	@Override
	public void drawScreen()
	{
		glPushMatrix();
		
		glTranslatef( 0.0f, 0.0f, ScreenHelper.SCREEN_DEPTH );
		
		glEnable( GL_TEXTURE_2D );
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		
		glPushMatrix();
			glTranslatef( -1.0f, 0.5f, 0.0f );
			
			glColor4f( 0.7f, 0.1f, 0.1f, 1.0f );
			glScalef( 3.5f, 4.5f, 2.5f );
			Font.JAPANESE_CALI.drawJapaneseString( "これは大相撲" );
			
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			glTranslatef( 0.04f, 0.02f, 0.0f );
			glScalef( 0.9f, 0.6f, 0.9f );
			Font.JAPANESE_CALI.drawJapaneseString( "これは大相撲" );
		glPopMatrix();
		
		
		glPushMatrix();
			glTranslatef( -1.1f, -0.3f, 0.0f );
			glColor3f( 1.0f, 1.0f, 1.0f );
//			Font.ZENZAI.drawString( "Hello" );
			
			getMenu().draw();
		glPopMatrix();
		

		glPushMatrix();

			glBindTexture( GL_TEXTURE_2D, getTextures().get( TextureNames.HAKUHO_SALT ) );
		
			glTranslatef( 0.6f, -0.6f, 0.0f );
			glScalef( 0.6f, 0.6f, 0.6f );
		
			glBegin( GL_QUADS );
			
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			glTexCoord2f( 0.0f, 0.0f );
			glVertex3f( 1.0f, 1.0f, 0.0f );
			glTexCoord2f( 1.0f, 0.0f );
			glVertex3f( -1.0f, 1.0f, 0.0f );
			glTexCoord2f( 1.0f, 1.0f );
			glVertex3f( -1.0f, -1.0f, 0.0f );
			glTexCoord2f( 0.0f, 1.0f );
			glVertex3f( 1.0f, -1.0f, 0.0f );
			
			glEnd();
			
		glPopMatrix();
		
		glDisable( GL_TEXTURE_2D );
		glDisable( GL_BLEND );
		
		glPopMatrix();
	}
	
	@Override
	public void cleanup(){
		
	}
	
	private Menu getMenu(){
		return menu;
	}
	
	private MenuItem getPracticeMenu(){
		if ( practiceMenu == null ){
			practiceMenu = new MenuItem( "Practice" );
		}
		
		return practiceMenu;
	}

	@Override
	public List<String> getTextureNames(){
		
		if ( textureNames == null ){
			textureNames = Arrays.asList( TextureNames.HAKUHO_SALT );
		}
		
		return textureNames;
	}

	@Override
	public ScreenInitData getNextScreenData()
	{
		ScreenInitData data = null;
		
		if ( getMenu().getSelectedMenuItem().equals( getPracticeMenu() ) ){
			data = new ScreenInitData( PracticePlayerSelect.class );
			data.getInitData().put( PlayerSelectScreen.PLAYER_LIST, DatabaseManager.getInstance().getCurrentBanzuke() );
		}
		else {
			data = new ScreenInitData( FontTestScreen.class );
		}
//		ScreenInitData data = new ScreenInitData( DohyoScreen.class );
		return data;
	}
}
