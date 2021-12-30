package net.pacogames.coe.logic.game.runtime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.pacogames.coe.logic.game.input.InputEvent;
import net.pacogames.coe.logic.game.input.Key;
import net.pacogames.coe.logic.utils.Point;
import net.pacogames.coe.logic.utils.Vector2;

public class GameLogicImpl implements GameLogic {

	private Map<Long, Frame> frames;
	private Map<Long, List<InputEvent>> p1inputQueue;
	private Map<Long, List<InputEvent>> p2inputQueue;

	private GameTimer gameTimer;
	private ExecutorService frameLoader;

	public GameLogicImpl() {
		frames = new HashMap<>();
		p1inputQueue = new HashMap<>();
		p2inputQueue = new HashMap<>();

		gameTimer = new GameTimer();
		frameLoader = Executors.newSingleThreadExecutor();
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
		return frames.containsKey(framestamp) ? frames.get(framestamp) : null;
	}

	@Override
	public void loadAdvanceFrames() {
		frameLoader.execute(() -> {
			var timeElapsed = gameTimer.getTimeElapsed(System.nanoTime());
			var currentFramestamp = getClosestFramestamp(timeElapsed, false);

			for (long framestamp = currentFramestamp; framestamp <= currentFramestamp + 2; framestamp++) {
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
			queue.put(framestamp, Arrays.asList(event));
		}
	}

	private Frame createFrame(long framestamp) {
		var lastFrame = frames.get(framestamp - 1);
		return lastFrame;
	}

	private void createFirstFrame() {
		Point pos1 = new Point(1000, 700);
		Vector2 momentum1 = new Vector2(400, 1000);
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

		Frame firstFrame = new Frame(0, player1data, player2data);
		frames.put(0l, firstFrame);

	}

	private long getClosestFramestamp(long timeElapsed, boolean roundUp) {
		float frameIndex = (timeElapsed / (float) Frame.LENGTH);
		long framestamp = (long) (roundUp ? Math.ceil(frameIndex) : Math.floor(frameIndex));
		return framestamp;
	}
}