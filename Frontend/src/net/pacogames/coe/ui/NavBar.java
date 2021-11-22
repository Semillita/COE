package net.pacogames.coe.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import net.pacogames.coe.ui.buttons.NavButton;

public class NavBar {
	
	private Texture texture;
	private int x, y, width, height;
	
	private NavButton settingsButton;
	private NavButton playButton;
	Texture settingsTexture;
	Texture playTexture;
	
	List<NavButton> buttons;
	
	public NavBar (int x,int y, int width, int height) {
		texture = new Texture("menu/BlackPixel.png");
		settingsTexture = new Texture("menu/SETTINGS.png");
		playTexture = new Texture("menu/SETTINGS.png");
		settingsButton = new NavButton(settingsTexture, 10, (height - settingsTexture.getHeight())/2 + y, 
				settingsTexture.getWidth(), settingsTexture.getHeight());
		playButton = new NavButton(playTexture, 10, (height - playTexture.getHeight())/2 + y, playTexture.getWidth(), 
				playTexture.getHeight());
		buttons = new ArrayList<>();
		buttons.add(settingsButton);
		buttons.add(playButton);
	}
	
	public void render(Batch batch) {
		batch.draw(texture, x, y, width, height);
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
	
	private void positionButtons(final int x, final int y, final int width, final int height) {
		int xPos = 10;
		for(NavButton button : buttons) {
			Texture buttonTexture = button.getTexture();
			int yPos = (height - buttonTexture.getHeight())/2 + y;
			System.out.println(xPos + ", " + yPos);
			button.setBounds(xPos, yPos, buttonTexture.getWidth(), buttonTexture.getHeight());
			xPos += buttonTexture.getWidth() + 200;
		}
	}

}
