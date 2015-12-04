package com.nate.sumo.model.common;

public class Longitude {

	private Double degrees;
	
	public Longitude( Double degress ){
		this.degrees = degress;
	}
	
	public Double getDegrees(){
		return this.degrees;
	}
	
	@Override
	public String toString() {
		
		return getDegrees() + "";
	}
}
