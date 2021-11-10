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
import net.pacogames.coe.ui.Button;
import net.pacogames.coe.ui.ImageButton;

public class MainMenu implements Scene {
	
	private Camera camera;
	private Viewport viewport;
	private Button button;
	
	
	private final int VIEWPORT_WIDTH = 3840, VIEWPORT_HEIGHT = 2160;
	
	public MainMenu() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
		this.button = new ImageButton(new Texture("Uchallengingme.png"), camera.viewportWidth / 2, camera.viewportHeight / 2);
		setInputListener();
	}
	
	@Override
	public void render(Batch batch, double deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		button.render(batch);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();			
	}
	public void setInputListener() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			
			@Override
			public boolean mouseMoved(int x, int y) {
				Vector3 worldCords = new Vector3(x, y, 0);
				worldCords = camera.unproject(worldCords);
				x = (int) worldCords.x;
				y = (int) worldCords.y;
				Rectangle b = button.getBox();
				if(b.contains(x, y) && !button.isHovered()){
					button.hover(true);
				}
				if(!b.contains(x, y) && button.isHovered()) {
					button.hover(false);
				}
				return false;
			}

			@Override
			public boolean scrolled(int arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
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

}
