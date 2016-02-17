package com.nate.sumo.display.widgets;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;
import java.util.List;

import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.model.basho.Division;

public class BanzukeSelector extends Widget
{

	private Integer currentDivision = 0;
	
	public BanzukeSelector()
	{
		super();
	}
	
	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( action != GLFW_PRESS ){
			return;
		}
		
		switch( key ){
			case GLFW_KEY_COMMA:
				currentDivision++;
				break;
			case GLFW_KEY_PERIOD:
				currentDivision--;
				break;
			default:
				break;
		}
		
		if ( currentDivision >= Division.values().length ){
			currentDivision = 0;
		}
		
		if ( currentDivision < 0 ){
			currentDivision = Division.values().length - 1;
		}
	}

	@Override
	public void draw()
	{
		glPushMatrix();
		
			glEnable( GL_TEXTURE_2D );
			glEnable( GL_BLEND );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			glTranslatef( 0.05f, -0.05f, 0.0f );
			
			// draw the division spinner
			glPushMatrix();
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_LB ) );
				ScreenHelper.getInstance().drawTexturedSquare( 0.17f, 0.09f );
				
				glTranslatef( 0.19f, 0.0f, 0.0f );
				
				glDisable( GL_TEXTURE_2D );
//				glColor3f( 1.0f, 0.0f, 0.0f );
//				glBegin( GL_QUADS );
//				glVertex3f( 0.0f, 0.0f, 0.0f );
//				glVertex3f( 0.6f, 0.0f, 0.0f );
//				glVertex3f( 0.6f, 0.09f, 0.0f );
//				glVertex3f( 0.0f, 0.09f, 0.0f );
//				glEnd();
				
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				Division cDiv = Division.values()[currentDivision];
				int chars = cDiv.getName().getFirstName_kanji().length();
				float totalWidth = chars * 0.14f;
				float startingSpot = (0.6f / 2.0f) - (totalWidth / 2.0f);
				
				glPushMatrix();
				glTranslatef( startingSpot, 0.0f, 0.0f );
				Font.JAPANESE_CALI.drawJapaneseString( Division.values()[currentDivision].getName().getFirstName_kanji() );
				glPopMatrix();
				
				glTranslatef( 0.02f + 0.6f, 0.0f, 0.0f );
				glEnable( GL_TEXTURE_2D );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_RB ) );
				ScreenHelper.getInstance().drawTexturedSquare( 0.17f, 0.09f );
			
			glPopMatrix();
		
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
		
		glPopMatrix();
		
		
	}

	@Override
	public List<String> getTextureNames()
	{
		if ( textureNames == null ){
			textureNames = Arrays.asList( TextureNames.CTL_LB, TextureNames.CTL_RB );
		}
		
		return textureNames;
	}

}
