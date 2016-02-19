package com.nate.sumo.display;

import org.junit.Test;

public class ScreenHelperTest {

	@Test
	public void testJapaneseNumberConversion(){
		
		int test = 23;
		String rez = ScreenHelper.getInstance().getKanjiForNumber( test );
		
		assert( rez == "二十三" );
		
		test = 5;
		rez = ScreenHelper.getInstance().getKanjiForNumber( test );
		
		assert( rez == "五" );
	}
}
