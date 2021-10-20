package net.pacogames.coe.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Button {
	
	private Texture texture;
	
	public Button(Texture texture) {
		this.texture = texture;	
	}
	
	public void render(Batch batch, float x, float y) {
		batch.draw(texture, x, y);
	}
}
