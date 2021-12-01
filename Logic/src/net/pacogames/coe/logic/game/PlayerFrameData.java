package net.pacogames.coe.logic.game;

import java.util.Map;

import net.pacogames.coe.logic.utils.Point;
import net.pacogames.coe.logic.utils.Vector2;

public class PlayerFrameData {

	public final Point pos;
	public final Vector2 momentum;
	public Map<Key, Boolean> input;
	public final int stun;
	public final int damage;
	
	public PlayerFrameData(Point pos, Vector2 momentum, Map<Key, Boolean> inputs, int stun, int damage) {
		this.pos = pos;
		this.momentum = momentum;
		this.stun = stun;
		this.damage = damage;
	}
}