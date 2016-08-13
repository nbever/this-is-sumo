package com.nate.sumo.display.widgets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.sumo.DisplayRunner;
import com.nate.sumo.display.Screen;
import com.nate.sumo.display.ScreenHelper;
import com.nate.sumo.display.TextureManager;
import com.nate.sumo.display.TextureNames;
import com.nate.sumo.display.screens.ScreenInitData;
import com.nate.util.SharedLibraryLoader;

import static org.lwjgl.opengl.GL11.*;

public class TestGuitarHeroScreen extends Screen implements SequenceIf{

	public static void main( String[] args ){
		SharedLibraryLoader.load();
		TestGuitarHeroScreen screen = new TestGuitarHeroScreen( new HashMap<String, Object>() );
		TestScreenManager testManager = new TestScreenManager( screen );
		DisplayRunner dpr = new DisplayRunner( testManager );
		dpr.startLoop();
	}
	
	private GuitarHero gh;
	
	public TestGuitarHeroScreen(Map<String, Object> initData) {
		super( initData );
	}

	@Override
	public List<String> getTextureNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawScreen() {
		// TODO Auto-generated method stub
		getGuitarHero().draw();
		
//		glPushMatrix();
//			glTranslatef( -0.5f, 0.5f, -5.0f );
//			glColor3f( 1.0f, 1.0f, 1.0f );
//			glEnable( GL_BLEND );
//			glEnable( GL_TEXTURE_2D );
//			
//			int tId = TextureManager.getInstance().getTextureId( TextureNames.SHRINE );
//			
//			glBindTexture( GL_TEXTURE_2D, tId );
//			ScreenHelper.getInstance().drawSquare( 0.5f, 0.5f, true );
//			glDisable( GL_TEXTURE_2D );
//			glDisable( GL_BLEND );
//		glPopMatrix();
	}

	@Override
	public void drawClosing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawLoading() {
		// TODO Auto-generated method stub
		try {
			TextureManager.getInstance().loadTexture( TextureNames.SHRINE );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_A );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_B );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_X );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_Y );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_RIGHT );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_LEFT );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_UP );
			TextureManager.getInstance().loadTexture( TextureNames.CTL_DOWN );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDirections(float lateral, float vertical, int action) {
		// TODO Auto-generated method stub
		
	}
	
	private GuitarHero getGuitarHero(){
		
		if ( gh == null ){
			gh = new GuitarHero( this, true, 40.0f, -2.0f, -0.2f, 10.0f, 0.2f );
		}
		
		return gh;
	}

	@Override
	public List<SEQUENCE_KEY> getSequence() {
		return Arrays.asList( SEQUENCE_KEY.TOWARD, SEQUENCE_KEY.UP, SEQUENCE_KEY.DOWN, SEQUENCE_KEY.AWAY, SEQUENCE_KEY.UP, 
			SEQUENCE_KEY.DOWN, SEQUENCE_KEY.RANDOM, SEQUENCE_KEY.RANDOM );
	}

}
