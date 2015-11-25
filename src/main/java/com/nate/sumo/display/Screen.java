package com.nate.sumo.display;

public abstract class Screen implements Drawable, KeyHandler
{
	private Screen lastScreen;
	private boolean closing = false;
	private boolean loading = true;
	
	public abstract void drawScreen();
	public abstract void drawClosing();
	public abstract void drawLoading();
	public abstract void handleKey( int key, int scanCode, int action, int mods );
	
	public Screen( Screen lastScreen ){
		this.lastScreen = lastScreen;
	}
	
	public Screen(){
		
	}
	
	public void draw(){
		
		if ( !isClosing() && !isLoading() ){
			drawScreen();
		}
		else if ( isClosing() ){
			drawClosing();
		}
		else if ( isLoading() ){
			drawLoading();
		}
	}
	
	public void loadNextScreen( Screen nextScreen ){
		
	}
	
	public boolean isLoading(){
		return loading;
	}
	
	protected void closeComplete(){
		closing = false;
	}
	
	protected void loadComplete(){
		loading = false;
	}
	
	public boolean isClosing(){
		return closing;
	}
	
	private Screen getLastScreen(){
		return lastScreen;
	}
}
