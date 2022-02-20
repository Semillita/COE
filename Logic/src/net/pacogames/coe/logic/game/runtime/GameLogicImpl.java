package net.pacogames.coe.logic.game.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.pacogames.coe.logic.game.physics.ArenaHitbox;
import net.pacogames.coe.logic.game.physics.Collision;
import net.pacogames.coe.logic.game.Player;
import net.pacogames.coe.logic.game.input.InputEvent;
import net.pacogames.coe.logic.game.input.Key;
import net.pacogames.coe.logic.game.physics.GamePhysics;
import net.pacogames.coe.logic.utils.Logger;
import net.pacogames.coe.logic.utils.Point;
import net.pacogames.coe.logic.utils.Vector2;

public class GameLogicImpl implements GameLogic {

	private Map<Long, Frame> frames;
	private Map<Long, List<InputEvent>> p1inputQueue;
	private Map<Long, List<InputEvent>> p2inputQueue;

	public GameTimer gameTimer;
	private ExecutorService frameLoader;
	private GamePhysics physics;

	private long lastFramestamp;

	public GameLogicImpl() {
		frames = new HashMap<>();
		p1inputQueue = new HashMap<>();
		p2inputQueue = new HashMap<>();

		gameTimer = new GameTimer();
		frameLoader = Executors.newSingleThreadExecutor();
		physics = new GamePhysics();
	}

	@Override
	public void startGame() {
		createFirstFrame();
		gameTimer.start();
	}

	@Override
	public Frame getCurrentFrame() {
		var timeElapsed = gameTimer.getTimeElapsed(System.nanoTime());
		var framestamp = getClosestFramestamp(timeElapsed, false);
		while (!frames.containsKey(framestamp)) {
		}
		return frames.containsKey(framestamp) ? frames.get(framestamp) : null;
	}

	@Override
	public void loadAdvanceFrames() {
		frameLoader.execute(() -> {
			var timeElapsed = gameTimer.getTimeElapsed(System.nanoTime());
			var currentFramestamp = getClosestFramestamp(timeElapsed, false);

			for (long framestamp = lastFramestamp; framestamp <= currentFramestamp + 4; framestamp++) {
				if (!frames.containsKey(framestamp)) {
					frames.put(framestamp, createFrame(framestamp));
				}
			}
		});
	}

	@Override
	public void registerInput(int playerID, InputEvent event, long timestamp) {
		var timeElapsed = gameTimer.getTimeElapsed(timestamp);
		var framestamp = getClosestFramestamp(timeElapsed, true);
		var queue = (playerID == 1) ? p1inputQueue : p2inputQueue;

		if (queue.containsKey(framestamp)) {
			queue.get(framestamp).add(event);
		} else {
			List<InputEvent> events = new ArrayList<>();
			events.add(event);
			queue.put(framestamp, events);
		}

		frameLoader.execute(() -> {
			for (long fs = framestamp; fs <= framestamp + 5; fs++) {
				frames.put(fs, createFrame(fs));
			}
		});
	}

