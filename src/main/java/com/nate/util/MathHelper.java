package com.nate.util;

public class MathHelper {

	public static Boolean equals( Number f1, Number f2 ){
		
		return Math.abs( f1.doubleValue() - f2.doubleValue() ) < 0.000001;
	}
	
	public static Double atan_full( double x, double y ){
		
		Double reg_tan = Math.atan( y / x );

		if ( x < 0  ){
			reg_tan += Math.PI;
		}
		
		if ( reg_tan < 0 ){
			reg_tan += 2.0*Math.PI;
		}
		
		if ( Double.isNaN( reg_tan ) || Math.abs( reg_tan ) == 0.0 ){
			if ( y == 0.0 && x >= 0.0 ){
				reg_tan = 0.0;
			}
			else if ( y== 0.0 && x < 0.0 ){
				reg_tan = Math.PI;
			}
			else if ( x == 0.0 && y > 0.0 ){
				reg_tan = Math.PI / 2.0;
			}
			else if ( x == 0.0 && y < 0 ){
				reg_tan = (3.0*Math.PI)/2.0;
			}
		}
		
		return reg_tan;
	}
	
}
