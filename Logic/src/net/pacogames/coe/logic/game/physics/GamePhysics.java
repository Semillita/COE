package net.pacogames.coe.logic.game.physics;

import java.util.Map;

import net.pacogames.coe.logic.game.input.Key;
import net.pacogames.coe.logic.utils.Point;
import net.pacogames.coe.logic.utils.Vector2;

import static net.pacogames.coe.logic.game.physics.Collision.Event;

public class GamePhysics {
	
	public Collision getNextCollision(Point p1pos, Point p2pos, Vector2 p1distance, Vector2 p2distance, 
			Vector2 p1momentum, Vector2 p2momentum, long timeLeft) {
		Hitbox p1box = new PlayerHitbox(p1pos);
		Hitbox p2box = new PlayerHitbox(p2pos);
		
		Collision p1arena = getArenaCollision(p1box, p1pos, p1distance, timeLeft);
		Collision p2arena = getArenaCollision(p2box, p2pos, p2distance, timeLeft);
		Collision p1p2 = getPlayerCollision(p1box, p2box, p1pos, p2pos, p1distance, p2distance, timeLeft);

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
	
	private Collision getPlayerCollision(Hitbox source1, Hitbox source2, Point p1pos, Point p2pos, Vector2 p1distance, Vector2 p2distance, float timeLeft) {
		Hitbox target1 = new PlayerHitbox(new Point(p1pos.x + p1distance.x, p1pos.y + p1distance.y));
		Hitbox target2 = new PlayerHitbox(new Point(p2pos.x + p2distance.x, p2pos.y + p2distance.y));
		
		// Kollar ifall p1 höger kolliderar med p2 vänster
		var topBottomOverlap = (source1.top() <= source2.bottom() && target1.top() > target2.bottom());
		var rightLeftOverlap = (source1.right() <= source2.left() && target1.right() > target2.left());
		var bottomTopOverlap = (source1.bottom() >= source2.top() && target1.bottom() < target2.top());
		var leftRightOverlap = (source1.left() >= source2.right() && target1.left() < target2.right());
		
		Collision c = null;
		
		if(topBottomOverlap) {
			var combined = p1distance.y - p2distance.y;
			var distanceBetween = source2.bottom() - source1.top();
			var slice = distanceBetween / combined;
			
			var left1 = source1.left() + p1distance.x * slice;
			var right1 = source1.right() + p1distance.x * slice;
			var left2 = source2.left() + p2distance.x * slice;
			var right2 = source2.right() + p2distance.x * slice;
			
			if((left1 >= left2 && left1 < right2) || (right1 > left2 && right1 <= right2)) {
				c = new Collision(slice * timeLeft, Event.P1P2Y);
			}
			
			//c = new Collision(time, Event.P1P2Y);
		}
		
		if(rightLeftOverlap) {
			var combined = p1distance.x - p2distance.x;
			var distanceBetween = source2.left() - source1.right();
			var slice = distanceBetween / combined;
			
			var bottom1 = source1.bottom() + p1distance.y * slice;
			var top1 = source1.top() + p1distance.y * slice;
			var bottom2 = source2.bottom() + p2distance.y * slice;
			var top2 = source2.top() + p2distance.y * slice;
			
			if((bottom1 >= bottom2 && bottom1 < top2) || (top1 > bottom2 && top1 <= top2)) {
				if(c == null || slice * timeLeft < c.time) {
					c = new Collision(slice * timeLeft, Event.P1P2X);
				}
			}
		}
		
		if(bottomTopOverlap) {
			var combined = p1distance.y - p2distance.y;
			var distanceBetween = source2.top() - source1.bottom();
			var slice = distanceBetween / combined;
			
			var left1 = source1.left() + p1distance.x * slice;
			var right1 = source1.right() + p1distance.x * slice;
			var left2 = source2.left() + p2distance.x * slice;
			var right2 = source2.right() + p2distance.x * slice;
			
			if((left1 >= left2 && left1 < right2) || (right1 > left2 && right1 <= right2)) {
				if(c == null || slice * timeLeft < c.time) {
					c = new Collision(slice * timeLeft, Event.P1P2Y);
				}
			}
		}
		
		if(leftRightOverlap) {
			var combined = p1distance.x - p2distance.x;
			var distanceBetween = source2.right() - source1.left();
			var slice = distanceBetween / combined;
			
			var bottom1 = source1.bottom() + p1distance.y * slice;
			var top1 = source1.top() + p1distance.y * slice;
			var bottom2 = source2.bottom() + p2distance.y * slice;
			var top2 = source2.top() + p2distance.y * slice;
			
			if((bottom1 >= bottom2 && bottom1 < top2) || (top1 > bottom2 && top1 <= top2)) {
				if(c == null || slice * timeLeft < c.time) {
					c = new Collision(slice * timeLeft, Event.P1P2X);
				}
			}
		}
		
		return c;
	}
	
	private Collision getArenaCollision(Hitbox source, Point pPos, Vector2 distance, long timeLeft) {
		Hitbox target = new PlayerHitbox(new Point(pPos.x + distance.x, pPos.y + distance.y));
		Hitbox arena = new ArenaHitbox();
		
		var topOverlap = target.top() > arena.top();
		var rightOverlap = target.right() > arena.right();
		var bottomOverlap = target.bottom() < arena.bottom();
		var leftOverlap = target.left() < arena.left();
		
		if(!(topOverlap || rightOverlap || bottomOverlap || leftOverlap)) {
			return null;
		}
		
		var pathDerivative = distance.y / distance.x;
		Collision c = null;
		
		if(topOverlap) {
			if(rightOverlap) {
				var cornerDerivative = (arena.top() - source.top()) / (arena.right() - source.right());
				c = (pathDerivative > cornerDerivative) ? new Collision(timeLeft * (arena.top() - source.top()) / distance.y, Event.ARENAY) :
					new Collision(timeLeft * (arena.right() - source.right()) / distance.x, Event.ARENAX);
			} else if(leftOverlap) {
				var cornerDerivative = (arena.top() - source.top()) / (arena.left() - source.left());
				c = (pathDerivative < cornerDerivative) ? new Collision(timeLeft * (arena.top() - source.top()) / distance.y, Event.ARENAY) :
					new Collision(timeLeft * (arena.left() - source.left()) / distance.x, Event.ARENAX);
			} else {
				c = new Collision(timeLeft * (arena.top() - source.top()) / distance.y, Event.ARENAY);
			}
		}
		
		if(bottomOverlap) {
			if(rightOverlap) {
				var cornerDerivative = (arena.bottom() - source.bottom()) / (arena.right() - source.right());
				c = (pathDerivative < cornerDerivative) ? new Collision(timeLeft * (arena.bottom() - source.bottom()) / distance.y, Event.ARENAY) :
					new Collision(timeLeft * (arena.right() - source.right()) / distance.x, Event.ARENAX);
			} else if(leftOverlap) {
				var cornerDerivative = (arena.bottom() - source.bottom()) / (arena.left() - source.left());
				c = (pathDerivative > cornerDerivative) ? new Collision(timeLeft * (arena.bottom() - source.bottom()) / distance.y, Event.ARENAY) :
					new Collision(timeLeft * (arena.left() - source.left()) / distance.x, Event.ARENAX);
			} else {
				c = new Collision(timeLeft * (arena.bottom() - source.bottom()) / distance.y, Event.ARENAY);
			}
		}
		
		if(rightOverlap) {
			c = new Collision(timeLeft * (arena.right() - source.right()) / distance.x, Event.ARENAX);
		}
		
		if(leftOverlap) {
			c = new Collision(timeLeft * (arena.left() - source.left()) / distance.x, Event.ARENAX);
		}
		
		return c;
	}
	
	public Vector2 getMovement(Map<Key, Boolean> inputs) {
		int movementX = (inputs.get(Key.RIGHT) ? 1 : 0) - (inputs.get(Key.LEFT) ? 1 : 0);
		int movementY = (inputs.get(Key.UP) ? 1 : 0) - (inputs.get(Key.DOWN) ? 1 : 0);
		return new Vector2(movementX, movementY);
	}
}
