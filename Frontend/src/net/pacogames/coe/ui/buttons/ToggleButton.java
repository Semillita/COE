package net.pacogames.coe.ui.buttons;

import com.badlogic.gdx.graphics.Texture;

public class ToggleButton implements Button{
	
	private Texture texture;
	
	protected int x, y;
	protected int width, height;
	
	protected boolean hovered = false;
	protected boolean pressed = false;
}
