package com.nate.sumo.display.screens;

import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;

import com.nate.sumo.display.SwipeScreen;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.fonts.Font;
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
		getMenu().handleKey(key, scanCode, action, mods);
	}

	@Override
	public void drawScreen()
	{
		if ( shingleId == -1 ){
			try
			{
				shingleId = TextureManager.getInstance().loadTexture( "shrine.jpg" );
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
		
		glPushMatrix();
			glTranslatef( -0.7f, 0.5f, -0.2f );
			
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			glScalef( 3.0f, 3.0f, 3.0f );
			Font.ZENZAI.drawString( "This is Sumo" );
			
			glColor4f( 0.07f, 0.3f, 0.07f, 1.0f );
			glTranslatef( 0.01f, 0.0f, 0.0f );
			glScalef( 0.95f, 0.95f, 0.95f );
			Font.ZENZAI.drawString( "This is Sumo" );
		glPopMatrix();
		
		
		glPushMatrix();
			glTranslatef( -1.1f, -0.3f, -0.2f );
			glColor3f( 1.0f, 1.0f, 1.0f );
//			Font.ZENZAI.drawString( "Hello" );
			
			getMenu().draw();
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
