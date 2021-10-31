package net.pacogames.coe.game;

import java.util.List;
import java.util.Map;

public class Frame {

	//Frame length in milliseconds
	public static final int length = 50;
	
	public final long timeStamp;
	public Map<Key, Boolean> inputs;
	public PlayerFrameData player1data;
	public PlayerFrameData player2data;
	
	public Frame(long timeStamp, Map<Key, Boolean> inputs, PlayerFrameData player1, PlayerFrameData player2) {
		this.timeStamp = timeStamp;
		
	}
}