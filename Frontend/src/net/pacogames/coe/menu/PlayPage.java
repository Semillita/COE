package net.pacogames.coe.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import net.pacogames.coe.ui.buttons.PlayButton;

/** The "play" page impementation of the {@link MenuPage} interface */
public class PlayPage implements MenuPage {

	private final List<PlayButton> buttons;

	public PlayPage(Consumer<PlayButton.Property> onPlay) {
		buttons = new ArrayList<>();
		Runnable playButtonClickListener = () -> onPlay.accept(PlayButton.Property.NORMAL);
		buttons.add(new PlayButton("normal", playButtonClickListener));
		buttons.add(new PlayButton("2v2", playButtonClickListener));
		buttons.add(new PlayButton("custom", playButtonClickListener));
	}

	/**
	 * Renders the page
	 * 
	 * @param batch the renderer used to render the page
	 */
	@Override
	public void render(Batch batch) {
		for (PlayButton button : buttons) {
			button.render(batch);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		for (PlayButton button : buttons) {
			button.mouseMoved(x, y);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		for (PlayButton button : buttons) {
			button.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		for (PlayButton button : buttons) {
			button.mouseReleased(x, y);
		}
	}

	/**
	 * Updates the page's layout
	 * 
	 * Called when the window is resized
	 * 
	 * @param x      the x-offset of the viewport in the world
	 * @param width  the width of the page
	 * @param height the height of the page
	 */
	@Override
	public void resize(int x, int width, int height) {
		positionButtons(x, width, height);
	}

	/**
	 * Positions the buttons on the page
	 * 
	 * @param x      the x-offset of the viewport in the world
	 * @param width  the width of the page
	 * @param height the height of the page
	 */
	private void positionButtons(int x, final int width, final int height) {
		final int GAP = 400;
		for (int i = 0; i < buttons.size(); i++) {
			PlayButton button = buttons.get(i);
			Texture buttonTexture = button.getBody();
			int xPos = width / 2 - (buttons.size() * buttonTexture.getWidth() + (buttons.size() - 1) * GAP) / 2
					+ i * (buttonTexture.getWidth() + GAP - x);
			int yPos = (height / 2 - buttonTexture.getHeight() / 2);
			button.setBounds(xPos, yPos, buttonTexture.getWidth(), buttonTexture.getHeight());
		}
	}
}