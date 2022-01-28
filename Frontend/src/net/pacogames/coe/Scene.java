package net.pacogames.coe;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Scene {

	public void render();
	
	public void resize(int width, int height);
	
}