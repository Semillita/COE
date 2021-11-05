package net.pacogames.coe.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Game {

	Map<Long, Frame> frames;
	Map<Long, List<InputEvent>> frameInputQueue;
	
	//Player player1, player2;
	
	public Game() {
		frames = new HashMap<Long, Frame>();
	}
	
	private Frame createFrame(long timeStamp) {
		Frame lastFrame = frames.get(timeStamp - Frame.LENGTH);
		
		PlayerFrameData p1 = lastFrame.player1data;
		PlayerFrameData p2 = lastFrame.player2data;
		
		//Calculate new momentum
		Map<Key, Boolean> p1input = p1.input;
		Map<Key, Boolean> p2input = p1.input;
		
		Vector2 p1movement = (p1.stun == 0) ? getMovement(p1input) : new Vector2(0, 0);
		Vector2 p2movement = (p2.stun == 0) ? getMovement(p2input) : new Vector2(0, 0);
		
		Point p1pos = new Point(p1.x, p1.y);
		Point p2pos = new Point(p2.x, p2.y);
		
		float time = 0;
		
		//Collision loop
		for(Collision c = getNextCollision(p1pos, p2pos, null, null, time); c != null; c = getNextCollision(p1pos, p2pos, null, null, time)) {
			
		}
		
		return null;
	}
	
	private Vector2 getMovement(Map<Key, Boolean> inputs) {
		int movementX = (inputs.get(Key.RIGHT) ? 1 : 0) - (inputs.get(Key.LEFT) ? 1: 0);
		int movementY = (inputs.get(Key.UP) ? 1 : 0) - (inputs.get(Key.DOWN) ? 1 : 0);
		return new Vector2(movementX, movementY);
	}
	
	private Collision getNextCollision(Point p1pos, Point p2pos, Vector2 p1dis, Vector2 p2dis, float time) {
		Hitbox p1box = new PlayerHitbox(p1pos);
		Hitbox p2box = new PlayerHitbox(p2pos);
		
		Collision p1arena = getArenaCollision(p1box, p1dis, time);
		Collision p2arena = getArenaCollision(p2box, p2dis, time);
		Collision p1p2 = getPlayerCollision(p1box, p2box, p1dis, p2dis, time);
		
		if(p1arena == null && p2arena == null && p1p2 == null) {
			return null;
		}
		
		if(p1arena != null && (p2arena == null || p1arena.time < p2arena.time) && (p1p2 == null || p1arena.time < p1p2.time)) {
			return new Collision(p1arena.time, (p1arena.event == Collision.Event.ARENAX) ? Collision.Event.P1ARENAX : Collision.Event.P1ARENAY);
		} else if(p2arena != null && (p1p2 == null || p2arena.time < p1p2.time)) {
			return new Collision(p2arena.time, (p2arena.event == Collision.Event.ARENAX) ? Collision.Event.P2ARENAX : Collision.Event.P2ARENAY);
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
		
		if(!(top > arena.top() || bottom < arena.bottom() || right > arena.right() || left < arena.left())) {
			return null;
		}
		
		Collision c;
		
		if(top > arena.top()) {
			if(right > arena.right()) {
				if(dis.y / dis.x < (top - arena.top()) / (right - arena.right())) {
					c = new Collision(timeLeft * (arena.top() - box.top()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.right() - box.right()) / dis.x, Collision.Event.ARENAX);
				}
			} else if(left < arena.left()) {
				if(dis.y / dis.x > (top - arena.top()) / (left - arena.left())) {
					c = new Collision(timeLeft * (arena.top() - box.top()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.left() - box.left()) / dis.x, Collision.Event.ARENAX);
				}
			} else {
				c = new Collision(timeLeft * (arena.top() - box.top()) / dis.y, Collision.Event.ARENAY);
			}
		} else if(bottom < arena.bottom()) {
			if(right > arena.right()) {
				if(dis.y / dis.x > (bottom - arena.bottom()) / (right - arena.right())) {
					c = new Collision(timeLeft * (arena.bottom() - box.bottom()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.right() - box.right()) / dis.x, Collision.Event.ARENAX);
				}
			} else if(left < arena.left()) {
				if(dis.y / dis.x < (bottom - arena.bottom()) / (left - arena.left())) {
					c = new Collision(timeLeft * (arena.bottom() - box.bottom()) / dis.y, Collision.Event.ARENAY);
				} else {
					c = new Collision(timeLeft * (arena.left() - box.left()) / dis.x, Collision.Event.ARENAX);
				}
			} else {
				c = new Collision(timeLeft * (arena.bottom() - box.bottom()) / dis.y, Collision.Event.ARENAY);
			}
		} else {
			if(right > arena.right()) {
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