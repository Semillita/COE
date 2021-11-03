package net.pacogames.coe.game;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public class PlayerFrameData {

	public final int x, y;
	public final Vector2 momentum;
	public Map<Key, Boolean> input;
	public final int stun;
	public final int damage;
	
	public PlayerFrameData(int x, int y, Vector2 momentum, Map<Key, Boolean> input, int stun, int damage) {
		this.x = x;
		this.y = y;
		this.momentum = momentum;
		this.stun = stun;
		this.damage = damage;
	}
}