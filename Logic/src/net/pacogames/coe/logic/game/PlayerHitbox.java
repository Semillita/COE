package net.pacogames.coe.logic.game;

import net.pacogames.coe.logic.game.physics.Hitbox;
import net.pacogames.coe.logic.utils.Point;

public class PlayerHitbox implements Hitbox {

	private final float x, y;
	
	public PlayerHitbox(Point p) {
		this(p.x, p.y);
	}
	
	public PlayerHitbox(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public float top() {
		return y + Player.HEIGHT / 2;
	}

	@Override
	public float bottom() {
		return y - Player.HEIGHT / 2;
	}

	@Override
	public float right() {
		return x + Player.WIDTH / 2;
	}

	@Override
	public float left() {
		return x - Player.WIDTH / 2;
	}
	
}