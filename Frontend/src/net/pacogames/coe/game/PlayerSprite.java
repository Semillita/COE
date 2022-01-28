package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class PlayerSprite {

	public static final float WIDTH = 80, HEIGHT = 160;
	
	private final Texture body;
	private int offsetX = 0, offsetY = 0;
	
	public PlayerSprite(int playerID) {
		body = Resources.getTexture("player/player" + playerID + ".png");
	}
	
	public void render(Batch batch, float x, float y) {
		batch.draw(body, this.offsetX + x - WIDTH / 2, this.offsetY + y - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}