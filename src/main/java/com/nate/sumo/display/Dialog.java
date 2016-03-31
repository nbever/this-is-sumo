package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.Action;
import com.nate.sumo.display.widgets.ActionHandler;
import com.nate.sumo.display.widgets.Button;
import com.nate.sumo.display.widgets.Widget;

public class Dialog extends Widget implements ActionHandler{

	public enum Type {
		INFO,CONFIRM,LOADING
	}
	
	public static final int OK = 1;
	public static final int CANCEL = 2;
	public static final String CLOSED = "CLOSED";
	
	private List<String> lines;
	private Type mode;
	private boolean show = false;
	
	private Button okButton;
	private Button cancelButton;
	
	private ActionHandler upstreamHandler;
	
	private int result = -1;
	
	public Dialog( Type mode, String text, ActionHandler handler ){
		
		upstreamHandler = handler;
		this.mode = mode;
		init( text );
	}
	
	public Dialog( Type mode, String text ){
		this( mode, text, null );
	}
	
	private void init( String text ){
		
		if ( mode == Type.CONFIRM ){
			getOkButton().setSelected( true );
			getCancelButton().setSelected( false );
		}
		
		initText( text );
	}
	
	private void initText( String text ){
		
		String buffer = "";
		
		lines = new ArrayList<String>();
		
		for ( int i = 0; i < text.length(); i++ ){
			
			char c = text.charAt( i );
			
			if ( buffer.length() >= 32 ){
				buffer += "- ";
			}
			
			if ( buffer.length() >= 24 && c == ' ' ){
				lines.add( buffer );
				buffer = "";
				continue;
			}
			
			buffer += c;
		}
		
		lines.add( buffer );
	}
	
	public void show(){
		show = true;
	}
	
	public void close(){
		
		if ( getUpstreamHandler() != null ){
			
			Action action = new Action();
			action.setActionKey( CLOSED );
			action.setSource( this );
			
			if ( getCancelButton().isSelected() ){
				action.setItem( CANCEL );
			}
			else if ( getOkButton().isSelected() ){
				action.setItem( OK );
			}
			
			getUpstreamHandler().actionPerformed( action );
		}
		
		unloadTextures();
		show = false;
	}


	@Override
	public void actionPerformed(Action action) {
		
		if ( action.getSource().equals( getOkButton() ) ){
			result = OK;
		}
		else if ( action.getSource().equals( getCancelButton() ) ){
			result = CANCEL;
		}
		
		close();
	}
	
	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		
		if ( action != GLFW_PRESS ){
			return;
		}
		
		if ( GLFW_KEY_RIGHT == key ){
			getCancelButton().setSelected( true );
			getOkButton().setSelected( false );
		}
		else if ( key == GLFW_KEY_LEFT ){
			getCancelButton().setSelected( false );
			getOkButton().setSelected( true );			
		}
		else {
			if ( getOkButton().isSelected() ){
				getOkButton().handleKey( key, scanCode, action, mods );
			}
			else {
				getCancelButton().handleKey( key, scanCode, action, mods );
			}
		}
	}

	@Override
	public void draw() {
		
		if ( !isShowing() ){
			return;
		}
		
		glPushMatrix();
		
			glEnable( GL_BLEND );
			glDisable( GL_TEXTURE_2D );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			glPushMatrix();
				glTranslatef( -1.1f, -1.1f, ScreenHelper.DIALOG_DEPTH );
				glColor4f( 0.0f, 0.0f, 0.0f, 0.7f );
				ScreenHelper.getInstance().drawSquare( 2.2f, 2.2f, false );
			glPopMatrix();
		
			glEnable( GL_TEXTURE_2D );
			glEnable( GL_BLEND );
			glTranslatef( -0.65f, -0.1f, ScreenHelper.DIALOG_DEPTH );
			glColor4f( 1.0f, 1.0f, 1.0f, 0.9f );
			glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.DIALOG_BKG ) );
			ScreenHelper.getInstance().drawSquare( 1.3f, 0.6f, true );
			
			// do the words
			glPushMatrix();
				float lineHeight = 0.05f;
				float up = ((float)getLines().size() * lineHeight) + 0.25f;
				glTranslatef( 0.4f, up, 0.0f );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				
				for ( String line : getLines() ){
					Font.TIMES_NEW_ROMAN.drawString( line );
					glTranslatef( 0.0f, -1.0f * lineHeight, 0.0f );
				}
			glPopMatrix();
			
			glPushMatrix();
				// draw the buttons
				if ( getMode().equals( Type.CONFIRM ) ){
					glTranslatef( 0.85f, 0.048f, 0.0f );
					getOkButton().draw();
					glTranslatef( 0.17f, 0.0f, 0.0f );
					getCancelButton().draw();
				}
			glPopMatrix();
			
		glPopMatrix();
	}
	
	@Override
	public List<String> getTextureNames() {

		return Arrays.asList( TextureNames.DIALOG_BKG, TextureNames.BUTTON_BKG );
	}
	
	private void unloadTextures(){
		TextureManager.getInstance().releaseTexture( TextureNames.DIALOG_BKG );
		TextureManager.getInstance().releaseTexture( TextureNames.BUTTON_BKG );		
	}
	
	private Button getOkButton(){
		if ( okButton == null ){
			okButton = new Button( "OK", 0.13f, this );
		}
		
		return okButton;
	}
	
	private Button getCancelButton(){
		if ( cancelButton == null ){
			cancelButton = new Button( "Cancel", 0.21f, this );
		}
		
		return cancelButton;
	}
	
	private List<String> getLines(){
		return lines;
	}
	
	private Type getMode(){
		return mode;
	}
	
	public int getResult(){
		return this.result;
	}
	
	public boolean isShowing(){
		return show;
	}
	
	public ActionHandler getUpstreamHandler(){
		return upstreamHandler;
	}
}
