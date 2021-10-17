package net.pacogames.coe;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Scene {

	public void render(Batch batch, double deltaTime);
	
	public void resize(int width, int height);
	
}
