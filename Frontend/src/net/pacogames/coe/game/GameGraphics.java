package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.logic.game.runtime.Frame;

public interface GameGraphics {

	public void renderFrame(Batch batch, Frame frame);
	
	public void resize(int viewportWidth, int viewportHeight);
	
}