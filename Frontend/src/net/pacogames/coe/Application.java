package net.pacogames.coe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.pacogames.coe.game.GameScene;
import net.pacogames.coe.menu.MainMenuScene;

public class Application extends ApplicationAdapter {

	private Scene scene;

	/**Initializes basic components*/
	@Override
	public void create() {
		setScene(new MainMenuScene(this::startGame));
		setDisplayMode();
	}

	/**Clears the window's frame buffer and renders the current scene*/
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		scene.render();
	}

	/**Called when the window is resized, updates layout of current scene*/
	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}

	/**Sets the current scene to a new game scene*/
	public void startGame() {
		setScene(new GameScene(this::endGame));
	}
	
	/**Sets the current scene to a new menu scene*/
	public void endGame() {
		setScene(new MainMenuScene(this::startGame));
	}
	
	/**Sets the window's display mode to borderless windowed fullscreen*/
	private void setDisplayMode() {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
	}

	/**Sets the current scene*/
	private void setScene(Scene scene) {
		scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.scene = scene;
	}
}