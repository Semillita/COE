package net.pacogames.coe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.pacogames.coe.game.GameScene;
import net.pacogames.coe.menu.MainMenu;

public class Application extends ApplicationAdapter {

	SpriteBatch batch;
	Scene scene;

	private int iteration = 0;
	private int windowWidth, windowHeight;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScene(new MainMenu(() -> setScene(new GameScene())));

		//setDisplayMode();
	}

	@Override
	public void render() {
		// Clear window
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		scene.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
		windowWidth = width;
		windowHeight = height;

	}

	@Override
	public void dispose() {

	}

	private void setDisplayMode() {
		// Borderless windowed fullscreen
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
	}

	private void setScene(Scene scene) {
		this.scene = scene;
		scene.resize(windowWidth, windowHeight);
		batch = new SpriteBatch();
	}
}