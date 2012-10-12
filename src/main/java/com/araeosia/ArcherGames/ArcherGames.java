package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.CommandHandler;
import com.araeosia.ArcherGames.utils.Archer;
import com.araeosia.ArcherGames.utils.Config;
import com.araeosia.ArcherGames.listeners.PlayerEventListener;
import com.araeosia.ArcherGames.listeners.EntityEventListener;

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
	public ServerWide serverwide;

	/**
	 *
	 */
	@Override
	public void onEnable() {
		log = this.getLogger();
		scheduler = new ScheduledTasks(this);
		serverwide = new ServerWide(this);
		config = new Config(this);
		config.loadConfiguration();
		// Events
		this.getServer().getPluginManager().registerEvents(new EntityEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
		// Commands
		this.getCommand("kit").setExecutor(new CommandHandler(this));
		this.getCommand("listkits").setExecutor(new CommandHandler(this));
		this.getCommand("vote").setExecutor(new CommandHandler(this));
		this.getCommand("money").setExecutor(new CommandHandler(this));
		this.getCommand("stats").setExecutor(new CommandHandler(this));
		this.getCommand("archergames").setExecutor(new CommandHandler(this));

		log.info("ArcherGames is enabled!");
		if (debug) {
			log.info("Debug mode is enabled!");
		}
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
