package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	
	private Texture body;
	protected int x, y;
	protected int width, height;
	
	protected boolean hovered = false;
	protected boolean pressed = false;
	
	public void render(Batch batch) {
		batch.draw(body, x, y, width, height);
	}
	
	public void mouseMoved(int x, int y) {
		if(isInside(x, y)) {
			onMouseMoved(x, y);
			if(!hovered) {
				hovered = true;
			}
		} else {
			if(hovered) {
				hovered = false;
			}
		}
	}
	
	public void mousePressed(int x, int y) {
		if(isInside(x, y)) {
			pressed = true;
			onPress();
		}
	}
	
	public void mouseReleased(int x, int y) {
		onRelease();
		if(isInside(x, y) && pressed) {
			onClick();
		}
	}
	
	protected void onMouseMoved(int x, int y) {
		
	}
	
	protected void onEnter() {
	
	}
	
	protected void onExit() {
		
	}
	
	protected void onPress() {
		
	}
	
	protected void onRelease() {
		
	}
	
	protected void onClick() {
		
	}
	
	public Texture getBody() {
		return body;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	private boolean isInside(int x, int y) {
		return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
	}
}
