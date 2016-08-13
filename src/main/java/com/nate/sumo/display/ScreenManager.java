package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.util.Map;

import com.nate.sumo.display.screens.MainScreen;
import com.nate.sumo.display.screens.ScreenInitData;

public class ScreenManager implements Drawable, KeyHandler
{

//	protected static ScreenManager screenManager;
	
	private Screen currentScreen;
	private Screen nextScreen;
	private int backgroundTexture = -1;
	
//	public static ScreenManager getInstance(){
//		if ( screenManager == null ){
//			screenManager = new ScreenManager();
//		}
//		return screenManager;
//	}
	
	public ScreenManager(){}
	
	public void initialize(){
		
		currentScreen = new MainScreen();
		
		try{
			setBackgroundTexture( "shrine.jpg" );
		}
		catch ( Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw(){
		
		glPushMatrix();
		
		if ( backgroundTexture != -1 ){
			glEnable( GL_TEXTURE_2D );
			glBindTexture(GL_TEXTURE_2D, backgroundTexture);
			
			glTranslatef( 0.0f, 0.0f, ScreenHelper.SCREEN_DEPTH );
			
			glBegin( GL_QUADS );
				
				glColor4f( 1.0f, 1.0f, 1.0f, 0.8f );
				glTexCoord2f( 0.0f, 0.0f );
				glVertex3f( -1.3f, 1.3f, 0.0f );
				glTexCoord2f( 1.0f, 0.0f );
				glVertex3f( 1.3f, 1.3f, 0.0f );
				glTexCoord2f( 1.0f, 1.0f );
				glVertex3f( 1.3f, -1.3f, 0.0f );
				glTexCoord2f( 0.0f, 1.0f );
				glVertex3f( -1.3f, -1.3f, 0.0f );
			
			glEnd();
			
			glDisable( GL_TEXTURE_2D );
		}
		glPopMatrix();
		
		// closing so we should draw the next screen
		if ( getCurrentScreen().isClosing() && !getCurrentScreen().isDone() ){
			
			if ( getNextScreen() == null ){
				ScreenInitData initData = getCurrentScreen().getNextScreenData();
				try{
					Constructor<? extends Screen> constructor = null;
					
					if ( initData.getInitData() != null && !initData.getInitData().isEmpty() ){
						constructor = initData.getNextScreenClass().getConstructor( Map.class );
						nextScreen = constructor.newInstance( initData.getInitData() );
					}
					else {
						constructor = initData.getNextScreenClass().getConstructor();
						nextScreen = constructor.newInstance();
					}
					
					
				}
				catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			nextScreen.draw();
		}
		
		getCurrentScreen().draw();
		
		if ( getCurrentScreen().isDone() ){
			// draw one last time so it picks up the completion
			getCurrentScreen().draw();
			currentScreen = nextScreen;
			nextScreen = null;
		}
		
	}
	
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		// TODO Auto-generated method stub
		getCurrentScreen().handleKey(key, scanCode, action, mods);
	}
	
	public void handleDirections( float lateral, float vertical, int action ){
		
		if ( Math.abs( lateral ) < 0.2f ){
			lateral = 0.0f;
		}
		
		if ( Math.abs( vertical ) < 0.2f ){
			vertical = 0.0f;
		}
		
		getCurrentScreen().handleDirections( lateral, vertical, action );
	}
	
	private Screen getCurrentScreen(){
		return currentScreen;
	}
	
	protected void setCurrentScreen( Screen screen ){
		currentScreen = screen;
	}
	
	private Screen getNextScreen(){
		return nextScreen;
	}
	
	public void setBackgroundTexture( String resource ) throws IOException, URISyntaxException{
		
		if ( backgroundTexture != -1 ){
			TextureManager.getInstance().releaseTexture( backgroundTexture );
			backgroundTexture = -1;
		}
		
		if ( resource == null ){
			return;
		}
		
		backgroundTexture = TextureManager.getInstance().loadTexture( resource );
	}
}
