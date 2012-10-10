package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.entity.Player;


public class Archer {

	public boolean canTalk;
	public Player player;
	public boolean isAlive;
	public String kit;
	public boolean isReady;
	public String name;
	
	/**
	 * Make a new player when they login
	 * @param name 
	 */
	public Archer(Player player){
		this.player = player;
		canTalk = false;
		isAlive = true;
		kit = "";
		isReady = false;
	}
	
	/**
	 * Get if this player can talk
	 * @return 
	 */
	public boolean canTalk(){
		return canTalk;
	}
	
	/**
	 * Set if this player can talk
	 * @param canTalk 
	 */
	public void setCanTalk(boolean canTalk){
		this.canTalk = canTalk;
	}
	
	/**
	 * Get if the player is Alive
	 * @return if the player is alive
	 */
	public boolean isAlive(){
		return isAlive;
	}
	
	/**
	 * Set the player to dead
	 * 
	 */
	public void kill(){
		isAlive = false;
	}
	
	/**
	 * Get the player's Kit.
	 * @return 
	 */
	public String getKitName(){
		return kit;
	}
	
	/**
	 * Set the player's kit
	 * @param kit 
	 */
	public void selectKit(String kit){
		this.kit = kit;
	}
	
	/**
	 * get if the player is ready to play
	 * @return if the player is ready to play
	 */
	public boolean isReady(){
		return isReady;
	}
	
	/**
	 * Set the player as ready
	 */
	public void ready(){
		isReady = true;
	}
	
	// Static methods for use for monitoring and handling
	
	/**
	 * Get an archer by their name
	 * @param name
	 * @return 
	 */
	public static Archer getByName(String name){
		for(Archer a : ArcherGames.players){
			if(name.equals(a.player.getName())){
				return a;
			}
		}
		return null;
	}
}
