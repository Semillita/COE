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
	private int x, y, width, height;
	
	private NavButton settingsButton;
	private NavButton playButton;
	/*Texture settingsTexture;
	Texture playTexture;*/
	
	List<NavButton> buttons;
	
	public NavBar (int x,int y, int width, int height, Consumer<PageProperty> onButtonClick) {
		background = Resources.getTexture("colors/black.png");
		var settingsTexture = Resources.getTexture("buttons/nav/settings.png");
		var playTexture = Resources.getTexture("buttons/nav/play.png");
		
		playButton = new NavButton(playTexture, () -> {
			onButtonClick.accept(PageProperty.PLAY);
		});
		settingsButton = new NavButton(settingsTexture, () -> onButtonClick.accept(PageProperty.SETTINGS));
		
		buttons = new ArrayList<>();
		buttons.add(playButton);
		buttons.add(settingsButton);
	}
	
	public void render(Batch batch) {
		batch.draw(background, x, y, width, height);
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
		for(NavButton button : buttons) {
			Texture buttonTexture = button.getBody();
			int yPos = (height - buttonTexture.getHeight())/2 + y;
			button.setBounds(xPos, yPos, buttonTexture.getWidth(), buttonTexture.getHeight());
			xPos += buttonTexture.getWidth() + 100;
		}
	}

}
