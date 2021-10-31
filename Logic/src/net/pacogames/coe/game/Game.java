package net.pacogames.coe.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Game {

	Map<Long, Frame> frames;
	
	public Game() {
		frames = new HashMap<Long, Frame>();
	}
	
	public Frame getPreviousFrame(long timeStamp) {
		return frames.get(timeStamp - Frame.length);
	}
}