package net.pacogames.coe.logic.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.pacogames.coe.logic.utils.Point;
import net.pacogames.coe.logic.utils.Vector2;

public class Match {

	Map<Long, Frame> frames;
	Map<Long, List<InputEvent>> p1inputQueue;
	Map<Long, List<InputEvent>> p2inputQueue;

	// Player player1, player2;

	public Match() {
		frames = new HashMap<>();
		p1inputQueue = new HashMap<>();
		p2inputQueue = new HashMap<>();
	}

	private Frame createFrame(long timeStamp) {
		Frame lastFrame = frames.get(timeStamp - Frame.LENGTH);

		PlayerFrameData p1 = lastFrame.player1data;
		PlayerFrameData p2 = lastFrame.player2data;

		Map<Key, Boolean> p1input = p1.input;
		Map<Key, Boolean> p2input = p1.input;

		Vector2 p1movement = (p1.stun == 0) ? getMovement(p1input) : new Vector2(0, 0);
		Vector2 p2movement = (p2.stun == 0) ? getMovement(p2input) : new Vector2(0, 0);

		// Collision loop
		float timeElapsed = 0;

		Point p1pos = new Point(p1.pos.x, p1.pos.y);
		Point p2pos = new Point(p2.pos.x, p2.pos.y);

		Vector2 p1momentum = new Vector2(p1.momentum.x, p1.momentum.y);
		Vector2 p2momentum = new Vector2(p2.momentum.x, p2.momentum.y);

		while (true) {
			float timeLeft = Frame.LENGTH / 1000 - timeElapsed;
			Vector2 p1dis = new Vector2(0, 0);
			Vector2 p2dis = new Vector2(0, 0);

			if ((p1momentum.x >= 0 && p1movement.x >= 0) || (p1momentum.x <= 0 && p1movement.x <= 0)) {
				p1dis.x = p1momentum.x * timeLeft + p1movement.x * Player.SPEED * timeLeft;
			}

			if ((p1momentum.y >= 0 && p1movement.y >= 0) || (p1momentum.y <= 0 && p1movement.y <= 0)) {
				p1dis.y = p1momentum.y * timeLeft + p1movement.y * Player.SPEED * timeLeft;
			}

			if ((p2momentum.x >= 0 && p2movement.x >= 0) || (p2momentum.x <= 0 && p2movement.x <= 0)) {
				p2dis.x = p2momentum.x * timeLeft + p2movement.x * Player.SPEED * timeLeft;
			}

			if ((p2momentum.y >= 0 && p2movement.y >= 0) || (p2momentum.y <= 0 && p2movement.y <= 0)) {
				p2dis.y = p2momentum.y * timeLeft + p2movement.y * Player.SPEED * timeLeft;
			}

			Collision c = getNextCollision(p1pos, p2pos, p1dis, p2dis, timeLeft);
			if (c == null) {
				applyIntervalChanges(p1pos, p2pos, p1dis, p2dis, timeLeft);
				break;
			} else {
				switch (c.event) {
				case P1ARENAX:
					p1momentum.x *= -0.9;
					break;
				case P1ARENAY:
					p1momentum.y *= -0.9;
					break;
				case P2ARENAX:
					p2momentum.x *= -0.9;
					break;
				case P2ARENAY:
					p2momentum.y *= -0.9;
					break;
				case P1P2X:
					float x1 = p2momentum.x * 0.8f;
					float x2 = p1momentum.x * 0.8f;
					p1momentum.x = x1;
					p2momentum.y = x2;
					break;
				case P1P2Y:
					float y1 = p2momentum.x * 0.8f;
					float y2 = p1momentum.x * 0.8f;
					p1momentum.x = y1;
					p2momentum.y = y2;
					break;	
				}
				
				timeElapsed += c.time;
				applyIntervalChanges(p1pos, p2pos, p1dis, p2dis, c.time);
			}
		}
		
		//Apply momentum retardation here

		//Create frame object
		Map<Key, Boolean> p1newInput = applyInputEvents(p1input, p1inputQueue.get(timeStamp));
		PlayerFrameData player1data = new PlayerFrameData(p1pos, p1momentum, p1newInput, p1.stun - 1, 0);
		
		Map<Key, Boolean> p2newInput = applyInputEvents(p2input, p2inputQueue.get(timeStamp));
		PlayerFrameData player2data = new PlayerFrameData(p2pos, p2momentum, p2newInput, p2.stun - 1, 0);
		
		return new Frame(timeStamp + Frame.LENGTH, player1data, player2data);
	}

	private Map<Key, Boolean> applyInputEvents(Map<Key, Boolean> previous, List<InputEvent> inputs) {
		Map<Key, Boolean> frameInput = new HashMap<>();
		frameInput.putAll(previous);
		for(InputEvent e : inputs) {
			frameInput.put(e.key, e.pressed);
		}
		return frameInput;
	}
	
