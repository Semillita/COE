package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.logic.game.runtime.Frame;

/**Interface for the {@link GameScene} to interact with the graphics part*/
public interface GameGraphics {

	/**Renders a frame*/
	public void renderFrame(Frame frame);
	
	/**Updates the scene's layout*/
	public void resize(int viewportWidth, int viewportHeight);
	
}