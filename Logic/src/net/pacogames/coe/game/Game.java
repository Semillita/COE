package net.pacogames.coe.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Game {

	Map<Long, Frame> frames;
	Map<Long, List<InputEvent>> frameInputQueue;
	
	//Player player1, player2;
	
	public Game() {
		frames = new HashMap<Long, Frame>();
	}
	
	private Frame createFrame(long timeStamp) {
		Frame lastFrame = frames.get(timeStamp - Frame.LENGTH);
		
		PlayerFrameData p1 = lastFrame.player1data;
		PlayerFrameData p2 = lastFrame.player2data;
		
		//Calculate new momentum
		Map<Key, Boolean> p1input = p1.input;
		Map<Key, Boolean> p2input = p1.input;
		
		Vector2 p1movement = (p1.stun == 0) ? getMovement(p1input) : new Vector2(0, 0);
		Vector2 p2movement = (p2.stun == 0) ? getMovement(p2input) : new Vector2(0, 0);
		
		Vector2 p1mom = p1.momentum;
		Vector2 p2mom = p2.momentum;
		
		Vector2 p1speed = new Vector2(p1mom.x, p1mom.y);
		Vector2 p2speed = new Vector2(p2mom.x, p2mom.y);
		
		
		return null;
	}
	
	private Vector2 getMovement(Map<Key, Boolean> inputs) {
		int movementX = (inputs.get(Key.RIGHT) ? 1 : 0) - (inputs.get(Key.LEFT) ? 1: 0);
		int movementY = (inputs.get(Key.UP) ? 1 : 0) - (inputs.get(Key.DOWN) ? 1 : 0);
		
		return new Vector2(movementX, movementY);
	}
	
	/*private Frame getPreviousFrame(long timeStamp) {
		return frames.get(timeStamp - Frame.length);
	}*/
}