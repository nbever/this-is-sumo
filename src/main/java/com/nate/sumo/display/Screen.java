package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.model.Vector3f;
import com.nate.sumo.display.screens.ScreenInitData;
import com.nate.sumo.display.widgets.Widget;

public abstract class Screen implements Drawable, KeyHandler
{
	private Map<String, Integer> textures;
	private Map<String, Object> initData;
	protected List<String> textureNames;
	
	private List<Widget> registeredWidgets;
	private Widget p1SelectedWidget;
	private Widget p2SelectedWidget;
	
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
		
		getTextures().keySet().stream().forEach( resource -> {
			TextureManager.getInstance().releaseTexture( resource );
		});
		
		textures = null;
	}
	
	protected void registerWidget( Widget widget ){
		getRegisteredWidgets().add( widget );
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
	
	protected void selectNextWidget( boolean isp1, boolean forward ){
		
		int swIndex = -1;
		
		if ( isp1 ){
			swIndex = getRegisteredWidgets().indexOf( getPlayer1SelectedWidget() );
		}
		else {
			swIndex = getRegisteredWidgets().indexOf( getPlayer2SelectedWidget() );
		}
		
		if ( swIndex == -1 && getRegisteredWidgets().size() > 0 ){
			swIndex = 0;
		}
		else {
			
			if ( forward ){
				swIndex++;
			}
			else {
				swIndex--;
			}
			
			if ( swIndex >= getRegisteredWidgets().size() ){
				swIndex = 0;
			}
			
			if ( swIndex < 0 ){
				swIndex = getRegisteredWidgets().size() - 1;
			}
		}
		
		selectWidget( getRegisteredWidgets().get( swIndex ), isp1 );
	}
	
	protected void selectWidget( Widget widget, boolean isp1 ){
		
		widget.setSelected( true );
		
		if ( isp1 ){
			p1SelectedWidget = widget;
		}
		else {
			p2SelectedWidget = widget;
		}
		
		getRegisteredWidgets().stream().filter( w -> {
			if ( w.equals( getPlayer1SelectedWidget() ) ||
				w.equals( getPlayer2SelectedWidget() ) ){
				return false;
			}
			
			return true;
		}).forEach( w -> {
			w.setSelected( false );
		});
	}
	
	protected Widget getPlayer1SelectedWidget(){
		return p1SelectedWidget;
	}
	
	protected Widget getPlayer2SelectedWidget(){
		return p2SelectedWidget;
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
	
	protected List<Widget> getRegisteredWidgets(){
		if ( registeredWidgets == null ){
			registeredWidgets = new ArrayList<Widget>();
		}
		
		return registeredWidgets;
	}

}
