package com.nate.sumo.display.widgets.menu;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import com.nate.sumo.display.Drawable;
import com.nate.sumo.display.fonts.ZenzaiFont;

public class Menu implements Drawable
{
	private List<MenuItem> menuItems;
	
	public Menu( List<MenuItem> items ){
		menuItems = items;
	}
	
	public void draw()
	{
		glPushMatrix();
		
		try {
		
			for ( int i = 0; i < getMenuItems().size(); i++ ){
				
				glTranslatef( 0.0f, -0.3f, 0.0f );
			}
			
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		
		glPopMatrix();
	}
	
	private List<MenuItem> getMenuItems(){
		if ( menuItems == null ){
			menuItems = new ArrayList<MenuItem>();
		}
		
		return menuItems;
	}

}
