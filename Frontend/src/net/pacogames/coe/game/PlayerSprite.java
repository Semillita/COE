package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

/**Sprite to draw the player*/
public class PlayerSprite {

	public static final float WIDTH = 130, HEIGHT = 160;
	
	Texture body;
	private int offsetX = 0, offsetY = 0;
	
	public PlayerSprite(int playerID) {
		body = Resources.getTexture("player/player" + playerID + ".png");
	}
	
	/**Renders the player
	 * 
	 * @param batch the renderer used to draw the sprite
	 * @param x the x-position within the map
	 * @param y the y-position within the map*/
	public void render(Batch batch, float x, float y) {
		batch.draw(body, this.offsetX + x - WIDTH / 2, this.offsetY + y - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
	/**Sets the offset of the players in the world in relation to their position within the map
	 * 
	 * @param offsetX the x-offset
	 * @param offsetY the y-offset*/
	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}