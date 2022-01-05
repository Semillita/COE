package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class ArenaSprite {

	public static final float WIDTH = 2800, HEIGHT = 1800;
	
	private final Texture body;
	private int x = 0, y = 0;
	
	public ArenaSprite() {
		body = Resources.getTexture("map/map.png");
	}
	
	public void render(Batch batch) {
		batch.draw(body, x, y, 2800, 1800);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
