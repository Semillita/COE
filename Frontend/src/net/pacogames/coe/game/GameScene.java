package net.pacogames.coe.game;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.COE;
import net.pacogames.coe.Scene;
import net.pacogames.coe.logic.game.input.InputEvent;
import net.pacogames.coe.logic.game.input.Key;
import net.pacogames.coe.logic.game.runtime.GameLogic;
import net.pacogames.coe.logic.game.runtime.GameLogicImpl;

public class GameScene implements Scene {

	private final int[] keys1 = { Keys.W, Keys.D, Keys.S, Keys.A, Keys.SPACE }, keys2 = { Keys.UP, Keys.RIGHT, Keys.DOWN, Keys.LEFT, Keys.ENTER };
	private final Runnable onReturn;
	private final GameLogic logic;
	private final GameGraphics graphics;

	public GameScene(Runnable onReturn) {
		this.onReturn = onReturn;
		logic = new GameLogicImpl();
		graphics = new GameGraphicsImpl();

		logic.startGame();
		setInputListener();
	}

	/**Renders the game*/
	@Override
	public void render() {
		logic.loadAdvanceFrames();
		var currentFrame = logic.getCurrentFrame();
		
		var timeElapsed = ((GameLogicImpl) logic).gameTimer.getTimeElapsed(System.nanoTime());
		
		graphics.renderFrame(currentFrame);
		
		if (currentFrame.gameOver) {
			COE.getApp().endGame();
		}
	}

	/**Updates the layout of the game*/
	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);
	}

	/**Sets the input listener of the window*/
	private void setInputListener() {
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				registerKeyClick(keycode, true);
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				registerKeyClick(keycode, false);
				return false;
			}
		});
	}

	/**Registers a key press event in a player's input queue*/
	private void registerKeyClick(int keycode, boolean pressed) {
		for (int playerID = 1; playerID <= 2; playerID++) {
			var playerKeys = (playerID == 1) ? keys1 : keys2;
			for (int index = 0; index < 5; index++) {
				if (keycode == playerKeys[index]) {
					var key = Key.values()[index];
					logic.registerInput(playerID, new InputEvent(key, pressed), System.nanoTime());
				}
			}
		}
		
		if(keycode == Keys.ESCAPE) {
			onReturn.run();
		}
	}
}