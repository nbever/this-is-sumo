package com.nate.sumo.display.widgets.menu;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.jme3.util.BufferUtils;
import com.nate.sumo.display.Drawable;
import com.nate.sumo.display.KeyHandler;
import com.nate.sumo.display.fonts.Font;

public class Menu implements Drawable, KeyHandler
{
	private List<MenuItem> menuItems;
	private MenuItem selectedItem;
	
	public Menu( List<MenuItem> items ){
		menuItems = items;
		selectedItem = menuItems.get( 0 );
	}
	
	public void draw()
	{
		glPushMatrix();
		
		FloatBuffer color = BufferUtils.createFloatBuffer( 4 );
		glGetFloatv( GL_CURRENT_COLOR, color );
		
		try {
		
			for ( int i = 0; i < getMenuItems().size(); i++ ){
				
				if ( !getMenuItems().get( i ).equals( getSelectedMenuItem() ) ){
					
					glColor4f( color.get( 0 ), color.get( 1 ), color.get( 2 ), 0.7f );
				}
				else {
					glColor4f( color.get( 0 ), color.get( 1 ), color.get( 2 ), 1.0f );
				}
				
				Font.ZENZAI.drawString( getMenuItems().get( i ).getText() );
				glTranslatef( 0.0f, -0.16f, 0.0f );
			}
			
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		
		glPopMatrix();
	}
	
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		Integer index = null;
		
		if ( key == GLFW_KEY_DOWN && action == GLFW_PRESS ){
			index = getMenuItems().indexOf( getSelectedMenuItem() );
			index++;
		}
		else if ( key == GLFW_KEY_UP && action == GLFW_PRESS){
			index = getMenuItems().indexOf( getSelectedMenuItem() );
			index--;
		}
		
		if ( index == null ){
			return;
		}
		
		if ( index >= getMenuItems().size() ){
			index = 0;
		}
		else if ( index < 0 ){
			index = getMenuItems().size() - 1;
		}
		
		selectedItem = getMenuItems().get( index );
	}
	
	private List<MenuItem> getMenuItems(){
		if ( menuItems == null ){
			menuItems = new ArrayList<MenuItem>();
		}
		
		return menuItems;
	}
	
	private MenuItem getSelectedMenuItem(){
		return selectedItem;
	}

}
