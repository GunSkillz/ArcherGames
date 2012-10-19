package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;

public class Archer {

	public String name;
	public boolean isAlive;
	public Kit kit;
	public boolean isReady;
	public int Score;

	/**
	 * Make a new player when they login
	 *
	 * @param name
	 */
	public Archer(String playerName) {
		this.name = playerName;
		isAlive = false;
		isReady = false;
	}

	/**
	 * Get if this player can talk
	 *
	 * @return
	 */
	public boolean canTalk() {
		return isReady;
	}
	
	public void setAlive(Boolean alive){
		this.isAlive = alive;
	}

	/**
	 * Get if the player is Alive
	 *
	 * @return if the player is alive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Set the player to dead
	 *
	 */
	public void kill() {
		isAlive = false;
	}

	/**
	 * Get the player's Kit.
	 *
	 * @return
	 */
	public Kit getKit() {
		return kit;
	}

	/**
	 * Set the player's kit
	 *
	 * @param kit
	 */
	public void selectKit(Kit kit) {
		this.isReady = true;
		this.kit = kit;
	}

	/**
	 * get if the player is ready to play
	 *
	 * @return if the player is ready to play
	 */
	public boolean isReady() {
		return isReady;
	}

	/**
	 * Set the player as ready
	 */
	public void ready() {
		isReady = true;
	}

	/**
	 *
	 */
	public String getName() {
		return name;
	}
	
	// Static methods for use for monitoring and handling

	/**
	 * Get an archer by their name
	 *
	 * @param name
	 * @return
	 */
	public static Archer getByName(String name) {
		for (Archer a : ArcherGames.players) {
			if (name.equals(a.getName())) {
				return a;
			}
		}
		return null;
	}
}
