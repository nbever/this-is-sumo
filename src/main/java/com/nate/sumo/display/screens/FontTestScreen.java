package com.nate.sumo.display.screens;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;

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
		
		fontMenu = new Menu( Arrays.asList( new MenuItem( "Zenzei", Font.ZENZAI )) );
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

}
