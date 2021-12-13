package net.pacogames.coe.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.Scene;
import net.pacogames.coe.logic.game.Match;
import net.pacogames.coe.resources.Resources;

public class Game extends Match implements Scene {

	private final int VIEWPORT_WIDTH = 3840, VIEWPORT_HEIGHT = 2160;
	
	private Camera camera;
	private Viewport viewport;
	
	private Arena arena;
	
	private Texture sprite;
	
	private Player player1;
	private Player player2;
	
	private ExecutorService pool;
	
	public Game() {
		super();
		
		arena = new Arena();
		
		initCameraViewport();
		
		int[] keys1 = {
				Keys.W, Keys.D, Keys.S, Keys.A
		};
		player1 = new Player(this, keys1, Resources.getTexture("player/red.png"), 1300, new Vector2(-1000, 1000));
		
		int[] keys2 = {
				Keys.UP, Keys.RIGHT, Keys.DOWN, Keys.LEFT
		};
		player2 = new Player(this, keys2, Resources.getTexture("player/blue.png"), 1500, new Vector2(800, 1800));
		
		pool = Executors.newSingleThreadExecutor();
		
		//super.start();
		
	}
	
	@Override
	public void render(Batch batch, double deltaTime) {
		//Se till att frames finns:
		super.updateFrames();
		
		//Plocka mest accurate frame
		var frame = super.getClosestFrame();
		var p1 = frame.player1data;
		var p2 = frame.player2data;
		
		batch.setProjectionMatrix(camera.combined);
		
		//Rita spelare
		player1.render(batch, (int) p1.pos.x, (int) p1.pos.y);
		player2.render(batch, (int) p2.pos.x, (int) p2.pos.y);
		
		arena.render(batch);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();
		
	}
	
	private void initCameraViewport() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
	}
}
