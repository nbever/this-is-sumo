package com.nate.sumo.model.common;

public class Longitude {

	public enum DIRECTION{EAST, WEST};
	
	private Double degrees;
	private DIRECTION cardinality;
	
	public Longitude( Double degress, DIRECTION cardinality ){
		this.degrees = degress;
		this.cardinality = cardinality;
	}
	
	public Double getDegrees(){
		return this.degrees;
	}
	
	public DIRECTION getCardinality(){
		return this.cardinality;
	}
	
	@Override
	public String toString() {
	
		return getDegrees() + " " + getCardinality().name().charAt( 0 );
	}
}
