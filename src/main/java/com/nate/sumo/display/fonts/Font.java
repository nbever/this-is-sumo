package com.nate.sumo.display.fonts;

public enum Font
{
	ZENZAI( new GlFont(  "zenzai_itacha/Zenzai_Itachi.ttf" ));
	
	private GlFont realFont;
	
	private Font( GlFont glFont ){
		realFont = glFont;
	}
	
	public void drawString( String text ){
		if ( realFont != null ){
			realFont.drawString( text );
		}
	}
}
