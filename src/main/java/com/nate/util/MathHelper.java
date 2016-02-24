package com.nate.util;

public class MathHelper {

	public static Boolean equalsf( float f1, float f2 ){
		
		return Math.abs( f1 - f2 ) < 0.000001;
	}
	
}
