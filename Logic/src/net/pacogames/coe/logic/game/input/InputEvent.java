package net.pacogames.coe.logic.game.input;

/**Data about a key input event*/
public class InputEvent {

	public Key key;
	public boolean pressed;
	
	public InputEvent(Key key, boolean pressed) {
		this.key = key;
		this.pressed = pressed;
	}
}
