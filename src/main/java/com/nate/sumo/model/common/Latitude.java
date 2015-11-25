package com.nate.sumo.model.common;

public class Latitude {

	public enum DIRECTION{NORTH, SOUTH};
	
	private Double degrees;
	private DIRECTION cardinality;
	
	public Latitude( Double degress, DIRECTION cardinality ){
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
