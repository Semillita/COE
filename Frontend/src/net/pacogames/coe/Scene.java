package net.pacogames.coe;

import com.badlogic.gdx.graphics.g2d.Batch;

/**Interface for the application to interact with all types of scenes*/
public interface Scene {

	/**Called to render the scene*/
	public void render();
	
	/**Called to resize the scene when the window is resized*/
	public void resize(int width, int height);
	
}