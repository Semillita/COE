package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.Scene;
import net.pacogames.coe.logic.game.GameLogic;
import net.pacogames.coe.logic.game.GameLogicImpl;

public class GameScene implements Scene {

	private GameLogic logic;
	private GameGraphics graphics;
	
	public GameScene() {
		logic = new GameLogicImpl();
		graphics = new GameGraphicsImpl();
		
		logic.startGame();
	}

	@Override
	public void render(Batch batch) {
		graphics.renderFrame(batch, logic.getCurrentFrame());
	}

	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);
	}
	
}