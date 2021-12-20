package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.Scene;
import net.pacogames.coe.logic.game.GameLogic;

public class GameScene implements Scene {

	private GameLogic logic;
	private GameGraphics graphics;
	
	public GameScene() {
		//logic.startGame();
	}

	@Override
	public void render(Batch batch, double deltaTime) {
		//currentFrame = logic.getCurrentFrame()
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
}