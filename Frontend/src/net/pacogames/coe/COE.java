package net.pacogames.coe;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.pacogames.coe.logic.utils.Logger;

/**Launcher class*/
public class COE {

	private static Application app;

	/**Main method to start the application*/
	public static void main(String[] args) {
		Logger.flag = true;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		config.fullscreen = true;
		config.title = "Call of Elysium";
		
		app = new Application();
		new LwjglApplication(app, config);
	}
	
	/**Returns the {@link Application} instance
	 * 
	 * Used by different parts of the code to access the application instance which in turn owns most instances of different components, so that the 
	 * Application instance doesn't have to be passed down by all components to their child components.
	 * 
	 * @return the {@link Application}*/
	public static Application getApp() {
		return app;
	}
}