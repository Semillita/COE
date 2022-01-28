package net.pacogames.coe.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.Scene;
import net.pacogames.coe.resources.Resources;
import net.pacogames.coe.ui.NavBar;
import net.pacogames.coe.ui.buttons.Button;
import net.pacogames.coe.ui.buttons.PlayButton;

public class MainMenuScene implements Scene {

	private final int VIEWPORT_WIDTH = 3840, VIEWPORT_HEIGHT = 2160;
	private final Runnable onPlay;
	private final Camera camera;
	private final Viewport viewport;
	private final Batch batch;
	private final NavBar navBar;
	private final Texture bg;
	private final Texture logo;
	
	private MenuPage page;

	public MainMenuScene(Runnable onPlay) {
		this.onPlay = onPlay;
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
		
		batch = new SpriteBatch();
		navBar = new NavBar((int) (VIEWPORT_WIDTH - viewport.getWorldWidth()) / 2,
				(int) viewport.getWorldHeight() - 200, (int) viewport.getWorldWidth(), 200, this::navigationButtonClickListener);

		bg = Resources.getTexture("backgrounds/main_menu_high_res.png");
		logo = Resources.getTexture("backgrounds/main_menu_logo.png");

		page = new PlayPage(this::playButtonClickListener);
		setInputListener();
	}

	@Override
	public void render() {
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		
		batch.draw(bg, (viewport.getWorldWidth() - VIEWPORT_WIDTH) / 2,
				(viewport.getWorldHeight() - VIEWPORT_HEIGHT) / 2, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		batch.draw(logo, (viewport.getWorldWidth() - VIEWPORT_WIDTH) / 2,
				(viewport.getWorldHeight() - VIEWPORT_HEIGHT) / 2, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		
		navBar.render(batch);
		page.render(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();

		navBar.setBounds((int) (VIEWPORT_WIDTH - viewport.getWorldWidth()) / 2,
				(int) (viewport.getWorldHeight() - 150 - (viewport.getWorldHeight() - VIEWPORT_HEIGHT) / 2),
				(int) viewport.getWorldWidth(), 150);

		page.resize((int) (viewport.getWorldWidth() - VIEWPORT_WIDTH) / 2, (int) viewport.getWorldWidth(),
				(int) viewport.getWorldHeight() - 200);
	}

	private void setInputListener() {
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean mouseMoved(int x, int y) {
				Vector3 worldCords = new Vector3(x, y, 0);
				worldCords = camera.unproject(worldCords);
				x = (int) worldCords.x;
				y = (int) worldCords.y;
				navBar.mouseMoved(x, y);
				page.mouseMoved(x, y);
				return false;
			}

			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				Vector3 worldCords = new Vector3(x, y, 0);
				worldCords = camera.unproject(worldCords);
				x = (int) worldCords.x;
				y = (int) worldCords.y;
				navBar.mousePressed(x, y);
				page.mousePressed(x, y);
				return false;
			}

			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				Vector3 worldCords = new Vector3(x, y, 0);
				worldCords = camera.unproject(worldCords);
				x = (int) worldCords.x;
				y = (int) worldCords.y;
				navBar.mouseReleased(x, y);
				page.mouseReleased(x, y);
				return false;
			}

			@Override
			public boolean touchDragged(int x, int y, int arg2) {
				mouseMoved(x, y);
				return false;
			}
		});
	}

	private void setPage(MenuPage page) {
		if (page.getClass() != this.page.getClass()) {
			page.resize((int) (viewport.getWorldWidth() - VIEWPORT_WIDTH) / 2, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight() - 200);
			this.page = page;
			
		}
	}
	
	private void navigationButtonClickListener(PageProperty property) {
		switch (property) {
		case PLAY:
			setPage(new PlayPage(this::playButtonClickListener));
			break;
		case SETTINGS:
			setPage(new SettingsPage());
		}
	}
	
	private void playButtonClickListener(PlayButton.Property property) {
		if (property == PlayButton.Property.NORMAL) {
			onPlay.run();
		}
	}

}
