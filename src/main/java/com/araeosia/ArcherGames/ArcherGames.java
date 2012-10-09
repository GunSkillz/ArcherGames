package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.listeners.ArcherGamesKitCommandExecutor;
import com.araeosia.ArcherGames.listeners.PlayerChatEventListener;
import com.araeosia.ArcherGames.utils.Config;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.araeosia.ArcherGames.listeners.PlayerCommandPreProccessListener;
import com.araeosia.ArcherGames.listeners.PlayerLoginEventListener;
import com.araeosia.ArcherGames.utils.Archer;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class ArcherGames extends JavaPlugin {
	
	public boolean debug = false;
	public Config config;
	public int countDown;
	public final Logger log = getLogger();
	public List<String> voteSites;
	public Location startPosition;
        public HashMap<String, ArrayList<ItemStack>> kits = new HashMap<String, ArrayList<ItemStack>>();
        public static ArrayList<Archer> players = new ArrayList<Archer>();
	
	/**
	 * 
	 */
	@Override
	public void onEnable(){
		config = new Config(this);
		config.loadConfiguration();
		this.getServer().getPluginManager().registerEvents(new PlayerCommandPreProccessListener(this), this);
                this.getServer().getPluginManager().registerEvents(new PlayerChatEventListener(this), this);
                this.getServer().getPluginManager().registerEvents(new PlayerLoginEventListener(this), this);
                this.getCommand("kit").setExecutor(new ArcherGamesKitCommandExecutor(this));
                this.getCommand("listkits").setExecutor(new ArcherGamesKitCommandExecutor(this));
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
