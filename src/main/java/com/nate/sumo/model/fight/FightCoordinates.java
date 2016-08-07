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
		setX( -5.0f );
		setY( 6.0f );
		setFacing( 270.0f );
	}
	
	public void setWestPreFight(){
		setX( 5.0f );
		setY( 6.0f );
		setFacing( 270.0f );
	}
	
	public void setEastTachiai(){
		
	}
	
	public void setWestTachai(){
		
	}
	
}
