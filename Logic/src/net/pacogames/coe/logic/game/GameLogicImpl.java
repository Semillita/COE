package net.pacogames.coe.logic.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		gameTimer.start();
	}

	@Override
	public Frame getCurrentFrame() {
		var framestamp = getClosestFramestamp(System.nanoTime(), false);
		return frames.containsKey(framestamp) ? frames.get(framestamp) : null;
	}

	@Override
	public void loadAdvanceFrames() {
		frameLoader.execute(() -> {
			var timeElapsed = gameTimer.getTimeElapsed(System.nanoTime());
			var currentFramestamp = getClosestFramestamp(timeElapsed, false);
			
			for(long framestamp = currentFramestamp; framestamp <= currentFramestamp + 2; framestamp++) {
				if(!frames.containsKey(framestamp)) {
					frames.put(framestamp, createFrame(framestamp));
				}
			}
		});
	}
	
	@Override
	public void registerInput(int playerID, InputEvent event, long timestamp) {
		var framestamp = getClosestFramestamp(timestamp, true);
		var queue = (playerID == 1) ? p1inputQueue : p2inputQueue;
		
		if(queue.containsKey(framestamp)) {
			queue.get(framestamp).add(event);
		} else {
			queue.put(framestamp, Arrays.asList(event));
		}
	}
	
	private Frame createFrame(long framestamp) {
		return null;
	}
	
	private long getClosestFramestamp(long timestamp, boolean roundUp) {
		var timeElapsed = gameTimer.getTimeElapsed(timestamp);
		float frameIndex = (timeElapsed / (float) Frame.LENGTH);
		long framestamp = (long) (roundUp ? Math.ceil(frameIndex) : Math.floor(frameIndex));
		return framestamp;
	}
}