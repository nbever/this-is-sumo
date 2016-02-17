package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

public class ScreenHelper
{

	private static ScreenHelper instance;
	
	private ScreenHelper(){
		
	}
	
	public static ScreenHelper getInstance(){
		if ( instance == null ){
			instance = new ScreenHelper();
		}
		
		return instance;
	}
	
	public void drawTexturedSquare( float width, float height ){
		
		glBegin( GL_QUADS );
		
		glTexCoord2f( 0.0f, 1.0f );
		glVertex3f( 0.0f, 0.0f, 0.0f );
		glTexCoord2f( 1.0f, 1.0f );
		glVertex3f( width, 0.0f, 0.0f );
		glTexCoord2f( 1.0f, 0.0f );
		glVertex3f( width, height, 0.0f );
		glTexCoord2f( 0.0f, 0.0f );
		glVertex3f( 0.0f, height, 0.0f );
		
		glEnd();
	}
}
