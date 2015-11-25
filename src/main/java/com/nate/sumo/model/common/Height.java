package com.nate.sumo.model.common;

public class Height {

	private Integer centimeter_value;
	
	public Height(){}
	
	public Height( Integer a_centimeter_value ){
		this.centimeter_value = a_centimeter_value;
	}
	
	public Integer getValue(){
		return this.centimeter_value;
	}
	
	@Override
	public String toString() {
	
		return getValue() + "cm";
	}
}
