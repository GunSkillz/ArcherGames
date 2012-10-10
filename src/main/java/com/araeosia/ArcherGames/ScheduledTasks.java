package com.araeosia.ArcherGames;

import org.bukkit.Bukkit;

import com.araeosia.ArcherGames.ArcherGames;

public class ScheduledTasks {
	
	public ArcherGames plugin;
	public int gameStatus;
	public int currentLoop; // In loops
	public int preGameCountdown; // Time before anything starts. Players choose kits.
	public int gameInvincibleCountdown; // Time while players are invincible.
	public int gameOvertimeCountdown; // Time until overtime starts.
	public int minPlayersToStart;
	
	public ScheduledTasks(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	/**
	 * This will do the action every second (20 TPS)
	 */
	public void everySecondCheck(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				switch(gameStatus){
					case 1:
						// Pre-game
						if(currentLoop==preGameCountdown){
							// Time to start.
							if(plugin.getServer().getOnlinePlayers().length<minPlayersToStart){ // There aren't enough players.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("startnotenoughplayers"));
							}else{ // There's enough players, let's start!
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("starting"));
								gameStatus=2;
							}
							currentLoop=-1;
						}
						currentLoop++;
						break;
					case 2:
						// Invincibility
						break;
					case 3:
						// Game time
						break;
					case 4:
						// Overtime
						break;
					case 5:
						// Game finished, waiting for reset to pre-game
						break;
				}
			}		
		}, 1 * 20, 1 * 20);
	}

}
