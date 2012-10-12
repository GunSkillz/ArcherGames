package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.utils.Archer;
import com.araeosia.ArcherGames.utils.Config;
import com.araeosia.ArcherGames.commands.ArcherGamesCommandExecutor;
import com.araeosia.ArcherGames.commands.ArcherGamesInfoCommandExecutors;
import com.araeosia.ArcherGames.commands.ArcherGamesKitCommandExecutor;
import com.araeosia.ArcherGames.listeners.PlayerChatEventListener;
import com.araeosia.ArcherGames.listeners.PlayerCommandPreProccessListener;
import com.araeosia.ArcherGames.listeners.PlayerDamageEventListener;
import com.araeosia.ArcherGames.listeners.PlayerDeathEventListener;
import com.araeosia.ArcherGames.listeners.PlayerLoginEventListener;
import com.araeosia.ArcherGames.listeners.ProjectileHitEventListener;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;





public class ArcherGames extends JavaPlugin {

	public boolean debug = false;
	public Config config;
	public ScheduledTasks scheduler;
	public static Logger log;
	public List<String> voteSites;
	public Location startPosition;
	public HashMap<String, ArrayList<ItemStack>> kits = new HashMap<String, ArrayList<ItemStack>>();
	public static ArrayList<Archer> players = new ArrayList<Archer>();
	public HashMap<String, String> strings = new HashMap<String, String>();

	/**
	 *
	 */
	@Override
	public void onEnable() {
		log = this.getLogger();
		scheduler = new ScheduledTasks(this);
		config = new Config(this);
		config.loadConfiguration();
		// Events
		this.getServer().getPluginManager().registerEvents(new PlayerCommandPreProccessListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerChatEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerLoginEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDamageEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new ProjectileHitEventListener(this), this);
		// Commands
		this.getCommand("kit").setExecutor(new ArcherGamesKitCommandExecutor(this));
		this.getCommand("listkits").setExecutor(new ArcherGamesKitCommandExecutor(this));
		this.getCommand("vote").setExecutor(new ArcherGamesInfoCommandExecutors(this));
		this.getCommand("money").setExecutor(new ArcherGamesInfoCommandExecutors(this));
		this.getCommand("stats").setExecutor(new ArcherGamesInfoCommandExecutors(this));
		this.getCommand("archergames").setExecutor(new ArcherGamesCommandExecutor(this));

		log.info("ArcherGames is enabled!");
		if(debug){ log.info("Debug mode is enabled!"); }
		log.info("Starting automated loop of games...");
		scheduler.everySecondCheck();
	}

	/**
	 *
	 */
	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelTask(scheduler.schedulerTaskID); // Kill the loop.
		log.info("ArcherGames is disabled.");

	}
}
