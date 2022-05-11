package net.pacogames.coe.ui.buttons;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

/**Class for play button specific actions*/
public class PlayButton extends Button {
	
	private Texture hoverTexture;
	private Runnable onClick;
	
	public PlayButton(String name, Runnable onClick) {
		super(Resources.getTexture("buttons/play/" + name + ".png"));
		hoverTexture = Resources.getTexture("colors/white_transparent.png");
		this.onClick = onClick;
	}
	
	@Override
	public void render(Batch batch) {
		batch.draw(getBody(), x, y, width, height);	
		if(hovered) {
			batch.draw(hoverTexture, x, y, width, height);	
		}
		if(pressed) {
			batch.draw(hoverTexture, x, y, width, height);	
		}
	}
	
	@Override
	protected void onClick() {
		onClick.run();
	}
	
	public static enum Property{
		NORMAL, TRAINING, CUSTOM;
	}
}

