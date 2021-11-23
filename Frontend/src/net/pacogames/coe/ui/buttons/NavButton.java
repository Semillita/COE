package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class NavButton extends ImageButton {

	private Runnable onClick;
	
	public NavButton(Texture texture, Runnable onClick) {
		super(texture);
	}
	
	@Override
	public void render(Batch batch) {
		if(!hovered) {
			batch.draw(getTexture(), x, y, width, height);	
		} else {
			batch.draw(getTexture(), x - 10, y - 10, width + 20, height + 20);	
		}
	}
	
	@Override
	public void hover(boolean hovered) {
		this.hovered = hovered;
	}
	
}
