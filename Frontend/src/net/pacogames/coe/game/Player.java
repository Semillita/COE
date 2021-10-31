package net.pacogames.coe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Player {

	private static final float SPEED = 600;
	private static final float RETARDATION = 100;
	private static final int WIDTH = 80, HEIGHT = 160;
	
	private LocalGame game;
	int[] keys;
	private Texture texture;
	private PlayerHitbox hitbox;
	
	private float x, y;
	Vector2 momentum;
	Vector2 origin;
	
	public Player(LocalGame game, int[] keys, Texture texture, int x, Vector2 momentum) { 
		this.game = game;
		this.keys = keys;
		this.texture = texture;
		
		this.x = x;
		this.y = 1000;
				
		hitbox = new PlayerHitbox(WIDTH / -2, HEIGHT / -2, WIDTH, HEIGHT);
		
		this.momentum = momentum;
		origin = new Vector2(momentum.x, momentum.y);
	}
	
	public void render(Batch batch, double deltaTime) {
		float decrease = (float) (RETARDATION * deltaTime);
		
		float absDecY = (float) Math.sqrt(Math.pow(decrease, 2) / (Math.pow(momentum.x / momentum.y, 2) + 1));
		float absDecX = Math.abs(absDecY * (momentum.x / momentum.y));
		
		if(Math.abs(momentum.x) - absDecX > 0) {
			momentum.x = (momentum.x > 0) ? momentum.x - absDecX : momentum.x + absDecX;
		} else {
			momentum.x = 0;
		}
		
		if(Math.abs(momentum.y) - absDecY > 0) {
			momentum.y = (momentum.y > 0) ? momentum.y - absDecY : momentum.y + absDecY;
		} else {
			momentum.y = 0;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			System.out.println("h");
			momentum = new Vector2(origin.x, origin.y);
		}
		
		move(deltaTime);
		batch.draw(texture, x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 getMomentum() {
		return momentum;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean push(float movementX, float movementY) {
		Hitbox arenaHitbox = game.getArena().getHitbox();
		if(hitbox.left() + movementX < arenaHitbox.left() || hitbox.right() + movementX > arenaHitbox.right()) {
			return false;
		}
		
		if(hitbox.bottom() + movementY < arenaHitbox.bottom() || hitbox.top() + movementY > arenaHitbox.top()) {
			return false;
		}
		x += movementX;
		y += movementY;
		return true;
	}
	
	private void move(double deltaTime) {
		walk(deltaTime);
		applyMomentum(deltaTime);
	}
	
	private void walk(double deltaTime) {
		int movementX = 0, movementY = 0;
		float speed = SPEED;
		
		
		if(Gdx.input.isKeyPressed(keys[1])) {
			movementX += 1;
		}
		
		if(Gdx.input.isKeyPressed(keys[3])) {
			movementX -= 1;
		}
		
		if(Gdx.input.isKeyPressed(keys[0])) {
			movementY += 1;
		}
		
		if(Gdx.input.isKeyPressed(keys[2])) {
			movementY -= 1;
		}
		
		if(movementX != 0 && movementY != 0) {
			speed = (float) (speed * Math.sqrt(0.5));
		}
		
		game.playerWalk(this, movementX, movementY, speed, deltaTime);
	}
	
	private void applyMomentum(double deltaTime) {
		game.playerApplyMomentum(this, momentum, deltaTime);
	}
	
	private class PlayerHitbox implements Hitbox {
		private final int relativeX, relativeY;
		private final int width, height;
		
		public PlayerHitbox(int relativeX, int relativeY, int width, int height) {
			this.relativeX = relativeX;
			this.relativeY = relativeY;
			this.width = width;
			this.height = height;
		}
		
		public float top() {
			return y + relativeY + height;
		}
		
		public float bottom() {
			return y + relativeY;
		}
		
		public float right() {
			return x + relativeX + width;
		}
		
		public float left() {
			return x + relativeX;
		}
		
	}
	
	private static enum Direction {
		UP, RIGHT, DOWN, LEFT;
	}
	
}
