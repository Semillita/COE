package net.pacogames.coe;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class COE {

	private static Application app;

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		config.fullscreen = true;
		config.title = "Call of Elysium";

		app = new Application();
		new LwjglApplication(app, config);
	}
}