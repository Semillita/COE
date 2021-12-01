package net.pacogames.coe.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.Scene;
import net.pacogames.coe.resources.Resources;
import net.pacogames.coe.ui.NavBar;
import net.pacogames.coe.ui.buttons.Button;
import net.pacogames.coe.ui.buttons.ImageButton;
import net.pacogames.coe.ui.buttons.PlayButton;

public class MainMenu implements Scene {
	
	private final Runnable onPlay;
	
	private Camera camera;
	private Viewport viewport;
	private Button button;
	private MenuPage page;
	private NavBar navBar;
	private Texture bg;
	
	private final int VIEWPORT_WIDTH = 3840, VIEWPORT_HEIGHT = 2160;
	
	public MainMenu(Runnable onPlay) {
		this.onPlay = onPlay;
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
		navBar = new NavBar((int) (VIEWPORT_WIDTH - viewport.getWorldWidth())/2, (int) viewport.getWorldHeight() - 200, 
				(int) viewport.getWorldWidth(), 200, (property) -> {
					switch(property) {
					case PLAY:
						setPage(new PlayPage((prop) -> {
							if(prop == PlayButton.Property.NORMAL) {
								onPlay.run();
							}
						}));
					}
				});
		setInputListener();
		
		page = new PlayPage((prop) -> {
			if(prop == PlayButton.Property.NORMAL) {
				onPlay.run();
			}
		});
		bg = Resources.getTexture("menu/BG.png");
	}
	
	@Override
	public void render(Batch batch, double deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		batch.draw(bg, (viewport.getWorldWidth() - VIEWPORT_WIDTH)/2, (viewport.getWorldHeight() - VIEWPORT_HEIGHT)/2, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		navBar.render(batch);
		page.render(batch);
		page.resize((int) (viewport.getWorldWidth() - VIEWPORT_WIDTH)/2, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight() - 200);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();		
		
		navBar.setBounds((int) (VIEWPORT_WIDTH - viewport.getWorldWidth())/2, (int) (viewport.getWorldHeight() - 200 - (viewport.getWorldHeight() - VIEWPORT_HEIGHT)/2), 
				(int) viewport.getWorldWidth(), 200);
		
		page.resize((int) (viewport.getWorldWidth() - VIEWPORT_WIDTH)/2, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight() - 200);
	}
	public void setInputListener() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			
			@Override
			public boolean mouseMoved(int x, int y) {
				Vector3 worldCords = new Vector3(x, y, 0);
				worldCords = camera.unproject(worldCords);
				x = (int) worldCords.x;
				y = (int) worldCords.y;
				/*Rectangle b = button.getBox();
				if(b.contains(x, y) && !button.isHovered()){
					button.hover(true);
				}
				if(!b.contains(x, y) && button.isHovered()) {
					button.hover(false);
				}*/
				navBar.mouseMoved(x, y);
				page.mouseMoved(x, y);
				return false;
			}

			@Override
			public boolean scrolled(int arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
				navBar.click();
				page.press();
				return false;
			}

			@Override
			public boolean touchDragged(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
		}); 
	}
	
	private void setPage(MenuPage page) {
		if(page.getClass() != this.page.getClass()) {
			this.page = page;
		}
	}

}
