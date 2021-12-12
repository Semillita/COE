package net.pacogames.coe.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class Arena {
	
	private final int BORDER_WIDTH = 200;
	
	private final int WIDTH = 2800 - 2 * BORDER_WIDTH;
	private final int HEIGHT = 1800 - 2 * BORDER_WIDTH;
	
	private final int X = (3840 - WIDTH) / 2;
	private final int Y = (2160 - HEIGHT) / 2;
	
	Texture border;
	
	Hitbox hitbox;
	
	public Arena() {
		border = Resources.getTexture("textures/map/arena.png");
		hitbox = new ArenaHitbox();
	}
	
	public void render(Batch batch) {
		batch.draw(border, X - BORDER_WIDTH, Y - BORDER_WIDTH, WIDTH + 2 * BORDER_WIDTH, HEIGHT + 2 * BORDER_WIDTH);
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getX() {
		return X;
	}
	public int getY() {
		return Y;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	private class ArenaHitbox implements Hitbox {

		@Override
		public float top() {
			return Y + HEIGHT;
		}

		@Override
		public float bottom() {
			return Y;
		}

		@Override
		public float right() {
			return X + WIDTH;
		}

		@Override
		public float left() {
			return X;
		}
		
	}
}
