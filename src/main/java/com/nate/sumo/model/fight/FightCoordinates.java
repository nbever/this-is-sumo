package com.nate.sumo.model.fight;

public class FightCoordinates {

	private Float x = 0.0f;
	private Float y = 0.0f;
	private Float facing = 0.0f;
	
	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Float getFacing() {
		return facing;
	}
	public void setFacing(Float facing) {
		this.facing = facing;
	}
	
	public void setEastPreFight(){
		setX( -0.8f );
		setY( -0.3f );
	}
	
	public void setWestPreFight(){
		setX( 0.8f );
		setY( -0.3f );
	}
	
	public void setEastTachiai(){
		
	}
	
	public void setWestTachai(){
		
	}
	
}
