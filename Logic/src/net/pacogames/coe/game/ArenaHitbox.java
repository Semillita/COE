package net.pacogames.coe.game;

public class ArenaHitbox implements Hitbox {

	private static final float X = 0, Y = 0;
	private static final float WIDTH = 2400, HEIGHT = 1400;
	
	@Override
	public float top() {
		return Y + HEIGHT;
	}

	@Override
	public float bottom() {
		return Y;
	}

	@Override
	public float right() {
		return X + WIDTH;
	}

	@Override
	public float left() {
		return X;
	}
	
}