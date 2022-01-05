package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class PlayerSprite {

	public static final float WIDTH = 80, HEIGHT = 160;
	
	private final Texture body;
	private int x = 0, y = 0;
	
	public PlayerSprite(int playerID) {
		body = Resources.getTexture("player/player" + playerID + ".png");
	}
	
	public void render(Batch batch, float x, float y) {
		batch.draw(body, this.x + x - WIDTH / 2, this.y + y - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}