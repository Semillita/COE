package net.pacogames.coe.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.Scene;
import net.pacogames.coe.ui.Button;

public class MainMenu implements Scene {
	
	private Camera camera;
	private Viewport viewport;
	private Button button;
	
	private final int VIEWPORT_WIDTH = 3840, VIEWPORT_HEIGHT = 2160;
	
	public MainMenu() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
		this.button = new Button(new Texture("Uchallengingme.png"));
	}
	
	@Override
	public void render(Batch batch, double deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		button.render(batch, VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();
	}

}
