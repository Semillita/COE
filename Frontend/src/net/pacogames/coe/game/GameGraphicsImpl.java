package net.pacogames.coe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.logic.game.runtime.Frame;

public class GameGraphicsImpl implements GameGraphics {

	private static final int VIEWPORT_WIDTH = 3840;
	private static final int VIEWPORT_HEIGHT = 2160;

	private Camera camera;
	private Viewport viewport;

	private PlayerSprite player1, player2;

	public GameGraphicsImpl() {
		initCameraViewport();

		player1 = new PlayerSprite(1);
		player2 = new PlayerSprite(2);
	}

	@Override
	public void renderFrame(Batch batch, Frame frame) {
		batch.setProjectionMatrix(camera.combined);

		var p1pos = frame.player1data.pos;
		var p2pos = frame.player2data.pos;

		player1.render(batch, p1pos.x, p1pos.y);
		player2.render(batch, p2pos.x, p2pos.y);

	}

	@Override
	public void resize(int viewportWidth, int viewportHeight) {
		viewport.update(viewportWidth, viewportHeight, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();
	}

	private void initCameraViewport() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
	}

}