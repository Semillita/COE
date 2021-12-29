package net.pacogames.coe.logic.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogicImpl implements GameLogic {

	private Map<Long, Frame> frames;
	private Map<Long, List<InputEvent>> p1inputQueue;
	private Map<Long, List<InputEvent>> p2inputQueue;
	
	private GameTimer gameTimer;
	
	public GameLogicImpl() {
		frames = new HashMap<>();
		p1inputQueue = new HashMap<>();
		p2inputQueue = new HashMap<>();
		
		gameTimer = new GameTimer();
	}
	
	@Override
	public void startGame() {
		
	}

	@Override
	public Frame getCurrentFrame() {
		return null;
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
	
	public long getClosestFramestamp(long timestamp, boolean roundUp) {
		var timeElapsed = gameTimer.getTimeElapsed(timestamp);
		float frameIndex = (timeElapsed / (float) Frame.LENGTH);
		long framestamp = Frame.LENGTH * (long) (roundUp ? Math.ceil(frameIndex) : Math.floor(frameIndex));
		return framestamp;
	}
}