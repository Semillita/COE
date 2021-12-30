package net.pacogames.coe.logic.game.input;

public class InputEvent {

	public Key key;
	public boolean pressed;
	
	public InputEvent(Key key, boolean pressed) {
		this.key = key;
		this.pressed = pressed;
	}
}