	private Frame createFrame(long framestamp) {
		var lastFrame = frames.get(framestamp - 1);
		var p1data = lastFrame.player1data;
		var p2data = lastFrame.player2data;

		var p1movement = (p1data.stun <= 0) ? physics.getMovement(p1data.input) : new Vector2(0, 0);
		var p2movement = (p2data.stun <= 0) ? physics.getMovement(p2data.input) : new Vector2(0, 0);

		var p1pos = p1data.pos.clone();
		var p2pos = p2data.pos.clone();

		var p1momentum = p1data.momentum.clone();
		var p2momentum = p2data.momentum.clone();

		var p1newInputs = p1inputQueue.get(framestamp);
		var p2newInputs = p2inputQueue.get(framestamp);

		float distanceBetweenPlayers = (float) Math
				.sqrt(Math.pow(p1pos.x - p2pos.x, 2) + Math.pow(p1pos.y - p2pos.y, 2));

		if (p1newInputs != null) {
			for (var event : p1newInputs) {
				if (event.key == Key.ACTION && event.pressed) {
					if (distanceBetweenPlayers <= 350) {
						applyAttack(p1pos, p2pos, p2momentum);
					}
				}
			}
		}

		if (p2newInputs != null) {
			for (var event : p2newInputs) {
				if (event.key == Key.ACTION && event.pressed) {
					if (distanceBetweenPlayers <= 350) {
						applyAttack(p2pos, p1pos, p1momentum);
					}
				}
			}
		}

		long frameTimeElapsed = 0;
		boolean p1freezeX = false, p1freezeY = false, p2freezeX = false, p2freezeY = false;
		boolean playersStuckX = false, playersStuckY = false;
		boolean isGameOver = false;

		Collision lastCollision = null;
		boolean lastCollisionCausedByMomentum = false;

		while (true) {
			final long timeLeft = Frame.LENGTH - frameTimeElapsed;
			final float secondsLeft = timeLeft / 1_000_000_000f;

			final float speed = Player.SPEED;

			final var p1distance = new Vector2(p1momentum.x * secondsLeft, p1momentum.y * secondsLeft);
			final var p2distance = new Vector2(p2momentum.x * secondsLeft, p2momentum.y * secondsLeft);

			applyWalkingToDistance(p1movement, p1momentum, p1distance, p1freezeX, p1freezeY, secondsLeft);
			applyWalkingToDistance(p2movement, p2momentum, p2distance, p2freezeX, p2freezeY, secondsLeft);

			if (lastCollision != null) {
				switch (lastCollision.event) {
				case P1P2X:
					if (!lastCollisionCausedByMomentum) {
						var combinedDistanceX = p1distance.x + p2distance.x;
						p1distance.x = combinedDistanceX / 2;
						p2distance.x = combinedDistanceX / 2;
					}
					break;
				case P1P2Y:
					if (!lastCollisionCausedByMomentum) {
						var combinedDistanceY = p1distance.y + p2distance.y;
						p1distance.y = combinedDistanceY / 2;
						p2distance.y = combinedDistanceY / 2;
					}
					break;
				default:
					break;
				}
			}

			Collision collision = physics.getNextCollision(p1pos, p2pos, p1distance, p2distance, p1momentum, p2momentum,
					timeLeft);
			if (collision == null) {
				applyIntervalChanges(p1pos, p2pos, p1distance, p2distance);

				final var autoRetardation = Player.RETARDATION * secondsLeft;

				final var p1manualRetardation = getManualRetardation(p1movement, p1momentum, secondsLeft,
						Player.RETARDATION);
				final var p2manualRetardation = getManualRetardation(p2movement, p2momentum, secondsLeft,
						Player.RETARDATION);

				applyRetardation(p1momentum, autoRetardation, p1manualRetardation);
				applyRetardation(p2momentum, autoRetardation, p2manualRetardation);

				if (shouldPlayerDie(p1pos) || shouldPlayerDie(p2pos)) {
					isGameOver = true;
				}
				break;
			} else {
				boolean collisionCausedByMomentum = physics.isCollisionCausedByMomentum(collision, p1momentum,
						p2momentum, p1pos, p2pos);

				final var seconds = collision.time / 1_000_000_000;

				final var p1d = new Vector2(p1distance.x * seconds, p1distance.y * seconds);
				final var p2d = new Vector2(p2distance.x * seconds, p2distance.y * seconds);

				applyIntervalChanges(p1pos, p2pos, p1d, p2d);

				final var autoRetardation = Player.RETARDATION * seconds;

				final var p1manualRetardation = getManualRetardation(p1movement, p1momentum, seconds,
						Player.MANUAL_RETARDATION);
				final var p2manualRetardation = getManualRetardation(p2movement, p2momentum, seconds,
						Player.MANUAL_RETARDATION);

				applyRetardation(p1momentum, autoRetardation, p1manualRetardation);
				applyRetardation(p2momentum, autoRetardation, p2manualRetardation);

				switch (collision.event) {
				case P1ARENAX:
					if (!collisionCausedByMomentum) {
						p1freezeX = true;
						if (playersStuckX) {
							p2freezeX = true;
						}
					} else {
						playersStuckX = false;
					}

					p1momentum.x *= -0.9;
					break;
				case P1ARENAY:
					if (!collisionCausedByMomentum) {
						p1freezeY = true;
						if (playersStuckY) {
							p2freezeY = true;
						}
					} else {
						playersStuckY = false;
					}

					p1momentum.y *= -0.9;
					break;
				case P2ARENAX:
					if (!collisionCausedByMomentum) {
						p2freezeX = true;
						if (playersStuckX) {
							p1freezeX = true;
						}
					} else {
						playersStuckX = false;
					}

					p2momentum.x *= -0.9;
					break;
				case P2ARENAY:
					if (!collisionCausedByMomentum) {
						p2freezeY = true;
						if (playersStuckY) {
							p1freezeY = true;
						}
					} else {
						playersStuckY = false;
					}

					p2momentum.y *= -0.9;
					break;
				case P1P2X:
					float x1 = p2momentum.x * 0.8f;
					float x2 = p1momentum.x * 0.8f;
					p1momentum.x = x1;
					p2momentum.x = x2;

					playersStuckX = true;
					break;
				case P1P2Y:
					float y1 = p2momentum.y * 0.8f;
					float y2 = p1momentum.y * 0.8f;
					p1momentum.y = y1;
					p2momentum.y = y2;

					playersStuckY = true;
					break;
				}

				frameTimeElapsed += collision.time;
				lastCollision = collision;

				if (shouldPlayerDie(p1pos) || shouldPlayerDie(p2pos)) {
					isGameOver = true;
				}
			}
		}
		Map<Key, Boolean> p1newInput = applyInputEvents(p1data.input, p1inputQueue.get(framestamp));
		PlayerFrameData player1data = new PlayerFrameData(p1pos, p1momentum, p1newInput, Math.max(p1data.stun - 1, 0),
				0);

		Map<Key, Boolean> p2newInput = applyInputEvents(p2data.input, p2inputQueue.get(framestamp));
		PlayerFrameData player2data = new PlayerFrameData(p2pos, p2momentum, p2newInput, Math.max(p2data.stun - 1, 0),
				0);

		lastFramestamp = framestamp;

		return new Frame(framestamp, player1data, player2data, lastFrame.gameOver || isGameOver);
	}

