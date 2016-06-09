package com.nate.sumo;

import static org.lwjgl.glfw.GLFW.*;

import com.nate.sumo.display.ScreenManager;

public class KeyMapper {

	public static final int A_BUTTON = 0;
	public static final int B_BUTTON = 1;
	public static final int X_BUTTON = 2;
	public static final int Y_BUTTON = 3;
	public static final int R1_BUTTON = 4;
	public static final int R2_BUTTON = 5;
	public static final int L1_BUTTON = 6;
	public static final int L2_BUTTON = 7;
	public static final int START_BUTTON = 8;
	public static final int SELECT_BUTTON = 9;
	
	public static final int LEFT_DPAD = 10;
	public static final int RIGHT_DPAD = 11;
	public static final int UP_DPAD = 12;
	public static final int DOWN_DPAD = 13;

	public static final int LEFT_LSTICK = 14;
	public static final int RIGHT_LSTICK = 15;
	public static final int UP_LSTICK = 16;
	public static final int DOWN_LSTICK = 17;
	
	public static final int LEFT_RSTICK = 18;
	public static final int RIGHT_RSTICK = 19;
	public static final int UP_RSTICK = 20;
	public static final int DOWN_RSTICK = 21;
	
	public static void mapKey( int key, int scancode, int action, int mods ){
		
		
		if ( key != GLFW_KEY_RIGHT &&  key != GLFW_KEY_LEFT && key != GLFW_KEY_UP && key != GLFW_KEY_DOWN ){
			mapButton( key, scancode, action, mods );
		}
		else {
			mapDirections( key, scancode, action, mods );
		}
	}
	
	private static void mapButton( int key, int scanCode, int action, int mods ){
		
		int mappedKey = 0;
		
		switch( key ){
		case GLFW_KEY_ENTER:
			mappedKey = START_BUTTON;
			break;
		case GLFW_KEY_A:
			mappedKey = B_BUTTON;
			break;
		case GLFW_KEY_Z:
			mappedKey = A_BUTTON;
			break;
		case GLFW_KEY_S:
			mappedKey = Y_BUTTON;
			break;
		case GLFW_KEY_D:
			mappedKey = X_BUTTON;
			break;
		case GLFW_KEY_W:
			mappedKey = R1_BUTTON;
			break;
		case GLFW_KEY_E:
			mappedKey = L1_BUTTON;
			break;
		}
		
		ScreenManager.getInstance().handleKey( mappedKey, scanCode, action, mods );
	}
	
	private static void mapDirections( int key, int scanCode, int action, int mods ){
	
		float lateral = 0.0f;
		float vertical = 0.0f;
		
		switch( key ){
		case GLFW_KEY_RIGHT:
			lateral = 1.0f;
			break;
		case GLFW_KEY_LEFT:
			lateral = -1.0f; 
			break;
		case GLFW_KEY_UP:
			vertical = 1.0f; 
			break;
		case GLFW_KEY_DOWN:
			vertical = -1.0f; 
			break;
			
		}
		
		ScreenManager.getInstance().handleDirections( lateral, vertical, action );
	}
	
}
