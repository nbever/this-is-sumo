package com.nate.sumo.display;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TextureManager
{
	private static TextureManager textureManager;
	private Map<String, Integer> textures;
	
	private TextureManager(){
		
	}
	
	public static TextureManager getInstance(){
		if ( textureManager == null ){
			textureManager = new TextureManager();
		}
		
		return textureManager;
	}
	
	public Integer loadTexture( String resourceName ) throws IOException, URISyntaxException{
		
		// if we've already loaded it why do it again?
		Integer textureId = getTextureIds().get( resourceName );
		
		if ( textureId != null ){
			return textureId;
		}
		
		ByteBuffer byteBuffer;
		URL fileUrl = TextureManager.class.getResource( "/" + resourceName );
		
		File file = new File( fileUrl.toURI() );
		
		if ( !file.exists() ){
			throw new FileNotFoundException();
		}
		
		FileInputStream fileIn = new FileInputStream( file );
		FileChannel fch = fileIn.getChannel();
		
		byteBuffer = BufferUtils.createByteBuffer((int)fch.size() + 1);

		while ( fch.read( byteBuffer) != -1 ) ;
		
		fileIn.close();
		fch.close();
		
		byteBuffer.flip();
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		// Decode the image
		ByteBuffer image = stbi_load_from_memory(byteBuffer, w, h, comp, 0);
		
		if ( image == null )
			throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
		
		int texId = glGenTextures();
		glBindTexture( GL_TEXTURE_2D, texId );
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		if ( comp.get( 0 ) == 3 ){
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w.get( 0 ), h.get( 0 ), 0, GL_RGB, GL_UNSIGNED_BYTE, image );
		}
		else {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get( 0 ), h.get( 0 ), 0, GL_RGBA, GL_UNSIGNED_BYTE, image );
		}
		
		getTextureIds().put( resourceName, texId );
		
		stbi_image_free( image );
		
		return texId;
	}
	
	public void releaseTexture( Integer textureId ){

		Iterator<String> keyIt = getTextureIds().keySet().iterator();
		
		while( keyIt.hasNext() ){
			String key = keyIt.next();
			Integer anId = getTextureIds().get( key );
			
			if ( anId == textureId ){
				getTextureIds().remove( key );
				glDeleteTextures( textureId );
				break;
			}
		}
	}
	
	public void releaseTexture( String resourceName ){
		
		Integer textureId = getTextureIds().get( resourceName );
		
		if ( textureId != null ){
			glDeleteTextures( textureId );
			getTextureIds().remove( resourceName );
			return;
		}
	}
	
	private Map<String, Integer> getTextureIds(){
		if ( textures == null ){
			textures = new HashMap<String, Integer>();
		}
		
		return textures;
	}
	
	public Integer getTextureId( String resourceName ){
		
		Integer id = getTextureIds().get( resourceName );
		
		if ( id == null ){
			id = -1;
		}
		
		return id;
	}
}
