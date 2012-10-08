package com.araeosia.ArcherGames;

import org.bukkit.Bukkit;

import com.araeosia.ArcherGames.ArcherGames;

public class ScheduledTasks {
	
	public ArcherGames plugin;
	
	public ScheduledTasks(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	/**
	 * This will do the action every second (20 TPS)
	 */
	public void everySecondCheck(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				//Do stuff
			}		
		}, 1 * 20, 1 * 20);
	}

}
