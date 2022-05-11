package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

/**Sprite for the arena background*/
public class ArenaSprite {

	public static final float WIDTH = 2800, HEIGHT = 1800;
	
	private final Texture body;
	
	public ArenaSprite() {
		body = Resources.getTexture("map/arena.png");
	}
	
	/**Renders the sprite
	 * 
	 * @param batch the batch renderer used to draw the arena background*/
	public void render(Batch batch) {
		batch.draw(body, 0, 0, 3840, 2160);
	}
	
}
