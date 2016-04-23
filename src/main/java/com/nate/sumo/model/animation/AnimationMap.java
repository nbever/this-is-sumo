package com.nate.sumo.model.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimationMap {

	private static Logger logger = LogManager.getLogger();
	private Map<String, List<String>> rawMap;
	
	public AnimationMap(){}
	
	private AnimationMap( Map<String, List<String>> theMap ){
		rawMap = theMap;
	}
	
	public void addAnimation( String key, String file ){
		
		List<String> animations = getRawMap().get( key );
		
		if ( animations == null ){
			animations = new ArrayList<String>();
		}
		
		if ( !animations.contains( file ) ){
			animations.add( file );
		}
	}
	
	public String getAttackAnimation( String key ){
		
		return getAnimation( Attacks.class.getCanonicalName() + "." + key, key );
	}
	
	public String getReactionAnimation( String key ){
			
		return getAnimation( Reactions.class.getCanonicalName() + "." + key, key );
		
	}
	
	public String getTachiAiAnimation( String key ){
		
		return getAnimation( TachiAis.class.getCanonicalName() + "." + key, key );
		
	}
	
	public String getTachiAiReactionAnimation( String key ){
		
		return getAnimation( TachiAiReactions.class.getCanonicalName() + "." + key, key );
		
	}
	
	private String getAnimation( String className, String key ){
		
		List<String> animations = getRawMap().get( key );
		
		if ( animations == null || animations.isEmpty() ){
		
			try {
				
				Class<Enum> clz = (Class<Enum>) Class.forName( className );
				AnimEnumIf enumIf = (AnimEnumIf) Enum.valueOf( clz, "DEFAULT" );
				return enumIf.getFilename();
				
			} catch (ClassNotFoundException e) {

				logger.error( "Problem trying to gather the default animation for: " + key, e );
			}
			
		}
		
		int choice = (int)(Math.random() * (animations.size() - 1));
		return animations.get(  choice );
	}
	
	private Map<String, List<String>> getRawMap(){
		if ( rawMap == null ){
			rawMap = new HashMap<String, List<String>>();
		}
		
		return rawMap;
	}
	
	public String toString(){
		
		String str = "";
		Iterator<String> keys = getRawMap().keySet().iterator();
		
		while( keys.hasNext() ){
			
			String key = keys.next();
			List<String> files = getRawMap().get( key );
			
			str += key + ":";
			
			for ( String file : files ){
				key += file + ",";
			}
			
			str += "-";
		}
		
		return str;
	}
	
	public static AnimationMap loadString( String str ){
		
		String[] keys = str.split( "-" );
		AnimationMap map = new AnimationMap();
		
		for ( String key : keys ){
			String[] parts = key.split( ":" );
			String[] files = parts[1].split( "," );
			
			for ( String file : files ){
				map.addAnimation( parts[0], file );
			}
		}
		
		return map;
	}
}
