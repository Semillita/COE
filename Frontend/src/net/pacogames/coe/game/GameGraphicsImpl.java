package net.pacogames.coe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.logic.game.runtime.Frame;

public class GameGraphicsImpl implements GameGraphics {

	private static final int VIEWPORT_WIDTH = 3840;
	private static final int VIEWPORT_HEIGHT = 2160;

	private final Camera camera;
	private final Viewport viewport;
	private final Batch batch;
	private final PlayerSprite player1, player2;
	private final ArenaSprite arena;

	public GameGraphicsImpl() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
		batch = new SpriteBatch();
		player1 = new PlayerSprite(1);
		player2 = new PlayerSprite(2);
		arena = new ArenaSprite();
	}

	/**Renders a frame*/
	@Override
	public void renderFrame(Frame frame) {
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		
		arena.render(batch);
		var p1pos = frame.player1data.pos;
		player1.render(batch, p1pos.x, p1pos.y);
		var p2pos = frame.player2data.pos;
		player2.render(batch, p2pos.x, p2pos.y);
		
		batch.end();
	}

	/**Updates the scene's layout*/
	@Override
	public void resize(int viewportWidth, int viewportHeight) {
		viewport.update(viewportWidth, viewportHeight, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();
		
		var centerX = VIEWPORT_WIDTH / 2;
		var centerY = VIEWPORT_HEIGHT / 2;
		player1.setOffset(centerX - 1400 + 220, centerY - 900 + 220);
		player2.setOffset(centerX - 1400 + 220, centerY - 900 + 220);
		arena.setOffset(centerX - 1400, centerY - 900);
	}

}