package com.nate.sumo.model;

public abstract class Entity<T> {

	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId( Long id ){
		this.id = id;
	}
}
