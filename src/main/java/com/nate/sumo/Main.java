package com.nate.sumo;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.macosx.Unistd.getpid;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

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

	// We need to strongly reference callback instances.
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	float WIDTH = 1200;
	float HEIGHT = 900;
	int backgroundTexture;

	// The window handle
	private long window;
	
	public void run() {
		
		System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
//		System.out.println( getpid() );
//		System.out.println("Running on thread: " + (System.getenv().get("JAVA_STARTED_ON_FIRST_THREAD_" + getpid())));
		
		try {

			init();

			DatabaseManager.getInstance().getCurrentBanzuke();
			
			loop();

			// Release window and window callbacks
			glfwDestroyWindow(window);
			keyCallback.release();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Terminate GLFW and release the GLFWerrorfun
			glfwTerminate();
			errorCallback.release();
		}
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		// 	Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( glfwInit() != GL11.GL_TRUE )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow((int)WIDTH, (int)HEIGHT, "Hello World!", NULL, NULL);
		
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {

				if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
					glfwSetWindowShouldClose(window, GL_TRUE); 
					// We will detect this in our rendering loop
				}
				else {
					KeyMapper.mapKey( key, scancode, action, mods );
//					ScreenManager.getInstance().handleKey( key, scancode, action, mods );
				}
			}
		});

		// Get the resolution of the primary monitor
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		// Center our window
		glfwSetWindowPos(
				window,
				(int)((GLFWvidmode.width(vidmode) - WIDTH) / 2.0f),
				(int)((GLFWvidmode.height(vidmode) - HEIGHT) / 2.0f)
				);

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the ContextCapabilities instance and makes the OpenGL
		// bindings available for use.
		//GL.createCapabilities( true );
		// valid for latest build
		GLContext.createFromCurrent(); // use this line instead with the 3.0.0a build
		
		ScreenManager.getInstance();
		
		glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
		int frameRate = 65;
		double lastSample = 0.0;

		glTranslatef( 0.0f, 0.0f, -1.0f );
		
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( glfwWindowShouldClose(window) == GL_FALSE ) {

			
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			
			double now = getTime();
			
			if ( now - lastSample >= ( 1.0/(double)frameRate ) ){
				render();	
				lastSample = now;
			}
		}
	}
	
	private double getTime(){
		return glfwGetTime();
	}
	
	private void render(){
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
		
		glMatrixMode( GL_PROJECTION );
		glLoadIdentity();
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w, h);
		int width = w.get(0);
		int height = h.get(0);

		float aspect = width / height;
		
		glFrustum( -1.0f * aspect, aspect, -1.0f, 1.0f, 6.0, 100.0f );
//		glFrustum( -12.0f, 12.0f, -1.0f, 1.0f, 1.0, 10.0f );
		
		glMatrixMode( GL_MODELVIEW );
		glPolygonMode( GL_FRONT, GL11.GL_FILL );

			
//			glScalef( 0.5f, 0.5f, 0.5f );
		glPushMatrix();
	
			ScreenManager.getInstance().draw();
		glPopMatrix();
		
		glfwSwapBuffers(window);
	}

}
