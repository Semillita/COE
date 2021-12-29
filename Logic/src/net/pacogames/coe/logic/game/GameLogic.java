package net.pacogames.coe.logic.game;

public interface GameLogic {

	public void startGame();
	
	public Frame getCurrentFrame();
	
	public void registerInput(int playerID, InputEvent event, long timestamp);
	
	
}