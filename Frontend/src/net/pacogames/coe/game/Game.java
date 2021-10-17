package net.pacogames.coe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.pacogames.coe.Scene;
import net.pacogames.coe.resources.Resources;

public class Game implements Scene {

private final int VIEWPORT_WIDTH = 3840, VIEWPORT_HEIGHT = 2160;
	
	private Camera camera;
	private Viewport viewport;
	
	private Arena arena;
	
	private Texture sprite;
	
	private Player player1;
	private Player player2;
	
	public Game() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new ExtendViewport(3840, 2160, camera);
		
		arena = new Arena();
		
		int[] keys1 = {
				Keys.W, Keys.D, Keys.S, Keys.A
		};
		player1 = new Player(this, keys1, Resources.getTexture("player/red.png"), 1300, new Vector2(-1000, 1000));
		
		int[] keys2 = {
				Keys.UP, Keys.RIGHT, Keys.DOWN, Keys.LEFT
		};
		player2 = new Player(this, keys2, Resources.getTexture("player/blue.png"), 1500, new Vector2(800, 1800));
	}
	
	@Override
	public void render(Batch batch, double deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		
		player1.render(batch, deltaTime);
		player2.render(batch, deltaTime);
		
		arena.render(batch);
	}

	public void playerWalk(Player player, int movementX, int movementY, float speed, double deltaTime) {
		movementX *= speed * deltaTime;
		movementY *= speed * deltaTime;
		
		Player opponent = (player == player1) ? player2 : player1;
		
		Hitbox playerHitbox = player.getHitbox();
		Hitbox opponentHitbox = opponent.getHitbox();
		Hitbox arenaHitbox = arena.getHitbox();
		
		if(horizontalArenaCollision(player, movementX)) {
			movementX = 0;
		}
		
		if(verticalArenaCollision(player, movementY)) {
			movementY = 0;
		}
		
		float horizontalOverlap = horizontalPlayerOverlap(player, opponent, movementX);
		float verticalOverlap = verticalPlayerOverlap(player, opponent, movementY);
				
		if(horizontalOverlap > 0 && verticalOverlap > 0) {
			if(horizontalOverlap <= verticalOverlap) {
				boolean push = opponent.push(movementX / 2, 0);
				movementX = push ? movementX / 2 : 0;
			} else {
				boolean push = opponent.push(0, movementY / 2);
				if(push) {
					movementY = push ? movementY / 2 : 0;	
				}
			}
		}
		
		player.setPosition(player.getX() + movementX, player.getY() + movementY);
		
	}
	
	public void playerApplyMomentum(Player player, Vector2 momentum, double deltaTime) {
		float movementX = (float) (momentum.x * deltaTime);
		float movementY = (float) (momentum.y * deltaTime);
				
		Player opponent = (player == player1) ? player2 : player1;
		
		Hitbox playerHitbox = player.getHitbox();
		Hitbox opponentHitbox = opponent.getHitbox();
		Hitbox arenaHitbox = arena.getHitbox();
		
		if(horizontalArenaCollision(player, movementX)) {
			momentum.x *= -0.9f;
			movementX *= -0.9;
		}
		
		if(verticalArenaCollision(player, movementY)) {
			momentum.y *= -0.9;
			movementY *= -0.9;
		}
		
		float horizontalOverlap = horizontalPlayerOverlap(player, opponent, movementX);
		float verticalOverlap = verticalPlayerOverlap(player, opponent, movementY);
				
		if(horizontalOverlap > 0 && verticalOverlap > 0) {
			Vector2 playerMomentum = player.getMomentum();
			Vector2 opponentMomentum = opponent.getMomentum();
			
			if(horizontalOverlap <= verticalOverlap) {				
				float playerSpeed = playerMomentum.x;
				float opponentSpeed = opponentMomentum.x;
				playerMomentum.x = opponentSpeed * 0.8f;
				opponentMomentum.x = playerSpeed * 0.8f;
			} else {
				float playerSpeed = playerMomentum.y;
				float opponentSpeed = opponentMomentum.y;
				playerMomentum.y = opponentSpeed * 0.8f;
				opponentMomentum.y = playerSpeed * 0.8f;
			}
		}
		player.setPosition(player.getX() + movementX, player.getY() + movementY);
	}
	
	boolean horizontalArenaCollision(Player player, float movementX) {
		Hitbox playerHitbox = player.getHitbox();
		Hitbox arenaHitbox = arena.getHitbox();
		return (playerHitbox.left() + movementX < arenaHitbox.left() || playerHitbox.right() + movementX > arenaHitbox.right());
	}
	
	boolean verticalArenaCollision(Player player, float movementY) {
		Hitbox playerHitbox = player.getHitbox();
		Hitbox arenaHitbox = arena.getHitbox();
		return (playerHitbox.bottom() + movementY < arenaHitbox.bottom() || playerHitbox.top() + movementY > arenaHitbox.top());
	}
	
	float horizontalPlayerOverlap(Player player, Player opponent, float movementX) {
		Hitbox playerHitbox = player.getHitbox();
		Hitbox opponentHitbox = opponent.getHitbox();
		
		if(playerHitbox.right() + movementX >= opponentHitbox.left() && playerHitbox.left() + movementX <= opponentHitbox.right()) {
			return (movementX >= 0) ? 
					(playerHitbox.right() + movementX) - opponentHitbox.left() : opponentHitbox.right() - (playerHitbox.left() + movementX);
		} else {
			return 0;
		}
	}
	
	float verticalPlayerOverlap(Player player, Player opponent, float movementY) {
		Hitbox playerHitbox = player.getHitbox();
		Hitbox opponentHitbox = opponent.getHitbox();
		if(playerHitbox.top() + movementY >= opponentHitbox.bottom() && playerHitbox.bottom() + movementY <= opponentHitbox.top()) {
			return (movementY >= 0) ? 
					(playerHitbox.top() + movementY) - opponentHitbox.bottom() : opponentHitbox.top() - (playerHitbox.bottom() + movementY);
		} else {
			return 0;
		}
	}
	
	public Arena getArena() {
		return arena;
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
		viewport.apply();
		
	}
	
}
