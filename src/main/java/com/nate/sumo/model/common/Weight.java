package com.nate.sumo.model.common;

public class Weight {

	private Integer kg_value;
	
	public Weight( Integer a_kg_value ){
		this.kg_value = a_kg_value;
	}
	
	public Integer getValue(){
		return this.kg_value;
	}
	
	@Override
	public String toString() {
	
		return getValue() + "kg";
	}
	
}
