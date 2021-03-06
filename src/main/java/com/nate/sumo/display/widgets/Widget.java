package com.nate.sumo.display.widgets;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nate.sumo.display.Drawable;
import com.nate.sumo.display.KeyHandler;
import com.nate.sumo.display.TextureManager;

public abstract class Widget implements Drawable, KeyHandler
{

	private boolean selected = false;
	private static final Logger logger = LogManager.getLogger();
	protected List<String> textureNames;
	
	public Widget(){
		
		List<String> tNames = getTextureNames();
		
		if ( tNames != null ){
			tNames.stream().forEach( tn -> {
				
				try
				{
					TextureManager.getInstance().loadTexture( tn );
				}
				catch (Exception e)
				{
					logger.error( "Could not load the widget texture: " + tn );
				}
			});
		}
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected( boolean selected ){
		this.selected = selected;
	}
	
	public void cleanUp(){
		
		getTextureNames().stream().forEach( tn -> {
			TextureManager.getInstance().releaseTexture( tn );
		});
	}
	
	public abstract List<String> getTextureNames();
	
}
