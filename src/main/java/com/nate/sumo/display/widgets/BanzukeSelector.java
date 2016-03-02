package com.nate.sumo.display.widgets;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.model.Vector3f;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.model.animation.Animation;
import com.nate.sumo.model.basho.Banzuke;
import com.nate.sumo.model.basho.Division;
import com.nate.sumo.model.basho.Rank;
import com.nate.sumo.model.basho.Rank.RankClass;
import com.nate.sumo.model.basho.Rank.RankSide;
import com.nate.sumo.model.rikishi.Rikishi;
import com.nate.util.MathHelper;

public class BanzukeSelector extends Widget
{
	private Map<RankClass, Integer> rankHighNumberMap;
	
	private Integer currentDivision = 0;
	private Banzuke banzuke;
	
	private Rikishi nishiRikishi;
	private Rikishi higashiRikishi;
	
	private boolean higashiSelected = false;
	private boolean nishiSelected = false;
	
	private Animation selectorAnimation;
	private Animation tableAnimation;
	
	private final float BUTTON_WIDTH = 0.17f;
	private final float BUTTON_BUMPER = 0.02f;
	private final float DIV_TEXT_WIDTH = 0.6f;
	private final float SPINNER_HEIGHT = 0.09f;
	private final float B_WIDTH = BUTTON_BUMPER*2 + BUTTON_WIDTH*2 + DIV_TEXT_WIDTH;
	
	private final float titleHeight = 0.12f;
	private final float rankColWidth = 0.16f;
	
	private final int SCREEN_MOVE_FRAMES = 10;
	private final Vector3f INITIAL_SELECTOR = new Vector3f( 0.0f, -1.0f * (titleHeight + (2*BUTTON_BUMPER) + titleHeight) - 0.05f, 0.0f );
	private final Vector3f INITIAL_BG_POSITION = new Vector3f( 0.0f, 0.0f, 0.0f );
	
	public BanzukeSelector( Banzuke banzuke )
	{
		super();
		
		this.banzuke = banzuke;
		
		Rank y1 = new Rank( RankClass.YOKOZUNA, RankSide.EAST, 1 );
		setRikishi( this.banzuke.getByRank( Division.values()[0], y1 ) );
		
		selectorAnimation = new Animation( INITIAL_SELECTOR, INITIAL_SELECTOR, 0 );
		tableAnimation = new Animation( INITIAL_BG_POSITION, INITIAL_BG_POSITION, -1 );
	}
	
	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( action != GLFW_PRESS ){
			return;
		}
		
		if ( getSelectorAnimation().isRunning() ){
			return;
		}
		
		Division cDiv = Division.values()[currentDivision];
		Rank rank = getRikishi().getRikishiInfo().getCurrentRank();
		
		if ( key == GLFW_KEY_BACKSPACE ){
			if ( isNishiSelected() ){
				nishiSelected = false;
			}
			else {
				higashiSelected = false;
				nishiRikishi = null;
			}
			
			setRikishi( getCurrentTopRikishi() );
			selectorAnimation = new Animation( INITIAL_SELECTOR, INITIAL_SELECTOR, SCREEN_MOVE_FRAMES );
			tableAnimation = new Animation( INITIAL_BG_POSITION, INITIAL_BG_POSITION, -1 );
		}
		
		if ( isHigashiSelected() && isNishiSelected() ){
			return;
		}
		
