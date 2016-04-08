package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Map;

import com.nate.sumo.display.screens.FontTestScreen;
import com.nate.sumo.display.screens.MainScreen;
import com.nate.sumo.display.screens.ScreenInitData;

public class ScreenManager implements Drawable, KeyHandler
{

	private static ScreenManager screenManager;
	
	private Screen currentScreen;
	private Screen nextScreen;
	private int backgroundTexture;
	
	private ScreenManager(){}
	
	public static ScreenManager getInstance(){
		if ( screenManager == null ){
			screenManager = new ScreenManager();
			screenManager.currentScreen = new MainScreen();
			
			try{
				screenManager.setBackgroundTexture( "shrine.jpg" );
			}
			catch ( Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return screenManager;
	}
	
	public void draw(){
		
		glPushMatrix();
		
		glEnable( GL_TEXTURE_2D );
		glBindTexture(GL_TEXTURE_2D, backgroundTexture);
		
		glBegin( GL_QUADS );
			
			glColor4f( 1.0f, 1.0f, 1.0f, 0.8f );
			glTexCoord2f( 0.0f, 0.0f );
			glVertex3f( -1.3f, 1.3f, -0.1f );
			glTexCoord2f( 1.0f, 0.0f );
			glVertex3f( 1.3f, 1.3f, -0.1f );
			glTexCoord2f( 1.0f, 1.0f );
			glVertex3f( 1.3f, -1.3f, -0.1f );
			glTexCoord2f( 0.0f, 1.0f );
			glVertex3f( -1.3f, -1.3f, -0.1f );
		
		glEnd();
		
		glDisable( GL_TEXTURE_2D );
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
	
	private Screen getCurrentScreen(){
		return currentScreen;
	}
	
	private Screen getNextScreen(){
		return nextScreen;
	}
	
	private void setBackgroundTexture( String resource ) throws IOException, URISyntaxException{
		backgroundTexture = TextureManager.getInstance().loadTexture( resource );
	}
}
