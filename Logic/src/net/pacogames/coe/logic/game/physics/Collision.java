package net.pacogames.coe.logic.game.physics;

public class Collision {
	public final float time;
	public Event event;

	public Collision(float f, Event event) {
		this.time = f;
		this.event = event;
	}

	public static enum Event {
		ARENAX, ARENAY, P1ARENAX, P1ARENAY, P2ARENAX, P2ARENAY, P1P2X, P1P2Y;
	}

}