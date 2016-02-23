package com.nate.sumo.model.animation;

import com.nate.model.Vector3f;

public class Animation {

	private Vector3f startingValue;
	private Vector3f endingValue;
	private int frames;
	private int onFrame;
	private Vector3f value;
	
	private boolean running = false;
	private boolean stopped = true;
	
	public Animation( Vector3f aStartingValue, Vector3f anEndingValue, int numFrames ){
		this.startingValue = aStartingValue;
		this.endingValue = anEndingValue;
		this.value = this.startingValue;
		this.frames = numFrames;
		
		this.onFrame = 0;
	}
	
	public void advance(){
		
		if ( getOnFrame() > getFrames() ){
			stop();
			return;
		}
		
		if ( !running ){
			return;
		}
		
		float x = interpolateCoord( getStartingValue().getX(), getEndingValue().getX(), getOnFrame() );
		float y = interpolateCoord( getStartingValue().getY(), getEndingValue().getY(), getOnFrame() );
		float z = interpolateCoord( getStartingValue().getZ(), getEndingValue().getZ(), getOnFrame() );

		value = new Vector3f( x, y, z );
		
		onFrame++;
	}
	
	private float interpolateCoord( float start, float end, int onFrame ){
		
		float interval = (end - start) / (float)getFrames();
		
		return start + (interval * (float)onFrame);
	}
	
	public void start(){
		running = true;
		stopped = false;
	}
	
	public void stop(){
		running = false;
		stopped = true;
	}
	
	public void pause(){
		running = false;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public boolean hasStopped(){
		return stopped;
	}

	public Vector3f getStartingValue() {
		return startingValue;
	}

	public Vector3f getEndingValue() {
		return endingValue;
	}

	public int getFrames() {
		return frames;
	}
	
	public int getOnFrame(){
		return onFrame;
	}
	
	public Vector3f getValue(){
		return value;
	}
	
}
