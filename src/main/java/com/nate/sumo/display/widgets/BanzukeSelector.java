package com.nate.sumo.display.widgets;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.model.basho.Banzuke;
import com.nate.sumo.model.basho.Division;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.basho.Rank.RankSide;
import com.nate.sumo.model.rikishi.Rikishi;

public class BanzukeSelector extends Widget
{
	private Map<RankClass, Integer> rankHighNumberMap;
	
	private Integer currentDivision = 0;
	private Banzuke banzuke;
	
	private static final float BUTTON_WIDTH = 0.17f;
	private static final float BUTTON_BUMPER = 0.02f;
	private static final float DIV_TEXT_WIDTH = 0.6f;
	private static final float B_WIDTH = BUTTON_BUMPER*2 + BUTTON_WIDTH*2 + DIV_TEXT_WIDTH;
	
	public BanzukeSelector( Banzuke banzuke )
	{
		super();
		
		this.banzuke = banzuke;
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
		Division cDiv = Division.values()[currentDivision];
		
		glPushMatrix();
		
			glEnable( GL_TEXTURE_2D );
			glEnable( GL_BLEND );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			glTranslatef( 0.05f, -0.05f, 0.0f );
			
			// draw the division spinner
			glPushMatrix();
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_LB ) );
				ScreenHelper.getInstance().drawSquare( 0.17f, 0.09f, true );
				
				glTranslatef( 0.19f, 0.0f, 0.0f );
				
				glDisable( GL_TEXTURE_2D );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );

				int chars = cDiv.getName().getFirstName_kanji().length();
				float totalWidth = chars * 0.12f;
				float startingSpot = (0.6f / 2.0f) - (totalWidth / 2.0f);
				
				glPushMatrix();
				glTranslatef( startingSpot, 0.0f, 0.0f );
				Font.JAPANESE_CALI.drawJapaneseString( cDiv.getName().getFirstName_kanji() );
				glPopMatrix();
				
				glTranslatef( 0.02f + 0.6f, 0.0f, 0.0f );
				glEnable( GL_TEXTURE_2D );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_RB ) );
				ScreenHelper.getInstance().drawSquare( 0.17f, 0.09f, true );
			
			glPopMatrix();
			
			glPushMatrix();
				
				glDisable( GL_TEXTURE_2D );
				glTranslatef( 0.0f, -0.05f, 0.0f );
				
				for ( RankClass rClass : cDiv.getClassesIncluded() ){
					drawRankBox( rClass );
					glTranslatef( 0.0f, -1.0f * (BUTTON_BUMPER*2), 0.0f );
				}
			
			glPopMatrix();
		
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
		
		glPopMatrix();
	}
	
	private void drawRankBox( RankClass clazz ){
		
		float titleHeight = 0.12f;
		float rankColWidth = 0.16f;
		
		// the title
		glDisable( GL_TEXTURE_2D );
		glColor4f( 0.6f, 0.0f, 0.8f, 0.7f );
		glTranslatef( 0.0f, -1.0f*titleHeight, 0.0f );
		ScreenHelper.getInstance().drawSquare( B_WIDTH, titleHeight, false );
		
		glPushMatrix();
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			float startAt = forCenteredText( clazz.getName().getFirstName_kanji(), B_WIDTH, 1.0f );
			glTranslatef( startAt, 0.015f, 0.0f );
			Font.JAPANESE_CALI.drawJapaneseString( clazz.getName().getFirstName_kanji() );
		glPopMatrix();
		
		//go down a bit
		glTranslatef( 0.0f, -1.0f * (BUTTON_BUMPER), 0.0f );
		
		// draw the rank column
		float rankColHeight = getHighRankNumberMap().get( clazz ) * (titleHeight + BUTTON_BUMPER) - BUTTON_BUMPER;

		glPushMatrix();
			glDisable( GL_TEXTURE_2D );
			glColor4f( 0.2f, 0.2f, 0.2f, 0.8f );
			glTranslatef( (B_WIDTH / 2.0f) - (rankColWidth / 2.0f), 
					-1.0f * (rankColHeight+BUTTON_BUMPER),
					0.0f );
			ScreenHelper.getInstance().drawSquare( rankColWidth, rankColHeight, false );
		glPopMatrix();
		
		// draw the text
		for ( int i = 1; i <= getHighRankNumberMap().get( clazz ); i++ ){
			glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
			glTranslatef( 0.0f, -1.0f * (titleHeight + BUTTON_BUMPER ), 0.0f );
			
			// find and draw the rikishi for this spot
			Rank rankE = new Rank( clazz, RankSide.EAST, i );
			Rank rankW = new Rank( clazz, RankSide.WEST, i );
			Division cDiv = Division.values()[currentDivision];
			Rikishi rikishiE = getBanzuke().getByRank( cDiv, rankE );
			Rikishi rikishiW = getBanzuke().getByRank( cDiv, rankW );
			
			// draw boxes
			glDisable( GL_TEXTURE_2D );
			glColor4f( 1.0f, 1.0f, 1.0f, 0.8f );
			ScreenHelper.getInstance().drawSquare( (B_WIDTH - rankColWidth) / 2.0f, titleHeight, false );
			
			glPushMatrix();
				glTranslatef( B_WIDTH/2.0f + rankColWidth/2.0f, 0.0f, 0.0f );
				ScreenHelper.getInstance().drawSquare( (B_WIDTH - rankColWidth) / 2.0f, titleHeight, false );
			glPopMatrix();
			
			float moreX = 0.0f;
			float rikiColWidth = (B_WIDTH / 2.0f) - (rankColWidth/2.0f);
			
			if ( rikishiE != null ){
				glColor4f( 0.0f, 0.0f, 0.0f, 1.0f );
				
				glPushMatrix();
					moreX = forCenteredText( rikishiE.getRikishiInfo().getShikona().getFirstName_kanji(), rikiColWidth, 1.0f );
					glTranslatef( moreX, 0.01f, 0.0f );
					Font.JAPANESE_CALI.drawJapaneseString( rikishiE.getRikishiInfo().getShikona().getFirstName_kanji() );
				glPopMatrix();
			}
			
			if ( rikishiW != null ){
				glColor4f( 0.0f, 0.0f, 0.0f, 1.0f );
				
				glPushMatrix();
					moreX = forCenteredText( rikishiW.getRikishiInfo().getShikona().getFirstName_kanji(), rikiColWidth, 1.0f );
					glTranslatef( moreX + rikiColWidth + rankColWidth, 0.01f, 0.0f );
					Font.JAPANESE_CALI.drawJapaneseString( rikishiW.getRikishiInfo().getShikona().getFirstName_kanji() );
				glPopMatrix();
			}
			
			glPushMatrix();
				String numStr = ScreenHelper.getInstance().getKanjiForNumber( i );
				glTranslatef( (B_WIDTH / 2.0f) - (rankColWidth / 2.0f), 0.04f, 0.0f );
				
				glScalef( 0.5f, 0.5f, 0.5f );
				float numStart = forCenteredText( numStr, rankColWidth, 0.5f );
				glTranslatef( numStart + 0.02f, 0.0f, 0.0f );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				Font.JAPANESE_CALI.drawJapaneseString( numStr );
			glPopMatrix();
		}
		
		glTranslatef( 0.0f, -1.0f * BUTTON_BUMPER, 0.0f );
	}

	private float forCenteredText( String text, float width, float scale ){
		int chars = text.length();
		float totalWidth = chars * 0.09f;
		width = width + ((1.0f - scale)*width);
		float startingSpot = (width / 2.0f) - (totalWidth / 2.0f);
		
		return startingSpot;
	}
	
	@Override
	public List<String> getTextureNames()
	{
		if ( textureNames == null ){
			textureNames = Arrays.asList( TextureNames.CTL_LB, TextureNames.CTL_RB );
		}
		
		return textureNames;
	}
	
	/**
	 * This method gives us a map with the highest number in each rank class
	 * thereby allowing us the capability to draw the appropriate boxes
	 * 
	 * @return
	 */
	private Map<RankClass, Integer> getHighRankNumberMap(){
		if ( rankHighNumberMap == null ){
			rankHighNumberMap = new HashMap<RankClass, Integer>();
			
			for ( Division d : Division.values() ){
				
				getBanzuke().getFullBanzuke().get( d ).keySet().stream().forEach( rank ->{
					
					Integer number = rankHighNumberMap.get( rank.getRankClass() );
					
					// if the rank is banzuke gai or mae-zumo there are 
					// no numbers so we'll do a running count instead
					
					if ( rank.getRankClass().equals( RankClass.BANZUKE_GAI ) || rank.getRankClass().equals( RankClass.MAE_ZUMO )){
						if ( number == null ){
							number = 0;
						}
						
						number++;
						rankHighNumberMap.put( rank.getRankClass(), number );
					}
					else {
						
						if ( number == null || rank.getRankNumber() > number ){
							rankHighNumberMap.put( rank.getRankClass(), rank.getRankNumber() );
						}
					}
				});
			}
		}
		
		return rankHighNumberMap;
	}
	
	private Banzuke getBanzuke(){
		return banzuke;
	}

}
