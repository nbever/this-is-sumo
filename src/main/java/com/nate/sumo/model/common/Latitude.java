package com.nate.sumo.model.common;

public class Latitude {

	private Double degrees;
	
	public Latitude( Double degress ){
		this.degrees = degress;
	}
	
	public Double getDegrees(){
		return this.degrees;
	}
	
	@Override
	public String toString() {
	
		return getDegrees() + " ";
	}
}
