package com.nate.sumo.display.fonts;

import org.junit.Test;

public class JapaneseFontTest
{

	@Test
	public void findCharacters(){
		
		char[] chars = JapaneseFontTranslator.convert( "幕下" );
		
		for ( char ch : chars ){
			System.out.println( (int)ch );
		}
	}
}
