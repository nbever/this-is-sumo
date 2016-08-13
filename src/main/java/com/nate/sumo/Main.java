package com.nate.sumo;

import org.lwjgl.Sys;

import com.nate.sumo.display.ScreenManager;
import com.nate.util.SharedLibraryLoader;

public class Main
{

	public static void main( String[] args )
	{
		SharedLibraryLoader.load();

		Main main = new Main();
		main.run();
	}
	
	public void run() {
		
		System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

		DatabaseManager.getInstance().getCurrentBanzuke();
		
		DisplayRunner dpr = new DisplayRunner( new ScreenManager() );
		dpr.startLoop();

	}

}
