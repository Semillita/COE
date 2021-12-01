package net.pacogames.coe.logic.game;

import java.util.List;
import java.util.Map;

public class Frame {

	//Frame length in milliseconds
	public static final int LENGTH = 50;
	
	public final long timeStamp;
	public PlayerFrameData player1data;
	public PlayerFrameData player2data;
	
	public Frame(long timeStamp, PlayerFrameData player1, PlayerFrameData player2) {
		this.timeStamp = timeStamp;
		
	}
}