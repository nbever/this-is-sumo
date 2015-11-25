package com.nate.sumo.display.screens;

import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;

import com.nate.sumo.display.SwipeScreen;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.fonts.ZenzaiFont;
import com.nate.sumo.display.widgets.menu.Menu;
import com.nate.sumo.display.widgets.menu.MenuItem;

public class MainScreen extends SwipeScreen
{
	private Menu menu;
	
	private Integer shingleId = -1;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void drawScreen()
	{
		if ( shingleId == -1 ){
			try
			{
				shingleId = TextureManager.getInstance().loadTexture( "leadshingle.jpg" );
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		glEnable( GL_TEXTURE_2D );
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		
		glBindTexture(GL_TEXTURE_2D, shingleId);
		
		glBegin( GL_QUADS );
			
			glTexCoord2f( 0.0f, 0.0f );
			glVertex3f( -0.5f, 0.5f, -1.5f );
			glTexCoord2f( 1.0f, 0.0f );
			glVertex3f( 0.5f, 0.5f, -1.0f );
			glTexCoord2f( 1.0f, 1.0f );
			glVertex3f( 0.5f, -0.5f, -1.0f );
			glTexCoord2f( 0.0f, 1.0f );
			glVertex3f( -0.5f, -0.5f, -1.5f );
		
		glEnd();
		
		glPushMatrix();
			glTranslatef( 0.0f, 0.0f, -5.0f );
			glScalef( 0.015f, 0.015f, 0.015f );
			glColor3f( 1.0f, 1.0f, 1.0f );
			ZenzaiFont.getInstance().drawString( "Hello", 2 );
		glPopMatrix();
		
		glDisable( GL_TEXTURE_2D );
		glDisable( GL_BLEND );
	}
	
	private Menu getMenu(){
		return menu;
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		TextureManager.getInstance().releaseTexture( shingleId );
		shingleId = -1;
	}
}
