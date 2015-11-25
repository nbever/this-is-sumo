package com.nate.sumo.display;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.*;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class TextureManager
{
	private static TextureManager textureManager;
	private List<Integer> textures;
	
	private TextureManager(){
		
	}
	
	public static TextureManager getInstance(){
		if ( textureManager == null ){
			textureManager = new TextureManager();
		}
		
		return textureManager;
	}
	
	public Integer loadTexture( String resourceName ) throws IOException, URISyntaxException{
		
		ByteBuffer byteBuffer;
		URL fileUrl = ResourceLoader.getResource( "images/" + resourceName );
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
		
		getTextureIds().add( texId );
		
		return texId;
	}
	
	public void releaseTexture( Integer textureId ){
		
		glDeleteTextures( textureId );
		getTextureIds().remove( textureId );
	}
	
	private List<Integer> getTextureIds(){
		if ( textures == null ){
			textures = new ArrayList<Integer>();
		}
		
		return textures;
	}
}
