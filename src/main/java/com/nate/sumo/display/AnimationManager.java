package com.nate.sumo.display;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.nate.model.MD5Animation;

public class AnimationManager {

	public static final String DEFAULT_FOLDER = "default_actions";
	
	private static AnimationManager instance;
	
	private Map<String, MD5Animation> animMap;
	
	private AnimationManager(){}
	
	public static AnimationManager getInstance(){
		
		if ( instance == null ){
			instance = new AnimationManager();
		}
		
		return instance;
	}
	
	public MD5Animation loadAnimation( String key ){
		
		MD5Animation anim = getAnimationMap().get( key );
		
		if ( anim == null ){
			URL url = ModelManager.class.getResource( "/" + key );
			String filePath = url.getFile();
			
			try {
				anim = MD5Animation.loadAnimation( filePath );
				getAnimationMap().put( key, anim );
			} catch (Exception e) {
				System.out.println( "Could not locate animation " + key );
			}
		}
		
		return anim;
	}
	
	public void unloadModel( String key ){
		
		MD5Animation anim = loadAnimation( key );
		getAnimationMap().remove( key );
		
		anim = null;
	}
	
	private Map<String, MD5Animation> getAnimationMap(){
		
		if ( animMap == null ){
			animMap = new HashMap<String, MD5Animation>();
		}
		
		return animMap;
	}
}
