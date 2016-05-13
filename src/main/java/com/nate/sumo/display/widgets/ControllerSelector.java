package com.nate.sumo.display.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nate.model.Quaternarion;
import com.nate.model.Vector3f;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.model.animation.VectorAnimation;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class ControllerSelector extends Widget{

	private final float BURST_WIDTH = 0.4f;
	private final float BURST_HEIGHT = 0.4f;
	
	private final float C_HEIGHT = 0.2f;
	private final float C_WIDTH = 0.25f;
	
	private VectorAnimation animation;
	private List<Vector3f> positionMap;
	private int selection;
	private Quaternarion color;
	
	public ControllerSelector( List<Vector3f> positions, int initialPosition, Quaternarion color ){
		super();
		
		positionMap = positions;
		selection = initialPosition;
		this.color = color;
	}
	
	public ControllerSelector() {
		this( null, -1, new Quaternarion( 1.0f, 1.0f, 1.0f, 1.0f ) );
	}
	
	@Override
	public void draw() {
		
		glPushMatrix();
		
			glDisable( GL_TEXTURE_2D );
			glEnable( GL_BLEND );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			Vector3f pos;
		
			if ( getAnimation() == null || getAnimation().hasStopped() ){
				pos = getPositionMap().get( getSelection() );
			}
			else {
				
				getAnimation().advance();
				pos = getAnimation().getValue();
			}
			
			glTranslatef( pos.getX(), pos.getY(), pos.getZ() );
			
			float alpha = 0.4f;
			
			if ( isSelected() ){
				alpha = 1.0f;
			}
			
			glPushMatrix();
				glTranslatef( -0.17f, 0.12f, 0.0f );
				glRotatef( -45.0f, 0.0f, 0.0f, 1.0f );
				
				ScreenHelper.getInstance().drawCircleBurst( BURST_WIDTH, BURST_HEIGHT, getColor() );
			glPopMatrix();
			
			glPushMatrix();
				glEnable( GL_TEXTURE_2D );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_CONTROLLER ) );
				glColor4f( 1.0f, 1.0f, 1.0f, alpha );
				ScreenHelper.getInstance().drawSquare( C_WIDTH, C_HEIGHT, true );
				glDisable( GL_TEXTURE_2D );
				
			glPopMatrix();
		
		glPopMatrix();
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		
		if ( action != GLFW_PRESS ){
			return;
		}
		
		if ( key != GLFW_KEY_LEFT && key != GLFW_KEY_RIGHT ){
			return;
		}
		
		int index = getSelection();
		
		if ( key == GLFW_KEY_LEFT ){
			index--;
		}
		else {
			index++;
		}
		
		if ( index >= getPositionMap().size() ||
			index < 0 ){
			return;
		}
		
		animation = new VectorAnimation( getPositionMap().get( getSelection() ), 
			getPositionMap().get( index ), 10 );
		
		selection = index;
		
		animation.start();
	}

	@Override
	public List<String> getTextureNames() {

		return Arrays.asList( TextureNames.CTL_CONTROLLER );
	}
	
	private VectorAnimation getAnimation(){
		return animation;
	}
	
	private List<Vector3f> getPositionMap(){
		if ( positionMap == null ){
			positionMap = new ArrayList<Vector3f>();
		}
		
		return positionMap;
	}
	
	public int getSelection(){
		return selection;
	}
	
	public Quaternarion getColor(){
		return color;
	}

}
