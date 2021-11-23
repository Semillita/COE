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
	
	public NavBar (int x,int y, int width, int height, Consumer<MenuPage> onButtonClick) {
		background = new Texture("menu/BlackPixel.png");
		var settingsTexture = Resources.getTexture("menu/SETTINGS.png");
		var playTexture = Resources.getTexture("menu/SETTINGS.png");
		settingsButton = new NavButton(settingsTexture, () -> onButtonClick.accept(new SettingsPage()));
		playButton = new NavButton(playTexture, () -> onButtonClick.accept(new PlayPage()));
		buttons = new ArrayList<>();
		buttons.add(settingsButton);
		buttons.add(playButton);
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
			Rectangle b = button.getBox();
			if(b.contains(x, y) && !button.isHovered()){
				button.hover(true);
				System.out.println("Hover");
			}
			if(!b.contains(x, y) && button.isHovered()) {
				button.hover(false);
				System.out.println("Not hover");
			}
		}
		
		Rectangle rec = buttons.get(0).getBox();
		System.out.println(rec.x);
		System.out.println(rec.y);
		System.out.println(rec.width);
		System.out.println(rec.height);
	}
	
	private void positionButtons(final int x, final int y, final int width, final int height) {
		int xPos = 10 + x;
		for(NavButton button : buttons) {
			Texture buttonTexture = button.getTexture();
			int yPos = (height - buttonTexture.getHeight())/2 + y;
			System.out.println(xPos + ", " + yPos);
			button.setBounds(xPos, yPos, buttonTexture.getWidth(), buttonTexture.getHeight());
			xPos += buttonTexture.getWidth() + 200;
		}
	}

}
