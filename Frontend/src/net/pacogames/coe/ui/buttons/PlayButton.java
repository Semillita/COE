package net.pacogames.coe.ui.buttons;

import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;

public class PlayButton extends Button {
	
private Consumer<Property> onClick;

private Texture hoverTexture;

private Property property;
	
	public PlayButton(Texture texture, Consumer<Property> onClick, Property property) {
		super(texture);
		this.property = property;
		this.onClick = onClick;
		hoverTexture = Resources.getTexture("colors/white_transparent.png");
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
		onClick.accept(property);
	}
	
	public static enum Property{
		NORMAL, TRAINING, CUSTOM;
	}
}

