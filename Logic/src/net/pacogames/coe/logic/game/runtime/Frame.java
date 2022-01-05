package net.pacogames.coe.logic.game.runtime;

import java.util.List;
import java.util.Map;

public class Frame {

	//Frame length in milliseconds
	public static final int LENGTH = 16_000_000;
	
	public final long stamp;
	public PlayerFrameData player1data;
	public PlayerFrameData player2data;
	
	public Frame(long stamp, PlayerFrameData player1, PlayerFrameData player2) {
		this.stamp = stamp;
		this.player1data = player1;
		this.player2data = player2;
	}
}