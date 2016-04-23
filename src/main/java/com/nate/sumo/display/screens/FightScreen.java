package com.nate.sumo.display.screens;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import objimp.ObjImpMesh;
import objimp.ObjImpScene;

import com.nate.model.MD5Model;
import com.nate.sumo.display.Screen;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.ScreenManager;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.fonts.Font;
import com.nate.sumo.model.animation.ModelAnimationInfo;
import com.nate.sumo.model.fight.FightStatus;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class FightScreen extends Screen {

	private ObjImpScene dohyo;
	private float v_rot = 10.0f;
	private float h_rot = 90.0f;
	
	private FightStatus fight;
	private ModelAnimationInfo eastModel;
	private ModelAnimationInfo westModel;
	
	public FightScreen(Map<String, Object> initData) {
		super( initData );
		
		fight = (FightStatus)initData.get( FightStatus.class.getSimpleName() );
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
			glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
			
			glTranslatef( 0.0f, 0.0f, ScreenHelper.SCREEN_DEPTH );
			
			glColor3f( 1.0f, 1.0f, 1.0f );
			Font.JAPANESE_CALI.drawJapaneseString( "ロヂング。。。" );
			Font.TIMES_NEW_ROMAN.drawString( "Test 1 2" );
			
			glPushMatrix();
			
				glTranslatef( 0.0f, -1.0f, -3.0f );
				glScalef( 0.55f, 0.55f, 0.55f );
				glDisable( GL_BLEND );
				glEnable( GL_CULL_FACE );
				
				glRotatef( v_rot, 1.0f, 0.0f, 0.0f );
				glRotatef( h_rot, 0.0f, 1.0f, 0.0f );
			
				glBindTexture( GL_TEXTURE_2D, TextureManager.getInstance().getTextureId( "dohyo_tex.tga" ) );
				dohyo.drawWithExternalTextureMap();
				glDisable( GL_CULL_FACE );
				
//				glDisable( GL_TEXTURE_2D );
				getEastModel().draw();
				
			glPopMatrix();
			
		glPopMatrix();
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

			//appMap.loadstuff
			eastModel = new ModelAnimationInfo( getFight().getEastStatus().getRikishi().getAppearenceMap() );
			
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
		if ( key == GLFW_KEY_RIGHT ){
			h_rot += 5.0f;
		}
		else if ( key == GLFW_KEY_LEFT ){
			h_rot -= 5.0f;
		}
		else if ( key == GLFW_KEY_UP ){
			v_rot += 5.0f;
		}
		else if ( key == GLFW_KEY_DOWN ){
			v_rot -= 5.0f;
		}
	}
	
	private FightStatus getFight(){
		return fight;
	}
	
	private ModelAnimationInfo getEastModel(){
		return eastModel; 
	}
	
	private ModelAnimationInfo getWestModel(){
		return westModel;
	}

}
