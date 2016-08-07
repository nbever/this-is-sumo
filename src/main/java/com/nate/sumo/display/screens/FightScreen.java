package com.nate.sumo.display.screens;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import objimp.ObjImpScene;

import com.nate.sumo.display.Screen;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.ScreenManager;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.model.fight.Fight;
import com.nate.sumo.model.fight.RikishiStatus;

import static org.lwjgl.opengl.GL11.*;

public class FightScreen extends Screen {

	private ObjImpScene dohyo;
	private float v_rot = 0.0f;//10.0f;
	private float h_rot = 90.0f;
	
	private Fight fight;
	
	public FightScreen(Map<String, Object> initData) {
		super( initData );
		
		fight = (Fight)initData.get( Fight.class.getSimpleName() );
	}

	@Override
	public List<String> getTextureNames() {
		// TODO Auto-generated method stub
		return Arrays.asList( "dohyo_tex.tga" );
	}

	@Override
	public void drawScreen() {
		glPushMatrix();
			
			glEnable( GL_BLEND );
			glEnable( GL_TEXTURE_2D );
			glEnable( GL_DEPTH_TEST );
			glClear( GL_DEPTH_BUFFER_BIT );
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			glTranslatef( 0.0f, 0.0f, ScreenHelper.SCREEN_DEPTH );
			
			glColor3f( 1.0f, 1.0f, 1.0f );
//			Font.JAPANESE_CALI.drawJapaneseString( "ロヂング。。。" );
//			Font.TIMES_NEW_ROMAN.drawString( "Test 1 2" );
			
			glPushMatrix();
			
				glTranslatef( 0.0f, -1.0f, -3.0f );
				glScalef( 0.6f, 0.6f, 0.6f );
				glDisable( GL_BLEND );
//				glEnable( GL_CULL_FACE );
				
				glRotatef( v_rot, 1.0f, 0.0f, 0.0f );
				glRotatef( h_rot, 0.0f, 1.0f, 0.0f );
			
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( "dohyo_tex.tga" ) );
				dohyo.drawWithExternalTextureMap();
				
				glDisable( GL_TEXTURE_2D );
				
				glPushMatrix();
					glColor3f( 1.0f, 1.0f, 0.0f );
					glTranslatef( 0.0f, 0.45f, 0.0f );
					glRotatef( 90.0f, 1.0f, 0.0f, 0.0f );

					glScalef( 0.32f, 0.32f, 0.32f );
					ScreenHelper.getInstance().drawCircle( 7.5f, 36, 10.0f );
					
					glPushMatrix();
						glColor3f( 0.0f, 1.0f, 1.0f );
						float WIDTH = 18.0f;
						glTranslatef( -1.0f*(WIDTH/2.0f), -1.0f*(WIDTH/2.0f), 0.0f );
						ScreenHelper.getInstance().drawSquare( WIDTH, WIDTH, false, false, 10.0f );
					glPopMatrix();
					
					glPushMatrix();
						glColor3f( 1.0f, 1.0f, 1.0f );
						
						glBegin( GL_LINE_STRIP );
							glVertex3f( -1.5f, -1.25f, 0.0f );
							glVertex3f( 1.5f, -1.25f, 0.0f );
						glEnd();
						
						glBegin( GL_LINE_STRIP );
							glVertex3f( -1.5f, 1.25f, 0.0f );
							glVertex3f( 1.5f, 1.25f, 0.0f );
						glEnd();
					glPopMatrix();
				
					getFight().advance();
					
					glPushMatrix();
					
//						glColor3f( 0.6f, 0.0f, 1.0f );
//						glLineWidth( 1.0f );
//						
//						glBegin( GL_LINE_STRIP );
//							glVertex3f( 6.0f, -5.0f, 0.0f );
//							glVertex3f( 6.0f, -5.0f, -3.0f );
//						glEnd();
//					
						setRikishiPosition( getFight().getEastStatus() );
						
						glRotatef( 180.0f, 1.0f, 0.0f, 0.0f );
						glScalef( 1.6f, 1.6f, 1.6f );
						
						getFight().advance();
						getFight().getEastStatus().draw();
					glPopMatrix();
					
					glPushMatrix();
					
						setRikishiPosition( getFight().getWestStatus() );
						
						glRotatef( 180.0f, 1.0f, 0.0f, 0.0f );
						glScalef( 1.6f, 1.6f, 1.6f );
						
						getFight().getWestStatus().draw();
					glPopMatrix();
//					glPushMatrix();
//						glTranslatef( 0.0f, -2.0f, 0.0f );
//						
//						glBegin( GL_LINE_STRIP );
//							glVertex3f( 0.0f, 0.0f, 0.0f );
//							glVertex3f( 0.0f, 0.0f, -3.0f );
//						glEnd();
//						
//					glPopMatrix();
					
				glPopMatrix();

			glPopMatrix();
			
			glDisable( GL_DEPTH_TEST );
			
		glPopMatrix();
	}
	
	/**
	 * Abstracted this method so we could deal with the insanity of the coordinate flip flop one place
	 * 
	 * It also deals with rotations.  It wants to go counter-clockwise with 0 point toward the viewer. 
	 * That means we're off by 270 degrees and need to flip the rotation in order for the "facing" value
	 * to make sense.
	 * @param status
	 */
	private void setRikishiPosition( RikishiStatus status ){
		glTranslatef( status.getFightCoordinates().getY(), status.getFightCoordinates().getX(), 0.0f );
		glRotatef( -1.0f * (status.getFightCoordinates().getFacing() - 270.0f), 0.0f, 0.0f, 1.0f );
		glTranslatef( -1.6f, 0.0f, 0.0f );
	}

	@Override
	public void drawClosing() {
		closeComplete();
	}

	@Override
	public void drawLoading() {
		try {
			ScreenManager.getInstance().setBackgroundTexture( null );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dohyo = new ObjImpScene();
			
			URL fileUrl = FightScreen.class.getResource( "/d2.obj" );
			File file = new File( fileUrl.toURI() );
			
			dohyo.load( file.getAbsolutePath(), "dohyo_tex.tga" );

			getFight().getEastStatus().load();
			getFight().getWestStatus().load();
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		
		loadComplete();
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public ScreenInitData getNextScreenData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleKey(int key, int scanCode, int action, int mods) {

	}
	
	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		if ( lateral > 0 ){
			h_rot += 5.0f;
		}
		else if ( lateral < 0 ){
			h_rot -= 5.0f;
		}
		
		if ( vertical > 0 ){
			v_rot += 5.0f;
		}
		else if ( vertical < 0 ){
			v_rot -= 5.0f;
		}		
	}
	
	private Fight getFight(){
		return fight;
	}
}
