package net.pacogames.coe.game;

import com.badlogic.gdx.math.Vector2;

public class PlayerFrameData {

	public final int x, y;
	public final Vector2 momentum;
	
	public PlayerFrameData(int x, int y, Vector2 momentum) {
		this.x = x;
		this.y = y;
		this.momentum = momentum;
	}
}