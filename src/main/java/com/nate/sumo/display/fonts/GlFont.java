package com.nate.sumo.display.fonts;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.newdawn.slick.util.ResourceLoader;

public class GlFont
{
	private static final int BITMAP_W = 1024;
	private static final int BITMAP_H = 1024;
	
	private ByteBuffer chardata;
	private Integer fontTex;

	private int fontHeight = 24;
	
	private static final float[] scale = {
		24.0f,
		14.0f
	};

	private static final int[] sf = {
		0, 1, 2,
		0, 1, 2
	};
	
	protected GlFont( String resource, int fontSize ){
		
		fontHeight = fontSize;
		
		try {
			loadFont( resource );
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	private void loadFont( String resource ) throws URISyntaxException{
		
		fontTex = glGenTextures();
		
		try {
			ByteBuffer ttf;
			URL fileUrl = ResourceLoader.getResource( "fonts/" + resource );
			File file = new File( fileUrl.toURI() );
			
			if ( !file.exists() ){
				throw new FileNotFoundException();
			}
			
			FileInputStream fileIn = new FileInputStream( file );
			FileChannel fch = fileIn.getChannel();
			
			ttf = BufferUtils.createByteBuffer((int)fch.size() + 1);

			while ( fch.read( ttf) != -1 ) ;
			
			fileIn.close();
			fch.close();
			
			ttf.flip();
			
			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
			chardata = BufferUtils.createByteBuffer( bitmap.capacity() );
			stbtt_BakeFontBitmap(ttf, fontHeight, bitmap, BITMAP_W, BITMAP_H, 32, chardata);
			
			glBindTexture(GL_TEXTURE_2D, fontTex);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void releaseFont(){
		glDeleteTextures( fontTex );
	}
	
	public int getFontTexId(){
		return fontTex;
	}
	
	
	public void drawString( char[] text ){
		
		glPushMatrix();
		// standard scale factor
		glScalef( 0.002f, 0.002f, 0.002f );
		
		FloatBuffer xb = BufferUtils.createFloatBuffer(1);
		FloatBuffer yb = BufferUtils.createFloatBuffer(1);
		
		xb.put( 0, 0.0f );
		yb.put( 0, 0.0f );
		
		ByteBuffer q  = STBTTAlignedQuad.malloc();
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, fontTex);

		glBegin(GL_QUADS);
		for ( int i = 0; i < text.length; i++ ) {
			
			char c = text[i];
			if ( c == '\n' ) {
				yb.put(0, yb.get(0) + fontHeight);
				xb.put(0, 0.0f);
				continue;
			} else if ( c < 32 )// || 128 <= c )
				continue;

			
			stbtt_GetBakedQuad(chardata, BITMAP_W, BITMAP_H, c-32, xb, yb, q, 1 );
			
			drawBoxTC(
				STBTTAlignedQuad.x0( q ),STBTTAlignedQuad.y0( q ), STBTTAlignedQuad.x1(q ), STBTTAlignedQuad.y1( q ),
				STBTTAlignedQuad.s0( q ), STBTTAlignedQuad.t0( q ), STBTTAlignedQuad.s1( q ), STBTTAlignedQuad.t1( q )
			);
		}
		glEnd();
		glPopMatrix();
	}
	
	private void drawBoxTC( float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1 ){
		
		glTexCoord2f(s0, t0);
		glVertex2f(x0, -1.0f*y0);
		glTexCoord2f(s1, t0);
		glVertex2f(x1, -1.0f*y0);
		glTexCoord2f(s1, t1);
		glVertex2f(x1, -1.0f*y1);
		glTexCoord2f(s0, t1);
		glVertex2f(x0, -1.0f*y1);
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		releaseFont();
		super.finalize();
	}
}