	/**
	 * Applies the distance created by a player walking to that player's total
	 * distance in each axis respectively, provided that the player is walking in
	 * the same direction as any potential momentum the player has in that axis.
	 * 
	 * @param movement    a vector representing the player's movement input with
	 *                    both the x and the y value being either -1, 0 or 1 based
	 *                    on positive/negative direction
	 * @param momentum    a vector containing the player's momentum in the x and y
	 *                    axes
	 * @param distance    a vector containing the distance a player aims to move
	 *                    during the rest of the frame, no collisions accounted for
	 * @param freezeX     whether the player's walking ability is frozen in the x
	 *                    axis
	 * @param freezeY     whether the player's walking ability is frozen in the y
	 *                    axis
	 * @param secondsLeft the amount of seconds left of the frame
	 */
	private void applyWalkingToDistance(Vector2 movement, Vector2 momentum, Vector2 distance, boolean freezeX,
			boolean freezeY, float secondsLeft) {
		if (((movement.x >= 0 && momentum.x >= 0) || (movement.x <= 0 && momentum.x <= 0)) && !freezeX) {
			distance.x += movement.x * Player.SPEED * secondsLeft;
		}

		if (((movement.y >= 0 && momentum.y >= 0) || (movement.y <= 0 && momentum.y <= 0)) && !freezeY) {
			distance.y += movement.y * Player.SPEED * secondsLeft;
		}
	}

	/**
	 * Applies a push attack to a player.
	 * 
	 * @param attackerPos      the position of the attacking player
	 * @param receiverPos      the position of the receiving player
	 * @param receiverMomentum the momentum of the receiving player
	 */
	private void applyAttack(Point attackerPos, Point receiverPos, Vector2 receiverMomentum) {
		float absDistanceX = Math.abs(attackerPos.x - receiverPos.x);
		float absDistanceY = Math.abs(attackerPos.y - receiverPos.y);

		float totalDistance = absDistanceX + absDistanceY;

		float xPart = absDistanceX / totalDistance;
		float yPart = absDistanceY / totalDistance;

		float boostX = xPart * Player.FORCE * ((attackerPos.x < receiverPos.x) ? 1 : -1);
		float boostY = yPart * Player.FORCE * ((attackerPos.y < receiverPos.y) ? 1 : -1);

		receiverMomentum.x += boostX;
		receiverMomentum.y += boostY;
	}