	private void applyIntervalChanges(Point p1pos, Point p2pos, Vector2 p1dis, Vector2 p2dis, float length) {
		p1pos.add(p1dis);
		p2pos.add(p2dis);
	}

	private Vector2 getMovement(Map<Key, Boolean> inputs) {
		int movementX = (inputs.get(Key.RIGHT) ? 1 : 0) - (inputs.get(Key.LEFT) ? 1 : 0);
		int movementY = (inputs.get(Key.UP) ? 1 : 0) - (inputs.get(Key.DOWN) ? 1 : 0);
		return new Vector2(movementX, movementY);
	}

	private Collision getNextCollision(Point p1pos, Point p2pos, Vector2 p1dis, Vector2 p2dis, float time) {
		Hitbox p1box = new PlayerHitbox(p1pos);
		Hitbox p2box = new PlayerHitbox(p2pos);

		Collision p1arena = getArenaCollision(p1box, p1dis, time);
		Collision p2arena = getArenaCollision(p2box, p2dis, time);
		Collision p1p2 = getPlayerCollision(p1box, p2box, p1dis, p2dis, time);

		if (p1arena == null && p2arena == null && p1p2 == null) {
			return null;
		}

		if (p1arena != null && (p2arena == null || p1arena.time < p2arena.time)
				&& (p1p2 == null || p1arena.time < p1p2.time)) {
			return new Collision(p1arena.time,
					(p1arena.event == Collision.Event.ARENAX) ? Collision.Event.P1ARENAX : Collision.Event.P1ARENAY);
		} else if (p2arena != null && (p1p2 == null || p2arena.time < p1p2.time)) {
			return new Collision(p2arena.time,
					(p2arena.event == Collision.Event.ARENAX) ? Collision.Event.P2ARENAX : Collision.Event.P2ARENAY);
		} else {
			return p1p2;
		}
	}

	private Collision getPlayerCollision(Hitbox p1box, Hitbox p2box, Vector2 p1dis, Vector2 p2dis, float time) {
		return null;
	}

	private Collision getArenaCollision(Hitbox box, Vector2 dis, float time) {
		Hitbox arena = new ArenaHitbox();
		float frameLength = Frame.LENGTH / 1000;
		float timeLeft = frameLength - time;
		float top = box.top() + dis.y;
		float bottom = box.bottom() + dis.y;
		float right = box.right() + dis.x;
		float left = box.left() + dis.x;

		if (!(top > arena.top() || bottom < arena.bottom() || right > arena.right() || left < arena.left())) {
			return null;
		}

		Collision c;

		if (top > arena.top()) {
			if (right > arena.right()) {
				if (dis.y / dis.x < (top - arena.top()) / (right - arena.right())) {
					c = new Collision(timeLeft * (arena.top() - box.top()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.right() - box.right()) / dis.x, Collision.Event.ARENAX);
				}
			} else if (left < arena.left()) {
				if (dis.y / dis.x > (top - arena.top()) / (left - arena.left())) {
					c = new Collision(timeLeft * (arena.top() - box.top()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.left() - box.left()) / dis.x, Collision.Event.ARENAX);
				}
			} else {
				c = new Collision(timeLeft * (arena.top() - box.top()) / dis.y, Collision.Event.ARENAY);
			}
		} else if (bottom < arena.bottom()) {
			if (right > arena.right()) {
				if (dis.y / dis.x > (bottom - arena.bottom()) / (right - arena.right())) {
					c = new Collision(timeLeft * (arena.bottom() - box.bottom()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.right() - box.right()) / dis.x, Collision.Event.ARENAX);
				}
			} else if (left < arena.left()) {
				if (dis.y / dis.x < (bottom - arena.bottom()) / (left - arena.left())) {
					c = new Collision(timeLeft * (arena.bottom() - box.bottom()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.left() - box.left()) / dis.x, Collision.Event.ARENAX);
				}
			} else {
				c = new Collision(timeLeft * (arena.bottom() - box.bottom()) / dis.y, Collision.Event.ARENAY);
			}
		} else {
			if (right > arena.right()) {
				c = new Collision(timeLeft * (arena.right() - box.right()) / dis.x, Collision.Event.ARENAX);
			} else {
				c = new Collision(timeLeft * (arena.left() - box.left()) / dis.x, Collision.Event.ARENAX);
			}
		}

		return c;
	}

	private static final class Collision {
		public final float time;
		public final Event event;

		public Collision(float time, Event event) {
			this.time = time;
			this.event = event;
		}

		public static enum Event {
			ARENAX, ARENAY, P1ARENAX, P1ARENAY, P2ARENAX, P2ARENAY, P1P2X, P1P2Y;
		}
	}

}