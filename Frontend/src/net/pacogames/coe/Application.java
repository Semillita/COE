package net.pacogames.coe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.pacogames.coe.game.LocalGame;
import net.pacogames.coe.menu.MainMenu;

public class Application extends ApplicationAdapter {

	SpriteBatch batch;
	Scene scene;
	
	double nanoTime;
	
	private int iteration = 0;
	private int windowWidth, windowHeight;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		setScene(new MainMenu(() -> setScene(new LocalGame())));
		
		//Borderless windowed fullscreen
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
	    Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
	    
	    nanoTime = System.nanoTime();
	}

	@Override
	public void render() {
		/*
		 * if (iteration == 1) { scene = new LocalGame(); }
		 */
		//iteration += 1;
		
		//Clear window
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
		batch.begin();		
		scene.render(batch, getDeltaTime() / 1_000_000_000);
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
	
	private void setScene(Scene scene) {
		this.scene = scene;
		scene.resize(windowWidth, windowHeight);
		batch = new SpriteBatch();
	}
	
	private double getDeltaTime() {
		double newTime = System.nanoTime();
		double deltaTime = newTime - nanoTime;
		nanoTime = newTime;
		return deltaTime;
	}
	
}