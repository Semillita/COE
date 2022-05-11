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

/** The game's implementation of the {@link Scene} interface */
public class GameScene implements Scene {

	private final int[] keys1 = { Keys.W, Keys.D, Keys.S, Keys.A, Keys.SPACE },
			keys2 = { Keys.UP, Keys.RIGHT, Keys.DOWN, Keys.LEFT, Keys.ENTER };
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

	/**
	 * Renders the game
	 * 
	 * Loads frames in advance from the physics engine, renders the current frame
	 * and ends the game if it's over.
	 */
	@Override
	public void render() {
		logic.loadAdvanceFrames();
		var currentFrame = logic.getCurrentFrame();

		graphics.renderFrame(currentFrame);

		if (currentFrame.gameOver) {
			COE.getApp().endGame();
		}
	}

	/** Updates the layout of the game 
	 * 
	 * @param width the new width of the window
	 * @param height the new height of the window*/
	@Override
	public void resize(int width, int height) {
		graphics.resize(width, height);
	}

	/**
	 * Sets the input listener of the window
	 * 
	 * Cretes a new instance of {@link InputAdapter} with overridden methods for
	 * handling key presses.
	 */
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

	/**
	 * Registers a key press event in a player's input queue
	 * 
	 * Matches the key with a either a player's key and registers it with the
	 * physics engine or a specific key to return to the main menu.
	 * 
	 * @param keycode the specific code of the key that is pressed
	 * @param pressed whether the key was pressed. false if the key was released
	 */
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

		if (keycode == Keys.ESCAPE) {
			onReturn.run();
		}
	}
}