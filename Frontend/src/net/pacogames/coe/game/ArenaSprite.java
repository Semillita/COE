package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class ArenaSprite {

	public static final float WIDTH = 2800, HEIGHT = 1800;
	
	private final Texture body;
	private int offsetX = 0, offsetY = 0;
	
	public ArenaSprite() {
		body = Resources.getTexture("map/arena.png");
	}
	
	/**Renders the sprite*/
	public void render(Batch batch) {
		//batch.draw(body, offsetX, offsetY, 2800, 1800);
		batch.draw(body, 0, 0, 3840, 2160);
	}
	
	/**Sets the offset of the sprite*/
	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
}
