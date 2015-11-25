package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

public abstract class SwipeScreen extends Screen
{

	public SwipeScreen( Screen lastScreen )
	{
		super(lastScreen);
	}
	
	public SwipeScreen(){}

	private static final float OFF_SCREEN = 10.0f;
	private float leftCoord = OFF_SCREEN;
	
	@Override
	public void drawClosing()
	{
		glPushMatrix();
		
		glTranslatef( getLeftCoordinate(), 0.0f, 0.0f );
		leftCoord -= 0.2f;
		
		if ( getLeftCoordinate() <= -1.0f * OFF_SCREEN ){
			leftCoord = OFF_SCREEN;
			closeComplete();
		}
		
		drawScreen();
		
		glPopMatrix();
	}

	@Override
	public void drawLoading()
	{
		glPushMatrix();
		
		glTranslatef( getLeftCoordinate(), 0.0f, 0.0f );
		leftCoord -= 0.2f;
		
		if ( getLeftCoordinate() <= 0.0f ){
			leftCoord = -1.0f;
			loadComplete();
		}
		
		drawScreen();
		
		glPopMatrix();
	}
	
	private float getLeftCoordinate(){
		return leftCoord;
	}

}
