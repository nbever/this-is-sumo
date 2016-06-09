package com.nate.sumo.display.widgets;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;

import static org.lwjgl.opengl.GL11.*;

public class ButtonInstructions extends Widget {

	@Override
	public void draw() {
			
		glPushMatrix();
			glEnable( GL_BLEND );
			glDisable( GL_TEXTURE_2D );
			glScalef( 0.8f, 0.8f, 0.0f );
			glColor4f( 0.0f, 0.0f, 0.0f, 0.7f );
			ScreenHelper.getInstance().drawSquare( 0.55f, 0.09f + 0.03f, false );
			
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			glTranslatef( 0.0f, 0.01f, 0.0f );
			glEnable( GL_TEXTURE_2D );
			glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_B ) );
			ScreenHelper.getInstance().drawSquare( 0.09f, 0.1f, true );
			
			glTranslatef( 0.12f, 0.03f, 0.0f );
			Font.TIMES_NEW_ROMAN.drawString( "Back" );
			
			glTranslatef( 0.15f, -0.03f, 0.0f );
			glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_A ) );
			ScreenHelper.getInstance().drawSquare( 0.09f, 0.1f, true );
			
			glTranslatef( 0.12f, 0.03f, 0.0f );
			Font.TIMES_NEW_ROMAN.drawString( "Select" );
			
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
		glPopMatrix();
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<String> getTextureNames() {

		return Arrays.asList( TextureNames.CTL_A, TextureNames.CTL_B );
	}
}
