package net.pacogames.coe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.pacogames.coe.game.Game;
import net.pacogames.coe.menu.MainMenu;

public class Application extends ApplicationAdapter {

	SpriteBatch batch;
	Scene scene;
	
	double nanoTime;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		scene = new Game();
		
		//Borderless windowed fullscreen
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
	    Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
	    
	    nanoTime = System.nanoTime();
	}

	@Override
	public void render() {
		//Clear window
		Gdx.gl.glClearColor(0, 0, 0, 0);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
		batch.begin();		
		scene.render(batch, getDeltaTime() / 1_000_000_000);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}
	
	@Override
	public void dispose() {
		
	}
	
	private double getDeltaTime() {
		double newTime = System.nanoTime();
		double deltaTime = newTime - nanoTime;
		nanoTime = newTime;
		return deltaTime;
	}
	
}