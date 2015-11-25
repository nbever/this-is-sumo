package com.nate.sumo.display.screens;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import com.nate.sumo.display.Screen;
import com.nate.sumo.display.SwipeScreen;

public class DohyoScreen extends SwipeScreen
{

	private Float cameraAngle = 0.0f;
	
	public DohyoScreen(){
		super();
	}
	
	public DohyoScreen( Screen lastScreen ){
		super( lastScreen );
	}
	
	public void drawScreen()
	{
		//move camera
		glRotatef( getCameraAngle(), 0.0f, 1.0f, 0.0f );
		
		glBegin( GL_QUADS );
		
			glColor3f( 1.0f, 0.0f, 0.0f );
			
			glVertex3f( -1.0f, 1.0f, 1.0f );
			glVertex3f( 1.0f, 1.0f, 1.0f );
			glVertex3f( 1.0f, -1.0f, 1.0f );
			glVertex3f( -1.0f, -1.0f, 1.0f );
			
			glColor3f( 0.0f, 1.0f, 0.0f );
			
			glVertex3f( 1.0f, 1.0f, 1.0f );
			glVertex3f( 1.0f, 1.0f, -1.0f );
			glVertex3f( 1.0f, -1.0f, -1.0f );
			glVertex3f( 1.0f, -1.0f, 1.0f );
			
			glColor3f( 1.0f, 0.0f, 1.0f );
			
			glVertex3f( 1.0f, 1.0f, -1.0f );
			glVertex3f( 1.0f, -1.0f, -1.0f );
			glVertex3f( -1.0f, -1.0f, -1.0f );
			glVertex3f( -1.0f, 1.0f, -1.0f );
			
			glColor3f( 0.0f, 0.0f, 1.0f );
			
			glVertex3f( -1.0f, 1.0f, -1.0f );
			glVertex3f( -1.0f, 1.0f, 1.0f );
			glVertex3f( -1.0f, -1.0f, 1.0f );
			glVertex3f( -1.0f, -1.0f, -1.0f );
		
		glEnd();
	}

	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( isClosing() || isLoading() ){
			return;
		}
		
		if ( key == GLFW_KEY_LEFT && (action == GLFW_REPEAT || action == GLFW_PRESS) ){
			cameraAngle -= 5.0f;
			cameraAngle %= 360.0f;
		}
		else if ( key == GLFW_KEY_RIGHT && (action == GLFW_REPEAT || action == GLFW_PRESS) ){
			cameraAngle += 5.0f;
			cameraAngle %= 360.0f;
		}
	}
	
	private Float getCameraAngle(){
		return cameraAngle;
	}

}
