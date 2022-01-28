package net.pacogames.coe.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import net.pacogames.coe.menu.MenuPage;
import net.pacogames.coe.menu.PageProperty;
import net.pacogames.coe.menu.PlayPage;
import net.pacogames.coe.menu.SettingsPage;
import net.pacogames.coe.resources.Resources;
import net.pacogames.coe.ui.buttons.NavButton;

public class NavBar {
	
	private Texture background;
	private Texture highlight;
	private int x, y, width, height;
	
	private NavButton settingsButton;
	private NavButton playButton;
	/*Texture settingsTexture;
	Texture playTexture;*/
	
	List<NavButton> buttons;
	
	public NavBar (int x,int y, int width, int height, Consumer<PageProperty> buttonClickListener) {
		background = Resources.getTexture("colors/black.png");
		highlight = Resources.getTexture("colors/yellow_highlight_fade.png");
		var settingsTexture = Resources.getTexture("buttons/nav/settings.png");
		var playTexture = Resources.getTexture("buttons/nav/play.png");
		
		Runnable playPageButtonClickListener = () -> buttonClickListener.accept(PageProperty.PLAY);
		playButton = new NavButton(playTexture, playPageButtonClickListener);
		
		Runnable settingsPageButtonClickListener = () -> buttonClickListener.accept(PageProperty.SETTINGS);
		settingsButton = new NavButton(settingsTexture, settingsPageButtonClickListener);
		
		buttons = new ArrayList<>();
		buttons.add(playButton);
		buttons.add(settingsButton);
	}
	
	public void render(Batch batch) {
		batch.draw(background, x, y, width, height);
		batch.draw(highlight, 0, y - 5, width, 10);
		for(NavButton button : buttons) {
			button.render(batch);
		}
	}
	
	public void setBounds(final int x, final int y, final int width, final int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		positionButtons(x, y, width, height);
	}
	
	public void mouseMoved(int x, int y) {
		for(NavButton button : buttons) {
			button.mouseMoved(x, y);
		}
	}
	
	public void mousePressed(int x, int y) {
		for(NavButton button : buttons) {
			button.mousePressed(x, y);
		}
	}
	
	public void mouseReleased(int x, int y) {
		for(NavButton button : buttons) {
			button.mouseReleased(x, y);
		}
	}
	
	private void positionButtons(final int x, final int y, final int width, final int height) {
		int xPos = 100 + x;
		final int buttonHeight = (int) (height * 0.6);
		for(NavButton button : buttons) {
			Texture buttonTexture = button.getBody();
			int buttonWidth = (int) (buttonHeight * buttonTexture.getWidth() / buttonTexture.getHeight());
			int yPos = (height - buttonHeight)/2 + y;
			button.setBounds(xPos, yPos, buttonWidth, buttonHeight);
			xPos += buttonTexture.getWidth() + 100;
		}
	}

}
