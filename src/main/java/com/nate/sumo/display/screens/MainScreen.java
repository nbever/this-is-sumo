package com.nate.sumo.display.screens;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.menu.Menu;
import com.nate.sumo.display.widgets.menu.MenuItem;

public class MainScreen extends SwipeScreen
{
	private Menu menu;
	
	public MainScreen(){
		
		menu = new Menu( Arrays.asList( new MenuItem( "Quick Match" ),
									    new MenuItem( "Play Tournament" ),
									    new MenuItem( "Start Career" ),
									    new MenuItem( "Practice"), 
									    new MenuItem( "Load Game" )) );


	}
	
	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( key == GLFW_KEY_ENTER && action == GLFW_PRESS && mods == GLFW_MOD_SHIFT ){
			close();
		}
		else {
			getMenu().handleKey(key, scanCode, action, mods);
		}
	}

	@Override
	public void drawScreen()
	{
		
		glEnable( GL_TEXTURE_2D );
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		
		glPushMatrix();
			glTranslatef( -0.7f, 0.5f, -0.2f );
			
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			glScalef( 3.0f, 3.0f, 3.0f );
			Font.ZENZAI.drawString( "This is Sumo" );
			
			glColor4f( 0.07f, 0.3f, 0.07f, 1.0f );
			glTranslatef( 0.007f, 0.0f, 0.0f );
//			glScalef( 0.95f, 0.95f, 0.95f );
			Font.ZENZAI.drawString( "This is Sumo" );
		glPopMatrix();
		
		
		glPushMatrix();
			glTranslatef( -1.1f, -0.3f, -0.2f );
			glColor3f( 1.0f, 1.0f, 1.0f );
//			Font.ZENZAI.drawString( "Hello" );
			
			getMenu().draw();
		glPopMatrix();
		

		glPushMatrix();

			glBindTexture( GL_TEXTURE_2D, getTextures().get( TextureNames.HAKUHO_SALT ) );
		
			glTranslatef( 0.6f, -0.6f, -0.2f );
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
	}
	
	@Override
	public void cleanup(){
		
	}
	
	private Menu getMenu(){
		return menu;
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
		ScreenInitData data = new ScreenInitData( FontTestScreen.class );
		return data;
	}

}
