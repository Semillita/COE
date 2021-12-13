package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Slider extends Button{

	private float value;
	private Texture marker;
	
	public Slider(Texture body, Texture marker) {
		super(body);
		this.marker = marker;
	}
	
	@Override
	public void render(Batch batch) {
		super.render(batch);
		batch.draw(marker, getX() + value * getWidth() - 10, getY() - 5, 20, getHeight() + 10);
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if(super.pressed && x >= getX() && x < getX() + getWidth()) {
			value = (float) (x - getX()) / getWidth();
		}
	}
	
	@Override
	public void mousePressed(int x, int y) {
		super.mousePressed(x, y);
		if(super.isInside(x, y)) {
			value = (float) (x - getX()) / getWidth();
		}
	}
}