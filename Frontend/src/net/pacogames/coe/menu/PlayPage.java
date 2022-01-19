package net.pacogames.coe.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import net.pacogames.coe.resources.Resources;
import net.pacogames.coe.ui.buttons.NavButton;
import net.pacogames.coe.ui.buttons.PlayButton;

import static net.pacogames.coe.ui.buttons.PlayButton.Property;

public class PlayPage implements MenuPage {
	
	List<PlayButton> buttons;
	
	public PlayPage(Consumer<Property> onPlay) {
		buttons = new ArrayList<>();
		buttons.add(new PlayButton(Resources.getTexture("buttons/play/local.png"), onPlay, Property.NORMAL));
		buttons.add(new PlayButton(Resources.getTexture("buttons/play/ranked.png"), onPlay, Property.TRAINING));
		buttons.add(new PlayButton(Resources.getTexture("buttons/play/custom2.png"), onPlay, Property.CUSTOM));
	}
	
	public void render(Batch batch) {
		for(PlayButton button : buttons) {
			button.render(batch);
		}
	}
	
	public void mouseMoved(int x, int y) {
		for(PlayButton button : buttons) {
			button.mouseMoved(x, y);
		}
	}
	
	public void resize(int x, int width, int height) {
		positionButtons(x, width, height);
	}
	
	private void positionButtons(int x, final int width, final int height) {
		final int GAP = 400;
		for(int i = 0; i < buttons.size(); i++) {
			PlayButton button = buttons.get(i);
			Texture buttonTexture = button.getBody();
			int xPos = width / 2 - (buttons.size() * buttonTexture.getWidth() + (buttons.size() - 1) * GAP) / 2 + i * (buttonTexture.getWidth() + GAP - x);
			//int xPos = width / 2 - (buttons.size() * buttonTexture.getWidth() / 2 + buttons.size() * GAP) + i * (buttonTexture.getWidth() + GAP);
			int yPos = (height/2 - buttonTexture.getHeight()/2);
			button.setBounds(xPos, yPos, buttonTexture.getWidth(), buttonTexture.getHeight());
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		for(PlayButton button : buttons) {
			button.mousePressed(x, y);
		}
	}
	
	@Override
	public void mouseReleased(int x, int y) {
		for(PlayButton button : buttons) {
			button.mouseReleased(x, y);
		}
	}
}