	/**
	 * Checks if a player is withing the kill zone of the arena and should die
	 * 
	 * @param position the position of a player
	 * @return whether or not the player should die
	 */
	private boolean shouldPlayerDie(Point position) {
		var arena = new ArenaHitbox();
		var killZone = ArenaHitbox.KILL_ZONE;

		var marginTop = arena.top() - position.y;
		var marginRight = arena.right() - position.x;
		var marginBottom = position.y - arena.bottom();
		var marginLeft = position.x - arena.left();

		if (marginLeft < killZone) {
			if (marginBottom < killZone - marginLeft || marginTop < killZone - marginLeft) {
				return true;
			}
		} else if (marginRight < killZone) {
			if (marginBottom < killZone - marginRight || marginTop < killZone - marginRight) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Applies the distance each player moves during a certain interval to their
	 * respective positions
	 * 
	 * @param p1pos position of player 1
	 * @param p2pos position of player 2
	 * @param p1dis distance that player 1 has moved
	 * @param p2dis distance that player 2 has moved
	 */
	private void applyIntervalChanges(Point p1pos, Point p2pos, Vector2 p1dis, Vector2 p2dis) {
		p1pos.add(p1dis);
		p2pos.add(p2dis);
	}

	/**
	 * Applies automatic and manual retardation to a player's momentum. The
	 * automatic retardation is divided between the x and y axes based on the
	 * proportions of the x and y axes om the player's momentum.
	 * 
	 * @param momentum          the player's momentum
	 * @param autoRetardation   the total automatic retardation on the player
	 * @param manualRetardation the manual retardaion
	 */
	private void applyRetardation(Vector2 momentum, float autoRetardation, Vector2 manualRetardation) {
		if (momentum.x == 0 && momentum.y == 0) {
			return;
		}

		var absX = Math.abs(momentum.x);
		var absY = Math.abs(momentum.y);
		var total = absX + absY;

		var autoRetardationX = autoRetardation * (absX / total);
		var autoRetardationY = autoRetardation * (absY / total);

		var retardationX = autoRetardationX + manualRetardation.x;
		var retardationY = autoRetardationY + manualRetardation.y;

		momentum.x = (momentum.x > 0) ? Math.max(momentum.x - retardationX, 0) : Math.min(momentum.x + retardationX, 0);
		momentum.y = (momentum.y > 0) ? Math.max(momentum.y - retardationY, 0) : Math.min(momentum.y + retardationY, 0);
	}

	/**
	 * Returns a Vector2 object representing the manual retardation a player creates
	 * by walking in opposite direction of the momentum in both axes respectively.
	 * 
	 * @param movement    a vector representing the player's movement input with
	 *                    both the x and the y value being either -1, 0 or 1 based
	 *                    on positive/negative direction
	 * @param momentum    a vector containing the player's momentum in the x and y
	 *                    axes
	 * @param seconds     the length of the time interval for which the manual
	 *                    retardation is applied, in seconds
	 * @param retardation a constant for manual retardation
	 * @return a vector containing the manual retardation values in the x and y axes
	 */
	private Vector2 getManualRetardation(Vector2 movement, Vector2 momentum, float seconds, float retardation) {
		float retardationX = 0, retardationY = 0;

		if (movement.x * momentum.x < 0) {
			retardationX = seconds * retardation;
		}

		if (movement.y * momentum.y < 0) {
			retardationY = seconds * retardation;
		}

		if (retardationX != 0 || retardationY != 0) {
			System.out.println(retardationX + ", " + retardationY);
		}

		return new Vector2(retardationX, retardationY);
	}

	/**
	 * Applies a frame's input events to the key input states of the previous frame
	 * 
	 * @param previous the key input states of the previous frame
	 * @param inputs   the current frame's new input events
	 * @return a new map of the previous frame's key input states, overriden by any
	 *         new input events
	 */
	private Map<Key, Boolean> applyInputEvents(Map<Key, Boolean> previous, List<InputEvent> inputs) {
		Map<Key, Boolean> frameInput = new HashMap<>();
		frameInput.putAll(previous);
		if (inputs != null) {
			for (InputEvent e : inputs) {
				frameInput.put(e.key, e.pressed);
			}
		}
		return frameInput;
	}

	/** Creates the {@link Frame} object at framestamp 0 based on default values */
	private void createFirstFrame() {
		Point pos1 = new Point(1000, 700);
		Vector2 momentum1 = new Vector2(0, 0);
		int stun1 = 0;
		int damage1 = 0;

		Map<Key, Boolean> input1 = new HashMap<>();
		for (Key key : Key.values()) {
			input1.put(key, false);
		}

		PlayerFrameData player1data = new PlayerFrameData(pos1, momentum1, input1, stun1, damage1);

		Point pos2 = new Point(1200, 700);
		Vector2 momentum2 = new Vector2(0, 0);
		int stun2 = 0;
		int damage2 = 0;

		Map<Key, Boolean> input2 = new HashMap<>();
		for (Key key : Key.values()) {
			input2.put(key, false);
		}

		PlayerFrameData player2data = new PlayerFrameData(pos2, momentum2, input2, stun2, damage2);

		Frame firstFrame = new Frame(0, player1data, player2data, false);
		frames.put(0l, firstFrame);

		lastFramestamp = 0;
	}

	/**
	 * Returns the closest framestamp to a specific time
	 * 
	 * @param timeElapsed the elapsed time since the start of the game, in
	 *                    nanoseconds
	 * @param roundUp     whether the framestamp should be rounded up to match an
	 *                    integer. The framestamp is rounded down to closest integer
	 *                    when false
	 * @return the closest framestamp to the specified time
	 */
	public long getClosestFramestamp(long timeElapsed, boolean roundUp) {
		float frameIndex = (timeElapsed / (float) Frame.LENGTH);
		long framestamp = (long) (roundUp ? Math.ceil(frameIndex) : Math.floor(frameIndex));
		return framestamp;
	}
}