package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**Button in the navigation bar*/
public class NavButton extends Button {

	private Runnable onClick;
	
	public NavButton(Texture texture, Runnable onClick) {
		super(texture);
		this.onClick = onClick;
	}
	
	@Override
	public void render(Batch batch) {
		if(pressed) {
			batch.draw(getBody(), x - 5, y - 5, width + 10, height + 10);
		} else {
			if(!hovered) {
				batch.draw(getBody(), x, y, width, height);	
			} else {
				batch.draw(getBody(), x - 10, y - 10, width + 20, height + 20);	
			}
		}
	}
	
	@Override
	public void onClick() {
		super.onClick();
		onClick.run();
	}
	
}
