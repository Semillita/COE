package net.pacogames.coe.logic.game;

public class InputEvent {

	public Key key;
	public boolean pressed;
	
	public InputEvent(Key key, boolean pressed) {
		this.key = key;
		this.pressed = pressed;
	}
}
