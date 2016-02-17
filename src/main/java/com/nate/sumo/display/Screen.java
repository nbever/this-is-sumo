package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.model.Vector3f;
import com.nate.sumo.display.screens.ScreenInitData;

public abstract class Screen implements Drawable, KeyHandler
{
	private Map<String, Integer> textures;
	private Map<String, Object> initData;
	protected List<String> textureNames;
	
	private boolean closing = false;
	private boolean loading = true;
	private boolean live = true;
	
	public abstract List<String> getTextureNames();
	public abstract void drawScreen();
	public abstract void drawClosing();
	public abstract void drawLoading();
	public abstract void cleanup();
	public abstract ScreenInitData getNextScreenData();
	
	public abstract void handleKey( int key, int scanCode, int action, int mods );
	
	public Screen( Map<String, Object> initData ){
		
		this.initData = initData; 
		
		if ( getTextureNames() != null ){
			for ( String resource : getTextureNames() ){
				
				try
				{
					Integer texId = TextureManager.getInstance().loadTexture( resource );
					getTextures().put( resource, texId );
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (URISyntaxException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void unloadTextures(){
		
		getTextures().values().stream().forEach( ( texId ) -> {
			glDeleteTextures( texId );
		});
	}
	
	public void draw(){
		
		// means we're done
		if ( isDone() ){
			unloadTextures();
			cleanup();
			return;
		}
		
		if ( !isClosing() && !isLoading() ){
			drawScreen();
		}
		else if ( isClosing() ){
			live = false;
			drawClosing();
		}
		else if ( isLoading() ){
			drawLoading();
		}
	}
	
	public boolean isLoading(){
		return loading;
	}
	
	protected void closeComplete(){
		closing = false;
	}
	
	protected void loadComplete(){
		loading = false;
	}
	
	public boolean isClosing(){
		return closing;
	}
	
	public void close(){
		closing = true;
	}
	
	public boolean isDone(){
		return ( !isClosing() && !live );
	}
	
	public Map<String, Object> getInitData(){
		return initData;
	}
	
	protected Map<String, Integer> getTextures(){
		if ( textures == null ){
			textures = new HashMap<String, Integer>();
		}
		
		return textures;
	}

}
