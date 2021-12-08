package net.pacogames.coe.menu;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.ui.buttons.NavButton;

public interface MenuPage {
	public void render(Batch batch);
	
	public void resize(int x, int width, int height);
	
	public void mouseMoved(int x, int y);
	
	public void mousePressed(int x, int y);
	
	public void mouseReleased(int x, int y);
}
