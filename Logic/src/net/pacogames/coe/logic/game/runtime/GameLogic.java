package net.pacogames.coe.logic.game.runtime;

import net.pacogames.coe.logic.game.input.InputEvent;

/**Interface for the GameScene to interact with the physics adnd logical parts*/
public interface GameLogic {

	public void startGame();
	
	public Frame getCurrentFrame();
	
	public void loadAdvanceFrames();
	
	public void registerInput(int playerID, InputEvent event, long timestamp);
}