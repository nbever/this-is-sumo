package com.nate.sumo.display.screens;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.menu.Menu;

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
		glTranslatef( -0.9f, 0.0f, -0.2f );
		glScalef( 2.0f, 2.0f, 2.0f );
		
//		Font.JAPANESE_CALI.drawString( new char[]{65, 133, 120, 83, 100} );
		Font.JAPANESE_CALI.drawJapaneseString( "鉛筆".toCharArray() );
		glPopMatrix();
		
		glDisable( GL_BLEND );
	}

	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
	}
	
	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		if ( vertical > 0 ){
			character++;
		}
		else if ( vertical < 0 ){
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
