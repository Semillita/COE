package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class PlayButton extends ImageButton {
	
private Runnable onClick;

private Texture hoverTexture;
	
	public PlayButton(Texture texture, Runnable onClick) {
		super(texture);
		hoverTexture = Resources.getTexture("menu/TransparentWhitePixel.png");
	}
	
	@Override
	public void render(Batch batch) {
		batch.draw(getTexture(), x, y, width, height);	
		if(hovered) {
			batch.draw(hoverTexture, x, y, width, height);	
		}
		if(pressed) {
			batch.draw(hoverTexture, x, y, width, height);	
		}
	}
	
	@Override
	public void hover(boolean hovered) {
		this.hovered = hovered;
	}
}

