package net.pacogames.coe.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class ImageButton implements Button{
	
	private static final int BIG_HEIGHT = 600;
	private static final int BIG_WIDTH = 600;
	private static final int HEIGHT = 500;
	private static final int WIDTH = 500;
	
	
	public Texture texture;
	public float x, y;
	public boolean hovered = false;
	
	public ImageButton(Texture texture, float x, float y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
	}

	@Override
	public void hover(boolean hovered) {
		this.hovered = hovered;
		// TODO Auto-generated method stub
	}

	@Override
	public void press() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Batch batch) {
		
		if (hovered) {
			batch.draw(texture, x - (BIG_WIDTH - WIDTH)/2, y - (BIG_HEIGHT - HEIGHT)/2, BIG_WIDTH, BIG_HEIGHT);
		} else {
			batch.draw(texture, x, y, WIDTH, HEIGHT);
		}
	}

	public Rectangle getBox() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
}
