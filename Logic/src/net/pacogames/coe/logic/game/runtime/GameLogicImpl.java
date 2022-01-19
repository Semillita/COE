package net.pacogames.coe.logic.game.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.pacogames.coe.logic.game.physics.Collision;
import net.pacogames.coe.logic.game.Player;
import net.pacogames.coe.logic.game.input.InputEvent;
import net.pacogames.coe.logic.game.input.Key;
import net.pacogames.coe.logic.game.physics.GamePhysics;
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
		while(!frames.containsKey(framestamp)) {}
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
		
		System.out.println("Input at framestamp " + framestamp);
		
		frameLoader.execute(() -> {
			for(long fs = framestamp; fs <= framestamp + 5; fs++) {
				frames.put(fs, createFrame(fs));
			}
		});
	}

	private Frame createFrame(long framestamp) {
		System.out.println("Creating frame " + framestamp);
		
		var lastFrame = frames.get(framestamp - 1);
		var p1data = lastFrame.player1data;
		var p2data = lastFrame.player2data;
		
		var p1movement = (p1data.stun == 0) ? physics.getMovement(p1data.input) : new Vector2(0, 0);
		var p2movement = (p2data.stun == 0) ? physics.getMovement(p2data.input) : new Vector2(0, 0);
		
		var p1pos = p1data.pos.clone();
		var p2pos = p2data.pos.clone();
		
		var p1momentum = p1data.momentum.clone();
		var p2momentum = p2data.momentum.clone();
		
		long frameTimeElapsed = 0;
		
		while(true) {
			long timeLeft = Frame.LENGTH - frameTimeElapsed;
			
			final float speed = Player.SPEED;
			final float retardation = Player.RETARDATION;
			
			float secondsLeft = timeLeft / 1_000_000_000f;
			
			Vector2 p1distance = new Vector2(p1momentum.x * secondsLeft + p1movement.x * speed * secondsLeft, p1momentum.y * secondsLeft + p1movement.y * speed * secondsLeft);
			Vector2 p2distance = new Vector2(p2momentum.x * secondsLeft + p2movement.x * speed * secondsLeft, p2momentum.y * secondsLeft + p2movement.y * speed * secondsLeft);
			
			Collision c = physics.getNextCollision(p1pos, p2pos, p1distance, p2distance, timeLeft);
			if (c == null) {
				applyIntervalChanges(p1pos, p2pos, p1distance, p2distance);
				applyRetardation(p1momentum, retardation * (timeLeft / 1_000_000_000f));
				applyRetardation(p2momentum, retardation * (timeLeft / 1_000_000_000f));
				break;
			} else {
				var seconds = c.time / 1_000_000_000;
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
					p2momentum.x = x2;
					break;
				case P1P2Y:
					float y1 = p2momentum.y * 0.8f;
					float y2 = p1momentum.y * 0.8f;
					p1momentum.y = y1;
					p2momentum.y = y2;
					break;	
				}
				
				frameTimeElapsed += c.time;
				
				var p1d = new Vector2(p1distance.x * seconds, p1distance.y * seconds);
				var p2d = new Vector2(p2distance.x * seconds, p2distance.y * seconds);
				
				applyIntervalChanges(p1pos, p2pos, p1d, p2d);
				
				applyRetardation(p1momentum, retardation * (c.time / 1_000_000_000));
				applyRetardation(p2momentum, retardation * (c.time / 1_000_000_000));
			}
			
			break;
		}
				Map<Key, Boolean> p1newInput = applyInputEvents(p1data.input, p1inputQueue.get(framestamp));
				PlayerFrameData player1data = new PlayerFrameData(p1pos, p1momentum, p1newInput, p1data.stun - 1, 0);
				
				Map<Key, Boolean> p2newInput = applyInputEvents(p2data.input, p2inputQueue.get(framestamp));
				PlayerFrameData player2data = new PlayerFrameData(p2pos, p2momentum, p2newInput, p2data.stun - 1, 0);
				
				lastFramestamp = framestamp;
				
				return new Frame(framestamp, player1data, player2data);
	}

	private void applyIntervalChanges(Point p1pos, Point p2pos, Vector2 p1dis, Vector2 p2dis) {
		p1pos.add(p1dis);
		p2pos.add(p2dis);
	}
	
	private void applyRetardation(Vector2 momentum, float retardation) {
		if(momentum.x == 0 && momentum.y == 0) {
			return;
		}
		
		var absX = Math.abs(momentum.x);
		var absY = Math.abs(momentum.y);
		var total = absX + absY;
		
		var retX = retardation * (absX / total);
		var retY = retardation * (absY / total);
		
		momentum.x = (momentum.x > 0) ? Math.max(momentum.x - retX, 0) : Math.min(momentum.x + retX, 0);
		momentum.y = (momentum.y > 0) ? Math.max(momentum.y - retY, 0) : Math.min(momentum.y + retY, 0);
	}
	
	private Map<Key, Boolean> applyInputEvents(Map<Key, Boolean> previous, List<InputEvent> inputs) {
		Map<Key, Boolean> frameInput = new HashMap<>();
		frameInput.putAll(previous);
		if(inputs != null) {
			for(InputEvent e : inputs) {
				frameInput.put(e.key, e.pressed);
			}
		}
		return frameInput;
	}
	
	private void createFirstFrame() {
		Point pos1 = new Point(1000, 700);
		Vector2 momentum1 = new Vector2(-1200, -700);
		int stun1 = 0;
		int damage1 = 0;

		Map<Key, Boolean> input1 = new HashMap<>();
		for (Key key : Key.values()) {
			input1.put(key, false);
		}

		PlayerFrameData player1data = new PlayerFrameData(pos1, momentum1, input1, stun1, damage1);

		Point pos2 = new Point(1200, 700);
		Vector2 momentum2 = new Vector2(1000, 1000);
		int stun2 = 0;
		int damage2 = 0;

		Map<Key, Boolean> input2 = new HashMap<>();
		for (Key key : Key.values()) {
			input2.put(key, false);
		}

		PlayerFrameData player2data = new PlayerFrameData(pos2, momentum2, input2, stun2, damage2);

		Frame firstFrame = new Frame(0, player1data, player2data);
		frames.put(0l, firstFrame);

		lastFramestamp = 0;
	}

	public long getClosestFramestamp(long timeElapsed, boolean roundUp) {
		float frameIndex = (timeElapsed / (float) Frame.LENGTH);
		long framestamp = (long) (roundUp ? Math.ceil(frameIndex) : Math.floor(frameIndex));
		return framestamp;
	}
}