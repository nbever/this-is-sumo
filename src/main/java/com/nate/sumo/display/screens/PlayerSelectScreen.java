package com.nate.sumo.display.screens;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.lwjgl.opengl.GLContext;

import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.display.widgets.BanzukeSelector;
import com.nate.sumo.model.basho.Banzuke;
import com.nate.sumo.model.rikishi.Rikishi;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public abstract class PlayerSelectScreen extends SwipeScreen
{
	public static final String PLAYER_LIST = "player_list";

	private final Integer QUEUE_LIMIT = 10;
	private final float IMAGE_HEIGHT = 0.6f;
	private final float IMAGE_WIDTH = 0.32f;
	
	private Map<Rikishi, Integer> textureMap;
	private List<Rikishi> textureQueue;
	private BanzukeSelector banzukeSelector;
	
	private Long stayFrame = 0L;
	
	public PlayerSelectScreen(Map<String, Object> initData) {
		super( initData );
	}

	@Override
	public void drawScreen()
	{
		stayFrame++;
		
		// load if we've been sitting there and it's been a little while.
		if ( stayFrame > 30L ){
			
			if ( getBanzukeSelector().getHigashiRikishi() != null && getTextureMap().get( getBanzukeSelector().getHigashiRikishi() ) == null ){
				loadTexture( getBanzukeSelector().getHigashiRikishi() );
			}
			
			if ( getBanzukeSelector().getNishiRikishi() != null && getTextureMap().get( getBanzukeSelector().getNishiRikishi() ) == null ){
				loadTexture( getBanzukeSelector().getNishiRikishi() );
			}
		}
		
		glPushMatrix();
			glTranslatef( -0.95f, 0.9f, ScreenHelper.SCREEN_DEPTH );
			glScalef( 0.8f, 0.8f, 0.0f );
			getBanzukeSelector().draw();
		glPopMatrix();
		
		glPushMatrix();
			glTranslatef( -0.05f, 0.9f, ScreenHelper.SCREEN_DEPTH );
			drawRikishiSelection( getBanzukeSelector().getHigashiRikishi(), "東" );
			
			if ( getBanzukeSelector().getNishiRikishi() != null ){
				glTranslatef( 0.0f, -0.9f, ScreenHelper.SCREEN_DEPTH );
				drawRikishiSelection( getBanzukeSelector().getNishiRikishi(), "西" );
			}
		glPopMatrix();
	}
	
	private void drawRikishiSelection( Rikishi rikishi, String side ){
		
		glEnable( GL_TEXTURE_2D );
		glEnable( GL_BLEND );
		
		glPushMatrix();
			glColor3f( 1.0f, 1.0f, 1.0f );
			glTranslatef( 0.0f, -0.15f, 0.0f );
			
			Font.JAPANESE_CALI.drawJapaneseString( side );
			
			glDisable( GL_TEXTURE_2D );
			glTranslatef( 0.0f, -1.0f*IMAGE_HEIGHT, 0.0f );
			
			if ( getTextureMap().get( rikishi ) != null && getTextureMap().get( rikishi ) != -1 ){
				glEnable( GL_TEXTURE_2D );
				glBindTexture( GL_TEXTURE_2D, getTextureMap().get( rikishi ) );
			}
			
			ScreenHelper.getInstance().drawSquare( IMAGE_WIDTH, IMAGE_HEIGHT, true );
			
			glTranslatef( 0.0f, -0.1f, 0.0f );
			glDisable( GL_TEXTURE_2D );
			glColor3f( 1.0f, 1.0f, 1.0f );
			Font.JAPANESE_CALI.drawJapaneseString( rikishi.getRikishiInfo().getShikona().getFirstName_kanji() + " " +  
				rikishi.getRikishiInfo().getShikona().getLastName_kanji() );
			
		glPopMatrix();
		
		glPushMatrix();
		
			glEnable( GL_TEXTURE_2D );
			glTranslatef( IMAGE_WIDTH + 0.05f, -0.2f, 0.0f );
			glScalef( 0.7f, 0.7f, 0.0f );
			
			List<String> labels = Arrays.asList( "Shikona:", "Heya:", "", "Record:", "Overall:" );
			
			labels.stream().forEach( (l) -> {
				Font.TIMES_NEW_ROMAN.drawString( l );
				glTranslatef( 0.0f, -0.06f, 0.0f );
			});
			
		glPopMatrix();
		
		glPushMatrix();
			glEnable( GL_TEXTURE_2D );
			glTranslatef( IMAGE_WIDTH + 0.2f, -0.2f, 0.0f );
			glScalef( 0.7f, 0.7f, 0.0f );
		
			String heyaNameEn = "";
			String heyaNameJp = "";
			
			if ( rikishi.getRikishiInfo().getHeya() != null ){
				heyaNameEn = "(" + rikishi.getRikishiInfo().getHeya().getHeyaName().getFirstName_en() + ")";
				heyaNameJp = rikishi.getRikishiInfo().getHeya().getHeyaName().getFirstName_kanji();
			}
			
			List<String> valueLabels = Arrays.asList( rikishi.getRikishiInfo().getShikona().getFirstName_en() + " " + rikishi.getRikishiInfo().getShikona().getLastName_en(), 
				"",
				heyaNameEn,
				rikishi.getRikishiInfo().getCareerRecord().toString(),
				rikishi.getRikishiStats().getOverallSkill().intValue() + "" );
			
			valueLabels.stream().forEach( (vl) -> {
				Font.TIMES_NEW_ROMAN.drawString( vl );
				glTranslatef( 0.0f, -0.06f, 0.0f );
			});
			
			glTranslatef( 0.0f, 0.30f, 0.0f);
			Font.JAPANESE_CALI.drawString( heyaNameJp );
		
		glPopMatrix();
	}
	
	@Override
	public void handleKey( int key, int scanCode, int action, int mods )
	{
		if ( key == GLFW_KEY_LEFT || key == GLFW_KEY_RIGHT ||
			key == GLFW_KEY_UP || key == GLFW_KEY_DOWN ){
			stayFrame = 0L;
		}
		
		getBanzukeSelector().handleKey(key, scanCode, action, mods);
	}
	
	private BanzukeSelector getBanzukeSelector(){
		
		if ( banzukeSelector == null ){
			banzukeSelector = new BanzukeSelector( (Banzuke)getInitData().get( PLAYER_LIST ));
		}
		
		return banzukeSelector;
	}
	
	private List<Rikishi> getRikishiTextureQueue(){
		if ( textureQueue == null ){
			textureQueue = new ArrayList<>();
		}
		return textureQueue;
	}
	
	private Map<Rikishi, Integer> getTextureMap(){
		if ( textureMap == null ){
			textureMap = new HashMap<Rikishi, Integer>();
		}
		
		return textureMap;
	}
	
	private void unloadTexture(){
		Rikishi toRemove = getRikishiTextureQueue().remove( 0 );
		TextureManager.getInstance().releaseTexture( getTextureMap().get( toRemove ) );
		getTextureMap().remove( toRemove );
	}
	
	private void loadTexture( Rikishi r ){
		
		// if it's already there move it to the most recent
		// (the end of the list) and return because the
		// map is valid.
		if ( getRikishiTextureQueue().contains( r ) ){
			int index = getRikishiTextureQueue().indexOf( r );
			getRikishiTextureQueue().remove( index );
			getRikishiTextureQueue().add( r );
			return;
		}
		
		// not in the map so make room if necessary.
		if ( getRikishiTextureQueue().size() >= QUEUE_LIMIT ){
			unloadTexture();
		}
		
		getTextureMap().put( r, -1 );
		
//		// load the new texture
//		Runnable run = new Runnable(){
//			@Override
//			public void run()
//			{
				
				String portrait =  r.getRikishiInfo().getShikona().getFirstName_kanji() + "_" + 
					r.getRikishiInfo().getShikona().getLastName_kanji() + ".jpg";
				
				if ( r.getAppearenceMap() != null && r.getAppearenceMap().getPortrait() != null ){
					portrait = r.getAppearenceMap().getPortrait();
				}
				
				Integer ref = -1;
				
				try {
					ref = TextureManager.getInstance().loadTexture( "portraits/" + portrait );
				}
				catch( Exception e ){
					try
					{
						ref = TextureManager.getInstance().loadTexture( "portraits/no_image.jpg" );
					}
					catch (Exception e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if ( ref != -1 ){
					getTextureMap().put( r, ref );
				}
//			}
//		};
//		
//		Thread th = new Thread( run );
//		th.start();
	}
	
	@Override
	public List<String> getTextureNames()
	{
		if ( textureNames == null ){
			textureNames = Arrays.asList();
		}
		
		return textureNames;
	}
	
	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
		
	}
	
}
