package net.pacogames.coe.menu;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.pacogames.coe.resources.Resources;
import net.pacogames.coe.ui.buttons.Slider;

public class SettingsPage implements MenuPage{

	private Slider volume;
	
	public SettingsPage() {
		volume = new Slider(Resources.getTexture("menu/Slider.png"), Resources.getTexture("menu/SliderMarker.png"));
	}
	
	@Override
	public void render(Batch batch) {
		volume.render(batch);
		
	}

	@Override
	public void resize(int x, int width, int height) {
		volume.setBounds(500, 500, 300, 50);
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		volume.mouseMoved(x, y);
		
	}

	@Override
	public void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}