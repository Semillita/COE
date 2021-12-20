package net.pacogames.coe.logic.game;

public class GameTimer {

	//The match's starting time stamp in nanoseconds
	private long startTime;
	
	public GameTimer() {
		
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	/**Time since the game started in milliseconds*/
	protected long getTimeElapsed() {
		return (System.nanoTime() - startTime) / 1_000_000;
	}
	
}
