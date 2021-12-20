package net.pacogames.coe.game;

import net.pacogames.coe.logic.game.Frame;

public interface GameGraphics {

	public void renderFrame(Frame frame);
	
	public void resize(int viewportWidth, int viewportHeight);
	
}