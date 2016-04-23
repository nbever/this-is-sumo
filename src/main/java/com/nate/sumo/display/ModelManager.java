package com.nate.sumo.display;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.nate.model.MD5Mesh;
import com.nate.model.MD5Model;

public class ModelManager {

	private static ModelManager instance;
	
	private Map<String, MD5Model> modelMap;
	
	private ModelManager(){}
	
	public static ModelManager getInstance(){
		if ( instance == null ){
			instance = new ModelManager();
		}
		
		return instance;
	}
	
	public MD5Model loadModel( String key ){
		
		MD5Model model = getModelMap().get( key );
		
		if ( model == null ){
			URL url = ModelManager.class.getResource( key );
			String filePath = url.getFile();
			
			try {
				model = MD5Model.loadModel( filePath );
				getModelMap().put( key, model );
			} catch (Exception e) {
				System.out.println( "Could not locate model " + key );
			}
		}
		
		return model;
	}
	
	public void unloadModel( String key ){
		
		MD5Model model = loadModel( key );
		getModelMap().remove( key );
		
		MD5Mesh[] meshes = model.getMeshes();
		
		for( MD5Mesh mesh : meshes ){
			mesh.getIndexArray().clear();
			mesh.getVertexArray().clear();
			mesh.getTexelArray().clear();
		}
		
		meshes = null;
		model = null;
	}
	
	private Map<String, MD5Model> getModelMap(){
		if ( modelMap == null ){
			modelMap = new HashMap<String, MD5Model>();
		}
		return modelMap;
	}
}
