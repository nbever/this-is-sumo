package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.net.URISyntaxException;

import com.nate.sumo.display.screens.MainScreen;

public class ScreenManager implements Drawable, KeyHandler
{

	private static ScreenManager screenManager;
	
	private Screen currentScreen;
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
		
		getCurrentScreen().draw();
	}
	
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		// TODO Auto-generated method stub
		getCurrentScreen().handleKey(key, scanCode, action, mods);
	}
	
	private Screen getCurrentScreen(){
		return currentScreen;
	}
	
	private void setBackgroundTexture( String resource ) throws IOException, URISyntaxException{
		backgroundTexture = TextureManager.getInstance().loadTexture( resource );
	}
}
