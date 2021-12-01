package net.pacogames.coe.logic.utils;

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
	
}
