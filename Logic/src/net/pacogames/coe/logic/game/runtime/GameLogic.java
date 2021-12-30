package net.pacogames.coe.logic.game.runtime;

import net.pacogames.coe.logic.game.input.InputEvent;

public interface GameLogic {

	public void startGame();
	
	public Frame getCurrentFrame();
	
	public void loadAdvanceFrames();
	
	public void registerInput(int playerID, InputEvent event, long timestamp);
}