		switch( key ){
			case GLFW_KEY_COMMA:
				currentDivision--;
				selectorAnimation = new Animation( INITIAL_SELECTOR, INITIAL_SELECTOR, SCREEN_MOVE_FRAMES );
				fixDivision();
				setRikishi( getCurrentTopRikishi() );
				break;
			case GLFW_KEY_PERIOD:
				currentDivision++;
				selectorAnimation = new Animation( INITIAL_SELECTOR, INITIAL_SELECTOR, SCREEN_MOVE_FRAMES );
				fixDivision();
				setRikishi( getCurrentTopRikishi() );
				break;
			case GLFW_KEY_DOWN:
				moveSelectorDown( rank, cDiv );
				getSelectorAnimation().start();
				getTableAnimation().start();
				break;
			case GLFW_KEY_UP:
				moveSelectorUp( rank, cDiv );
				getSelectorAnimation().start();
				getTableAnimation().start();
				break;
			case GLFW_KEY_RIGHT:
				moveSelectorRight( rank, cDiv );
				getSelectorAnimation().start();
				break;
			case GLFW_KEY_LEFT:
				moveSelectorLeft( rank, cDiv );
				getSelectorAnimation().start();
				break;
			case GLFW_KEY_ENTER:
				if ( !isHigashiSelected() ){
					higashiSelected = true;
					setRikishi( getCurrentTopRikishi() );
					tableAnimation = new Animation( INITIAL_BG_POSITION, INITIAL_BG_POSITION, -1 );
					selectorAnimation = new Animation( INITIAL_SELECTOR, INITIAL_SELECTOR, SCREEN_MOVE_FRAMES );
				}
				else {
					nishiSelected = true;
				}
				break;
			default:
				break;
		}
		

	}
	
	private void fixDivision(){
		
		if ( currentDivision >= Division.values().length ){
			currentDivision = 0;
		}
		
		if ( currentDivision < 0 ){
			currentDivision = Division.values().length - 1;
		}
	}
	
	private void moveSelectorDown( Rank rank, Division cDiv ){
		float goDown = 0.0f;
		
		// we're at the end of a division
		if ( rank.getRankNumber() >= getHighRankNumberMap().get( rank.getRankClass() ) ){
			
			// if it's the last division we can't go down anymore
			if ( rank.getRankClass().equals( cDiv.getClassesIncluded().get(  cDiv.getClassesIncluded().size() - 1 ) ) ){
				return;
			}
			
			goDown -= ((4*BUTTON_BUMPER) + (titleHeight)); 
	
		}
		
		goDown -= (titleHeight + BUTTON_BUMPER);
		
		selectorAnimation = new Animation( getSelectorAnimation().getEndingValue(), 
				new Vector3f( getSelectorAnimation().getEndingValue().getX(),
					getSelectorAnimation().getEndingValue().getY() + goDown, 
					getSelectorAnimation().getEndingValue().getZ() ), SCREEN_MOVE_FRAMES );
		
		if ( getSelectorAnimation().getEndingValue().getY() <= -0.9f ){
			
			if ( cDiv.getClassesIncluded().get(  cDiv.getClassesIncluded().size() - 1 ) != rank.getRankClass() ||
				getHighRankNumberMap().get( rank.getRankClass() ) - rank.getRankNumber() > 4){
			
				tableAnimation = new Animation( getTableAnimation().getEndingValue(),
					new Vector3f( getTableAnimation().getEndingValue().getX(),
						getTableAnimation().getEndingValue().getY() + -1.0f*goDown,
						getTableAnimation().getEndingValue().getZ() ), SCREEN_MOVE_FRAMES );
			}
		}
		
		Rank newRank = null;
		
		// set the rikishi
		if ( rank.getRankNumber() >= getHighRankNumberMap().get(  rank.getRankClass() ) ){
			// move to the next rank
			newRank = new Rank( cDiv.getClassesIncluded().get( cDiv.getClassesIncluded().indexOf( rank.getRankClass() ) + 1 ),
				rank.getRankSide(), 1 );
		}
		else {
			newRank = new Rank( rank.getRankClass(), rank.getRankSide(), rank.getRankNumber() + 1 );
		}
		
		Rikishi r = getBanzuke().getByRank( cDiv, newRank );
		
		if ( r != null ){
			setRikishi( r );
		}
		else {
			moveSelectorDown( newRank, cDiv );
		}
	}
	
	private void moveSelectorUp( Rank rank, Division cDiv ){
		float goUp = 0.0f;
		
		if ( rank.getRankNumber() == 1 ){
		
			// if it's at the top we can't go up anymore
			if ( MathHelper.equalsf( getSelectorAnimation().getEndingValue().getY(), INITIAL_SELECTOR.getY() ) ){
				return;
			}
			
			goUp += (titleHeight + (4*BUTTON_BUMPER));
		}
		
		goUp += titleHeight + BUTTON_BUMPER;
		
		selectorAnimation = new Animation( getSelectorAnimation().getEndingValue(), 
				new Vector3f( getSelectorAnimation().getEndingValue().getX(),
					getSelectorAnimation().getEndingValue().getY() + goUp, 
					getSelectorAnimation().getEndingValue().getZ() ), SCREEN_MOVE_FRAMES );
		
		if ( getSelectorAnimation().getEndingValue().getY() <= -0.6f ){
			
			if ( cDiv.getClassesIncluded().get(  cDiv.getClassesIncluded().size() - 1 ) != rank.getRankClass() ||
				getHighRankNumberMap().get( rank.getRankClass() ) - rank.getRankNumber() > 3){
			
				tableAnimation = new Animation( getTableAnimation().getEndingValue(),
					new Vector3f( getTableAnimation().getEndingValue().getX(),
						getTableAnimation().getEndingValue().getY() + -1.0f*goUp,
						getTableAnimation().getEndingValue().getZ() ), SCREEN_MOVE_FRAMES );
			}
		}
		
		// get the rikishi
		Rank newRank2 = null;
		
		if ( rank.getRankNumber() == 1 ){
			RankClass newRankClass = cDiv.getClassesIncluded().get( cDiv.getClassesIncluded().indexOf( rank.getRankClass() ) - 1 ); 
			newRank2 = new Rank( newRankClass,
				rank.getRankSide(), 
				getHighRankNumberMap().get( newRankClass ) );
		}
		else {
			newRank2 = new Rank( rank.getRankClass(), rank.getRankSide(), rank.getRankNumber() - 1 );
		}
		
		Rikishi r = getBanzuke().getByRank( cDiv, newRank2 );
		
		if ( r != null ){
			setRikishi( r );
		}
		else {
			moveSelectorUp( newRank2, cDiv );
		}
	}
	
	private void moveSelectorRight( Rank rank, Division cDiv ){
		
		if ( rank.getRankSide().equals( RankSide.WEST ) ){
			return;
		}
		
		Rank newRank = new Rank( rank.getRankClass(), RankSide.WEST, rank.getRankNumber() );
		Rikishi r = getBanzuke().getByRank( cDiv, newRank ); 
		
		if ( r == null ){
			return;
		}
		
		setRikishi( r );
		
		selectorAnimation = new Animation( getSelectorAnimation().getEndingValue(),
			new Vector3f( getSelectorAnimation().getEndingValue().getX() + rankColWidth + ((B_WIDTH - rankColWidth)/2.0f),
					getSelectorAnimation().getEndingValue().getY(),
					getSelectorAnimation().getEndingValue().getZ() ), SCREEN_MOVE_FRAMES );
	}
	
	private void moveSelectorLeft( Rank rank, Division cDiv ){
		
		if ( rank.getRankSide().equals( RankSide.EAST ) ){
			return;
		}
		
		Rank newRank = new Rank( rank.getRankClass(), RankSide.EAST, rank.getRankNumber() );
		Rikishi r = getBanzuke().getByRank( cDiv, newRank ); 
		
		if ( r == null ){
			return;
		}
		
		setRikishi( r );
		
		selectorAnimation = new Animation( getSelectorAnimation().getEndingValue(),
				new Vector3f( getSelectorAnimation().getEndingValue().getX() - (rankColWidth + ((B_WIDTH - rankColWidth)/2.0f)),
						getSelectorAnimation().getEndingValue().getY(),
						getSelectorAnimation().getEndingValue().getZ() ), SCREEN_MOVE_FRAMES );
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
			
			glPushMatrix();
			
				if ( getTableAnimation() != null ){
					
					if ( getTableAnimation().isRunning() ){
						getTableAnimation().advance();
					}
					
					glTranslatef( getTableAnimation().getValue().getX(),
						getTableAnimation().getValue().getY(), 
						getTableAnimation().getValue().getZ() );
				}
			
				glPushMatrix();
				
					glDisable( GL_TEXTURE_2D );
					glTranslatef( 0.0f, -0.05f, 0.0f );
					
					for ( RankClass rClass : cDiv.getClassesIncluded() ){
						drawRankBox( rClass );
						glTranslatef( 0.0f, -1.0f * (BUTTON_BUMPER*2), 0.0f );
					}
				
				glPopMatrix();
							
				glPushMatrix();			
					drawSelectors();
				glPopMatrix();
			glPopMatrix();
		
			// draw the division spinner
			glPushMatrix();
			
				glDisable( GL_TEXTURE_2D );
				glColor4f( 0.0f, 0.0f, 0.0f, 0.7f );
				ScreenHelper.getInstance().drawSquare( (2*BUTTON_WIDTH) + DIV_TEXT_WIDTH, SPINNER_HEIGHT, false );
			
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glEnable( GL_TEXTURE_2D );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_LB ) );
				ScreenHelper.getInstance().drawSquare( BUTTON_WIDTH, SPINNER_HEIGHT, true );
				
				glTranslatef( 0.19f, 0.0f, 0.0f );
				
				glDisable( GL_TEXTURE_2D );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );

				int chars = cDiv.getName().getFirstName_kanji().length();
				float totalWidth = chars * 0.12f;
				float startingSpot = (DIV_TEXT_WIDTH / 2.0f) - (totalWidth / 2.0f);
				
				glPushMatrix();
				glTranslatef( startingSpot, 0.0f, 0.0f );
				Font.JAPANESE_CALI.drawJapaneseString( cDiv.getName().getFirstName_kanji() );
				glPopMatrix();
				
				glTranslatef( 0.02f + 0.6f, 0.0f, 0.0f );
				glEnable( GL_TEXTURE_2D );
				glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_RB ) );
				ScreenHelper.getInstance().drawSquare( BUTTON_WIDTH, SPINNER_HEIGHT, true );
			
			glPopMatrix();
			
			glPushMatrix();
				drawControlHelp();
			glPopMatrix();
			
			glDisable( GL_TEXTURE_2D );
			glDisable( GL_BLEND );
		
		glPopMatrix();
	}
	
	private void drawControlHelp(){
		
		glTranslatef( 0.0f, -2.31f, 0.0f );
		
		glDisable( GL_TEXTURE_2D );
		
		glColor4f( 0.0f, 0.0f, 0.0f, 0.7f );
		ScreenHelper.getInstance().drawSquare( B_WIDTH, SPINNER_HEIGHT + 0.03f, false );
		
		glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
		glTranslatef( 0.0f, 0.01f, 0.0f );
		glEnable( GL_TEXTURE_2D );
		glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_B ) );
		ScreenHelper.getInstance().drawSquare( 0.09f, 0.1f, true );
		
		glTranslatef( 0.12f, 0.03f, 0.0f );
		Font.TIMES_NEW_ROMAN.drawString( "Back" );
		
		glTranslatef( 0.25f, -0.03f, 0.0f );
		glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( TextureNames.CTL_A ) );
		ScreenHelper.getInstance().drawSquare( 0.09f, 0.1f, true );
		
		glTranslatef( 0.12f, 0.03f, 0.0f );
		Font.TIMES_NEW_ROMAN.drawString( "Select" );
		
		glDisable( GL_TEXTURE_2D );
		glDisable( GL_BLEND );
	}
	
	private void drawRankBox( RankClass clazz ){
		
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
			
			float zBox = 0.8f;
			
			if ( getRikishi() != null && getRikishi().equals( rikishiE ) &&
				getSelectorAnimation() != null && !getSelectorAnimation().isRunning() ){
				zBox = 1.0f;
			}
			
			glColor4f( 1.0f, 1.0f, 1.0f, zBox );
			ScreenHelper.getInstance().drawSquare( (B_WIDTH - rankColWidth) / 2.0f, titleHeight, false );
			
			glPushMatrix();
			
				zBox = 0.8f;
			
				if ( getRikishi() != null && getRikishi().equals( rikishiW ) &&
					getSelectorAnimation() != null && !getSelectorAnimation().isRunning() ){
					zBox = 1.0f;
				}
				
				glColor4f( 1.0f, 1.0f, 1.0f, zBox );
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
	
	private void drawSelectors(){
		
		if ( getSelectorAnimation() != null ){
			getSelectorAnimation().advance();
		}
		
		glDisable( GL_TEXTURE_2D );
	
		glTranslatef( getSelectorAnimation().getValue().getX(), 
			getSelectorAnimation().getValue().getY(), 
			getSelectorAnimation().getValue().getZ() );
		
		if ( !isHigashiSelected() ){
			glColor4f( 0.0f, 0.0f, 0.9f, 0.8f );
		}
		else {
			glColor4f( 0.9f, 0.0f, 0.0f, 0.8f );
		}
		
		ScreenHelper.getInstance().drawSquare( (B_WIDTH - rankColWidth)/2.0f, titleHeight, false, false, 10.0f );
		
		if ( !isHigashiSelected() ){
			glTranslatef( 0.005f, titleHeight - 0.05f, 0.0f );
		}
		else {
			glTranslatef( ((B_WIDTH - rankColWidth)/2.0f) - 0.15f, titleHeight - 0.05f, 0.0f );
		}
		
		glScalef( 0.5f, 0.5f, 0.0f );
		
		String selectionText = "東力士";
		
		if ( isHigashiSelected() ){
			selectionText = "西力士";
		}
		
		Font.JAPANESE_CALI.drawJapaneseString( selectionText );
	}

	private float forCenteredText( String text, float width, float scale ){
		int chars = text.length();
		float totalWidth = chars * 0.09f;
		width = width + ((1.0f - scale)*width);
		float startingSpot = (width / 2.0f) - (totalWidth / 2.0f);
		
		return startingSpot;
	}
	
	/**
	 * Find the top rikishi in the selected division
	 * @return
	 */
	private Rikishi getCurrentTopRikishi(){
		
		Division cDiv = Division.values()[currentDivision];
		Rank rank = new Rank( cDiv.getClassesIncluded().get( 0 ),
			RankSide.EAST, 1 );
		
		return getBanzuke().getByRank( cDiv, rank );
	}
	
	@Override
	public List<String> getTextureNames()
	{
		if ( textureNames == null ){
			textureNames = Arrays.asList( TextureNames.CTL_LB, TextureNames.CTL_RB, TextureNames.CTL_A, TextureNames.CTL_B,
				TextureNames.CTL_X, TextureNames.CTL_Y );
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
	
	private void setRikishi( Rikishi rikishi ){
		
		if ( isHigashiSelected() ){
			nishiRikishi = rikishi;
		}
		else {
			higashiRikishi = rikishi;
		}
	}
	
	private Rikishi getRikishi(){
		if ( isHigashiSelected() ){
			return nishiRikishi;
		}
		else {
			return higashiRikishi;
		}
	}
	
	public Boolean isHigashiSelected(){
		return higashiSelected;
	}
	
	public Boolean isNishiSelected(){
		return nishiSelected;
	}
	
	public Rikishi getNishiRikishi(){
		return nishiRikishi;
	}
	
	public Rikishi getHigashiRikishi(){
		return higashiRikishi;
	}
	
	private Animation getSelectorAnimation(){
		return selectorAnimation;
	}
	
	private Animation getTableAnimation(){
		return tableAnimation;
	}

}
