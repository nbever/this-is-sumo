package com.nate.sumo.display;

import static org.lwjgl.opengl.GL11.*;

import com.jme3.math.Vector4f;

public class ScreenHelper
{

	public static final float SCREEN_DEPTH = -6.01f;
	public static final float DIALOG_DEPTH = -6.009f;
	
	private static ScreenHelper instance;
	
	private ScreenHelper(){
		
	}
	
	public static ScreenHelper getInstance(){
		if ( instance == null ){
			instance = new ScreenHelper();
		}
		
		return instance;
	}
	
	public void drawSquare( float width, float height, boolean texture ){
		this.drawSquare( width, height, texture, true, 0.0f );
	}
	
	public void drawSquare( float width, float height, boolean texture, boolean fill, float lineWidth ){
		
		if ( fill ){
			glBegin( GL_QUADS );
		}
		else {
			glLineWidth(  lineWidth );
			glBegin( GL_LINE_STRIP ); 
		}
	
		if ( texture )
			glTexCoord2f( 0.0f, 0.0f );
		glVertex3f( 0.0f, height, 0.0f );
		if ( texture )
			glTexCoord2f( 1.0f, 0.0f );
		glVertex3f( width, height, 0.0f );
		if ( texture )
			glTexCoord2f( 1.0f, 1.0f );
		glVertex3f( width, 0.0f, 0.0f );
		if ( texture )
			glTexCoord2f( 0.0f, 1.0f );
		glVertex3f( 0.0f, 0.0f, 0.0f );

		if ( !fill ){
			glVertex3f( 0.0f, height, 0.0f );
		}
		
		glEnd();
	}
	
	public void drawCircleBurst( float width, float height, Vector4f color ){
		
//		glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
//		drawSquare( width, height, false );
		
		glBegin( GL_QUADS );
		
		// upper left
		glColor4f( 0.0f, 0.0f, 0.0f, 0.0f );
		glVertex3f( 0.0f, height, 0.0f );
		glVertex3f( width/2.0f, height, 0.0f );
		glColor4f( color.getX(), color.getY(), color.getZ(), color.getW() );
		glVertex3f( width/2.0f, height/2.0f, 0.0f );
		glColor4f( 0.0f, 0.0f, 0.0f, 0.0f );
		glVertex3f( 0.0f, height/2.0f, 0.0f );
		
		//upper right
		glColor4f( 0.0f, 0.0f, 0.0f, 0.0f );
		glVertex3f( width/2.0f, height, 0.0f );
		glVertex3f( width, height, 0.0f );
		glVertex3f( width, height/2.0f, 0.0f );
		glColor4f( color.getX(), color.getY(), color.getZ(), color.getW() );
		glVertex3f( width/2.0f, height/2.0f, 0.0f );
		
		// lower left
		glColor4f( 0.0f, 0.0f, 0.0f, 0.0f );
		glVertex3f( 0.0f, height/2.0f, 0.0f );
		glColor4f( color.getX(), color.getY(), color.getZ(), color.getW() );
		glVertex3f( width/2.0f, height/2.0f, 0.0f );
		glColor4f( 0.0f, 0.0f, 0.0f, 0.0f );
		glVertex3f( width/2.0f, 0.0f, 0.0f );
		glVertex3f( 0.0f, 0.0f, 0.0f );
		
		// lowe	r right
		glColor4f( color.getX(), color.getY(), color.getZ(), color.getW() );
		glVertex3f( width/2.0f, height/2.0f, 0.0f );
		glColor4f( 0.0f, 0.0f, 0.0f, 0.0f );
		glVertex3f( width, height/2.0f, 0.0f );
		glVertex3f( width, 0.0f, 0.0f );
		glVertex3f( width/2.0f, 0.0f, 0.0f );
		
		glEnd();
	}
	
	public String getKanjiForNumber( int i ){
		
		int hundreds = i / 100;
		int tens = (i - (hundreds*100)) / 10;
		int singles = i - (hundreds*100) - (tens*10);
		
		String japaneseNumber = "";
		
		if ( hundreds > 1 ){
			if ( hundreds > 1 ){
				japaneseNumber += singleDigitConversion( hundreds ) ;
			}
			japaneseNumber += singleDigitConversion( 100 );
		}
		
		if ( tens > 0 ){
			if ( tens > 1 ){
				japaneseNumber += singleDigitConversion( tens );
			}
			
			japaneseNumber += singleDigitConversion( 10 );
		}
		
		if ( singles != 0 || (singles == 0 && tens == 0 && hundreds == 0 )){
			japaneseNumber += singleDigitConversion( singles );
		}
		
		return japaneseNumber;
	}
	
	private String singleDigitConversion( int digit ){
		
		switch( digit ){
		case 0:
			return "霊";
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";
		case 100:
			return "百";
		default:
			return "";
	}
	}
}
