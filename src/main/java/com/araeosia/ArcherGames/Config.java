package com.araeosia.ArcherGames;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;


public class Config {

	
	private ArcherGames plugin;
	/**
	 * 
	 * @param plugin
	 */
	public Config(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void loadConfiguration(){
		if(!(plugin.getConfig().getDouble("ArcherGames.technical.version") == 0.1)){
			plugin.getConfig().set("ArcherGames.technical.debug", false);
			plugin.getConfig().set("ArcherGames.technical.version", 0.1);
			ArrayList<String> voteSites = new ArrayList<String>();
			voteSites.add("http://ow.ly/cpQI0");
			voteSites.add("http://ow.ly/cmwer");
			voteSites.add("http://ow.ly/cmnPu");
			voteSites.add("http://ow.ly/cmDIF");
			voteSites.add("http://ow.ly/cmP18");
			voteSites.add("http://ow.ly/cmETB");
			voteSites.add("http://ow.ly/dVcsF");
			voteSites.add("http://ow.ly/eggLe");
			plugin.getConfig().set("ArcherGames.vote.sites", voteSites);
			plugin.getConfig().set("ArcherGames.game.countDown", 30);
			plugin.getConfig().set("ArcherGames.game.startPosition.x", 100);
			plugin.getConfig().set("ArcherGames.game.startPosition.y", 70);
			plugin.getConfig().set("ArcherGames.game.startPosition.z", 100);
			plugin.saveConfig();
		}
		plugin.voteSites = (java.util.List<String>) plugin.getConfig().getList("ArcherGames.vote.sites");
		plugin.countDown = plugin.getConfig().getInt("ArcherGames.game.countDown");
		World w = null;
		for(World world : plugin.getServer().getWorlds()){
			if(world.getEnvironment() == Environment.NORMAL) w = world;
		}
		plugin.startPosition = new Location(
				w,
				plugin.getConfig().getInt("ArcherGames.game.startPosition.x"),
				plugin.getConfig().getInt("ArcherGames.game.startPosition.y"),
				plugin.getConfig().getInt("ArcherGames.game.startPosition.z")
				);
	}
}
