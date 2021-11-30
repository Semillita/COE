package net.pacogames.coe.game;

public class Attack {

	private float damage;
	private float force;
	private float stun;
	private float cooldown;
	
	public Attack(float damage, float force, float stun, float cooldown) {
		this.damage = damage;
		this.force = force;
		this.stun = stun;
		this.cooldown = cooldown;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public float getForce() {
		return force;
	}
	
	public float getStun() {
		return stun;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
}