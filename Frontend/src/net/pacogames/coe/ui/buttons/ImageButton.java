package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class ImageButton implements Button{
	
	private Texture texture;
	protected int x, y;
	protected int width, height;
	
	protected boolean hovered = false;
	protected boolean pressed = false;
	
	public ImageButton(Texture texture) {
		this.texture = texture;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Texture getTexture() {
		return texture;
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		Rectangle b = getBox();
		if(b.contains(x, y) && !hovered) {
			hover(true);
			System.out.println("Hover");
		}
		if(!b.contains(x, y) && hovered) {
			hover(false);
			System.out.println("Not hover");
		}
	}
	
	@Override
	public void hover(boolean hovered) {
		this.hovered = hovered;
		// TODO Auto-generated method stub
		System.out.println("h");
	}

	@Override
	public void press() {
		System.out.println("PRESS");
		if(hovered) {
			pressed = true;
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Batch batch) {
		
		/*if (hovered) {
			batch.draw(texture, x - (BIG_WIDTH - WIDTH)/2, y - (BIG_HEIGHT - HEIGHT)/2, BIG_WIDTH, BIG_HEIGHT);
		} else {
			batch.draw(texture, x, y, WIDTH, HEIGHT);
		}*/
		
		batch.draw(texture, x, y, width, height);
	}

	public Rectangle getBox() {
		return new Rectangle(x, y, width, height);
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
}
