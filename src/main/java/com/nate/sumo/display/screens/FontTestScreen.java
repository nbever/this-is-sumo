package com.nate.sumo.display.screens;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.menu.Menu;
import com.nate.sumo.display.widgets.menu.MenuItem;

public class FontTestScreen extends SwipeScreen
{
	
	private Menu fontMenu;
	private String inputString = "";
	private int character = 0;
	
	@Override
	public void drawScreen()
	{
		glPushMatrix();
		
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		
		glColor3f( 1.0f, 1.0f, 1.0f );
		glTranslatef( 0.0f, 0.0f, -0.2f );
		
		Font.JAPANESE_CALI.drawString( new char[]{65, 2200, 12354, 0x3042, 0x41} );
		
		glPopMatrix();
		
		glDisable( GL_BLEND );
	}

	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( key == GLFW_KEY_UP ){
			character++;
		}
		else if ( key == GLFW_KEY_DOWN ){
			character--;
		}
	}

	@Override
	public List<String> getTextureNames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScreenInitData getNextScreenData()
	{
		// TODO Auto-generated method stub
		return new ScreenInitData( MainScreen.class );
	}

}
