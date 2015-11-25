package com.nate.sumo.display.fonts;

import java.net.URISyntaxException;

public class ZenzaiFont extends GlFont
{
	private static ZenzaiFont instance;
	
	private ZenzaiFont() throws URISyntaxException{
		super( "zenzai_itacha/Zenzai_Itachi.ttf" );
	}
	
	public static ZenzaiFont getInstance(){
		
		if ( instance == null ){
			try
			{
				instance = new ZenzaiFont();
			}
			catch (URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
}
