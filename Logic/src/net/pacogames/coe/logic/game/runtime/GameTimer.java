package net.pacogames.coe.logic.game.runtime;

/**Utility class for timer related things*/
public class GameTimer {

	/**The match's starting time stamp in nanoseconds*/
	private long startTime;
	
	public GameTimer() {
		
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	/**Time since the game started in nanoseconds*/
	public long getTimeElapsed(long timestamp) {
		return timestamp - startTime;
	}
	
}
