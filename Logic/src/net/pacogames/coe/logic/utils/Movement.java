package net.pacogames.coe.logic.utils;

/** Extension of the {@link Vector2} class that contains an indicator of movement
 * in the x and y axes.
 * 
 * The x and y members are set to -1, 0 or 1 indicating a direction of movement where 
 * -1 means movement in negative direction, 0 means no movement, and 1 means movement 
 * in positive direction in both axes respectively.*/
public class Movement extends Vector2 {

	public Movement(float x, float y) {
		super(x, y);
	}
	
}
