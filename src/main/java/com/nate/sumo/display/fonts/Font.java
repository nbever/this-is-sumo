package com.nate.sumo.display.fonts;

public enum Font
{
	ZENZAI( new GlFont(  "zenzai_itacha/Zenzai_Itachi.ttf", 128 )),
	MADE_IN_CHINA( new GlFont( "made-in-china/MadeInChina.ttf", 24)),
	JAPANESE_CALI( new GlFont( "japanese_cali/japanese_cali.ttf", 48));
	
	private GlFont realFont;
	
	private Font( GlFont glFont ){
		realFont = glFont;
	}
	
	public void drawString( String text ){
		if ( realFont != null ){
			realFont.drawString( text.toCharArray() );
		}
	}
	
	public void drawString( char[] text ){
		if ( realFont != null ){
			realFont.drawString(text);
		}
	}
	
	public void drawJapaneseString( char[] text ){
		if ( realFont != null ){
			realFont.drawJapaneseString( text );
		}
	}
	
	public void drawJapaneseString( String text ){
		if ( realFont != null ){
			realFont.drawJapaneseString( text.toCharArray() );
		}
	}
}
