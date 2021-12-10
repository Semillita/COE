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
		batch.draw(marker, getX() + value * getWidth(), getY(), 100, 30);
	}
	
	@Override
	public void onMouseMoved(int x, int y) {
		value = (float) (x - getX()) / getWidth();
		System.out.println(getWidth());	
	}
	
}
