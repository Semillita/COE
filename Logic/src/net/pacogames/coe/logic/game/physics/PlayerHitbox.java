package net.pacogames.coe.logic.game.physics;

import net.pacogames.coe.logic.game.Player;
import net.pacogames.coe.logic.utils.Point;

/**The players' implementation of {@link Hitbox}*/
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