package com.nate.sumo.display.widgets;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class Button extends Widget {

	private String text;
	private Float width;
	private ActionHandler handler;
	private boolean selected = false;
	
	public Button( String text, Float width, ActionHandler handler ){
		this.text = text;
		this.width = width;
		this.handler = handler;
	}
	
	public Button( String text, ActionHandler handler ){
		this.text = text;
		this.width = (float)text.length() * 0.045f;
		this.handler = handler;
	}
	
	@Override
	public void draw() {
		
		float selBumper = 0.01f;
		
		if ( isSelected() ){
			glPushMatrix();
				glDisable( GL_TEXTURE_2D );
				glTranslatef( -1.0f*selBumper, -1.0f * selBumper, 0.0f );
				glColor4f( 1.0f, 1.0f, 1.0f, 0.6f );
				ScreenHelper.getInstance().drawSquare( getWidth() + (2*selBumper), 0.1f + (2*selBumper), false );
			glPopMatrix();
		}
		
		glPushMatrix();
			glEnable( GL_BLEND );
			glEnable( GL_TEXTURE_2D );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.BUTTON_BKG ) );
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			ScreenHelper.getInstance().drawSquare( getWidth(), 0.1f, true );
		
		glPopMatrix();
		
		glPushMatrix();
			glTranslatef( 0.02f, 0.03f, 0.0f );
			Font.TIMES_NEW_ROMAN.drawString( getText() );
		glPopMatrix();
		
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {

		if ( !isSelected() ){
			return;
		}
		
		if ( GLFW_KEY_ENTER == key ){
			Action wAction = new Action();
			wAction.setActionKey( getText() );
			wAction.setSource( this );
			getActionHandler().actionPerformed( wAction );
		}
	}
	
	private String getText(){
		return this.text;
	}
	
	private Float getWidth(){
		return width;
	}
	
	private ActionHandler getActionHandler(){
		return handler;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected( boolean isSelected ){
		selected = isSelected;
	}

	@Override
	public List<String> getTextureNames() {
		if ( textureNames == null ){
			textureNames = Arrays.asList(
				TextureNames.BUTTON_BKG 
			);
		}
		
		return textureNames;
	}

}
