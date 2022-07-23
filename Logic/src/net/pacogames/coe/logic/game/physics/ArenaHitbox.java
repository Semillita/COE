package net.pacogames.coe.logic.game.physics;

/**The arena's implementation of {@link Hitbox}*/
public class ArenaHitbox implements Hitbox {

	public static final float KILL_ZONE = 300;
	private static final float X = 0, Y = 0;
	private static final float WIDTH = 2360, HEIGHT = 1360;
	
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