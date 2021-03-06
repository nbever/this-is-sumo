package com.nate.sumo.display;

public interface KeyHandler
{
	public abstract void handleKey( int key, int scanCode, int action, int mods );
	public abstract void handleDirections( float lateral, float vertical, int action );
}
