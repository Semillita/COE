package net.pacogames.coe.game;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.Scene;
import net.pacogames.coe.logic.game.input.InputEvent;
import net.pacogames.coe.logic.game.input.Key;
import net.pacogames.coe.logic.game.runtime.GameLogic;
import net.pacogames.coe.logic.game.runtime.GameLogicImpl;

public class GameScene implements Scene {

	private final int[] keys1 = {
			Keys.W, Keys.D, Keys.S, Keys.A
	};
	
	private final int[] keys2 = {
			Keys.UP, Keys.RIGHT, Keys.DOWN, Keys.LEFT
	};
	
	private GameLogic logic;
	private GameGraphics graphics;
	
	public GameScene() {
		logic = new GameLogicImpl();
		graphics = new GameGraphicsImpl();

		setInputListener();
		logic.startGame();
	}

	@Override
	public void render(Batch batch) {
		logic.loadAdvanceFrames();
		var currentFrame = logic.getCurrentFrame();
		
		var timeElapsed = ((GameLogicImpl) logic).gameTimer.getTimeElapsed(System.nanoTime());
		
		graphics.renderFrame(batch, currentFrame);
	}

	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);
	}

	private void setInputListener() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				 registerInput(keycode, true);
				return false;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				registerInput(keycode, false);
				return false;
			}
		});
	}
	
	private void registerInput(int keycode, boolean pressed) {
		for(int playerID = 1; playerID <= 2; playerID++) {
			var playerKeys = (playerID == 1) ? keys1 : keys2;
			for(int index = 0; index < 4; index++) {
				if(keycode == playerKeys[index]) {
					var key = Key.values()[index];
					logic.registerInput(playerID, new InputEvent(key, pressed), System.nanoTime());
				}
			}
		}
	}
}