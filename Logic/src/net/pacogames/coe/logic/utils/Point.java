package net.pacogames.coe.logic.utils;

/**Utility class to represent a decimal value point in the world*/
public class Point {

	public float x, y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector2 v) {
		x += v.x;
		y += v.y;
	}
	
	public Point clone() {
		return new Point(x, y);
	}
	
}