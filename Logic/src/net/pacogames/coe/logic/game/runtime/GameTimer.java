package net.pacogames.coe.logic.game.runtime;

public class GameTimer {

	/**The match's starting time stamp in nanoseconds*/
	private long startTime;
	
	public GameTimer() {
		
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	/**Time since the game started in nanoseconds*/
	protected long getTimeElapsed(long timestamp) {
		return timestamp - startTime;
	}
	
}
