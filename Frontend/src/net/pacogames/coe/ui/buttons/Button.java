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
		
	}
	
	public void mousePressed(int x, int y) {
		
	}
	
	public void mouseReleased(int x, int y) {
		
	}
	
	public void onHover(int x, int y) {
		
	}
	
	public void onPress() {
		
	}
	
	public void onRelease() {
		
	}
	
	public void onClick() {
		
	}
	
	public boolean isHovered() {
		return hovered;
	}
}
