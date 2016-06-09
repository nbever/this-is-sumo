package com.nate.sumo.model.fight;

public class Pathway {

	private float xDest;
	private float yDest;
	private float moveFacing;
	private float arrivalFacing;
	
	public Pathway( float xDest, float yDest, float moveFacing, float arrivalFacing ){
		this.xDest = xDest;
		this.yDest = yDest;
		this.moveFacing = moveFacing;
		this.arrivalFacing = arrivalFacing;
	}

	public float getxDest() {
		return xDest;
	}

	public float getyDest() {
		return yDest;
	}

	public float getMoveFacing() {
		return moveFacing;
	}

	public float getArrivalFacing() {
		return arrivalFacing;
	}
}
