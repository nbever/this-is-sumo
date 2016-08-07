package com.nate.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMathHelper {

	@Test
	public void testRegTan() {
		
		Double rez = MathHelper.atan_full( 0.0, 0.0 );
		assertTrue( "Expected 0.0 but got: " + rez, rez == 0.0 );
		
		rez = MathHelper.atan_full( -300.0, 0.0 );
		assertTrue( "Expected " + Math.PI + " but got: " + rez, rez == Math.PI );
		
		rez = MathHelper.atan_full( 0.0, 300.0 );
		assertTrue( "Expected " + Math.PI/2.0 + " but got: " + rez, rez == Math.PI/2.0 );
		
		rez = MathHelper.atan_full( 10.0, 10.0 );
		assertTrue( "Expected " + Math.PI/4.0 + " but got: " + rez, rez == Math.PI/4.0 );
		
		rez = MathHelper.atan_full( 20.0, -20.0 );
		assertTrue( "Expected " + (7.0*Math.PI)/4.0 + " but got: " + rez, rez == (7.0*Math.PI)/4.0 );
		
		rez = MathHelper.atan_full( -150.0, -150.0 );
		assertTrue( "Expected " + (5.0*Math.PI)/4.0 + " but got: " + rez, rez == (5.0*Math.PI)/4.0 );
		
		rez = MathHelper.atan_full( -5.0, 5.0 );
		assertTrue( "Expected " + (3.0*Math.PI)/4.0 + " but got: " + rez, rez == (3.0*Math.PI)/4.0 );
		
	}

}
