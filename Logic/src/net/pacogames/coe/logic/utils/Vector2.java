package net.pacogames.coe.logic.utils;

public class Vector2 {

	public float x, y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 clone() {
		return new Vector2(x, y);
	}
	
}