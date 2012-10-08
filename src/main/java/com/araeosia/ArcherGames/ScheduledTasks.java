package com.araeosia.ArcherGames;

import org.bukkit.Bukkit;

import com.araeosia.ArcherGames.ArcherGames;

public class ScheduledTasks {
	
	public ArcherGames plugin;
	public int gameStatus;
	
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
