package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.utils.Config;
import java.util.List;
import java.util.logging.Logger;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.araeosia.ArcherGames.listeners.PlayerCommandPreProccessListener;

public class ArcherGames extends JavaPlugin {
	
	public boolean debug = false;
	public Config config;
	public int countDown;
	Logger log = this.getLogger();
	public List<String> voteSites;
	public Location startPosition;
	
	/**
	 * 
	 */
	@Override
	public void onEnable(){
		config = new Config(this);
		config.loadConfiguration();
		this.getServer().getPluginManager().registerEvents(new PlayerCommandPreProccessListener(this), this);
		log.info("ArcherGames is enabled!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				ArcherGames plugin = new ArcherGames();
				for(Player player : plugin.getServer().getOnlinePlayers()){
					player.sendMessage(ChatColor.GREEN + "Game starts in " + countDown + " seconds!");
				}
			}
		}, (countDown) * 20 * 60);
	
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				
			}
		}, (countDown) * 20 * 60);
	}
	
	/**
	 * 
	 */
	@Override
	public void onDisable(){
		log.info("ArcherGames is disabled.");
		
	}
}
