package com.nate.sumo.display.widgets;

import java.util.List;

import com.nate.sumo.display.fonts.Font;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class Spinner extends Widget {

	private int selectedIndex = -1; 
	private List<Object> choices;
	
	private boolean isPressed = false;
	private int lastKey = -1;
	
	public Spinner( List<Object> choices, Object defaultChoice ){
		this.choices = choices;
		
		selectedIndex = choices.indexOf( defaultChoice );
		
		if ( selectedIndex == -1 ){
			selectedIndex = 0;
		}
	}
	
	public Spinner( List<Object> choices ){
		this( choices, null );
	}
	
	@Override
	public void draw() {

		glPushMatrix();
			glEnable( GL_BLEND );
		
			if ( isSelected() ){
				setFull();
			}
			else {
				setAlpha();
			}
		
			Font.TIMES_NEW_ROMAN.drawString( getSelectedItem().toString() );
			
			glTranslatef( 0.0f, -0.05f, 0.0f );
			

			glPushMatrix();
				glDisable( GL_TEXTURE_2D );
				glScalef( 0.3f, 0.3f, 0.0f );
				
				if ( isSelected() ){
					
					setAlpha();
					
					if ( isPressed && lastKey == GLFW_KEY_LEFT ){
						setFull();
					}
					
					drawTriangle( false );
					glTranslatef( 0.12f, 0.0f, 0.0f );
					
					setAlpha();
					
					if ( isPressed && lastKey == GLFW_KEY_RIGHT ){
						setFull();
					}
					
					drawTriangle( true );
				}
			glPopMatrix();
		
			glDisable( GL_BLEND );
			
		glPopMatrix();
	}
	
	private void drawTriangle( boolean right ){
				
		glBegin( GL_TRIANGLES );
		
			if ( right ){
				glVertex3f( 0.0f, 0.0f, 0.0f );
				glVertex3f( 0.0f, 0.1f, 0.0f );
				glVertex3f( 0.1f, 0.05f, 0.0f );
			}
			else {
				glVertex3f( 0.0f, 0.05f, 0.0f );
				glVertex3f( 0.1f, 0.0f, 0.0f );
				glVertex3f( 0.1f, 0.1f, 0.0f );
			}
		
		glEnd();
	}
	
	private void setAlpha(){
		glColor4f( 1.0f, 1.0f, 1.0f, 0.6f );
	}
	
	private void setFull(){
		glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {

		if ( !isSelected() ){
			return;
		}
		System.out.println( action );
		if ( action == GLFW_RELEASE ){
			isPressed = false;
			return;
		}
		
		if ( action == GLFW_PRESS ){
			isPressed = true;
			lastKey = key;
			
			if ( lastKey == GLFW_KEY_RIGHT ){
				selectNextItem();
			}
			else if ( lastKey == GLFW_KEY_LEFT ){
				selectPreviousItem();
			}
		}
	}
	
	private void selectNextItem(){
		
		selectedIndex++;
		
		if ( selectedIndex >= getChoices().size() ){
			selectedIndex = 0;
		}
	}
	
	private void selectPreviousItem(){
		
		selectedIndex--;
		
		if ( selectedIndex < 0 ){
			selectedIndex = getChoices().size() - 1;
		}
	}

	@Override
	public List<String> getTextureNames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<Object> getChoices(){
		return choices;
	}
	
	private int getSelectedIndex(){
		return selectedIndex;
	}
	
	public Object getSelectedItem(){
		return getChoices().get( getSelectedIndex() );
	}

}
