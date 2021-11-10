package net.pacogames.coe.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface Button {
	
	public void hover(boolean hovered);
	public void press();
	public void release();
	public void render(Batch batch);
	public Rectangle getBox();
	public boolean isHovered();
	
	// Data types //
	
	/*
	 * private Texture texture; private boolean hover;
	 * 
	 * // Methods //
	 * 
	 * public Button(Texture texture) { this.texture = texture; //super(text, skin);
	 * addListener(new InputListener() {
	 * 
	 * @Override public void hover(InputEvent event, float x, float y, int pointer,
	 * Actor fromActor) { hover = true; }
	 * 
	 * @Override public void exit(InputEvent event, float x, float y, int pointer,
	 * Actor toActor) { hover = false; } }); }
	 * 
	 * public void render(Batch batch, float x, float y) { batch.draw(texture, x,
	 * y); }
	 * 
	 * public Color getColor() { if (!hover) return getColor(); //super.getColor
	 * ifall en class extendar fr�n button else return Color.WHITE; //Jag h�rdkodar
	 * f�rgen �n s� l�nge, �ndrar opacity sen. }
	 */	
	
	/* Estos funktioner �r n�dv�ndiga
	
	public void isHover() {
		
	}
	
	public void isPressed() {
		
	}
	
	public void isReleased() {
		
	}
	
	public Color getColor() {
	
	}
	
	*/
}
