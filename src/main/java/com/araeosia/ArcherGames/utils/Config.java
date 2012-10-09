package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import static org.bukkit.enchantments.Enchantment.*;


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
			plugin.getConfig().set("ArcherGames.timers.preGameCountdown", 120); // 2 minutes for everyone to get in game
			plugin.getConfig().set("ArcherGames.timers.gameInvincibleCountdown", 60); // 1 minute for everyone to get far enough away from each other
			plugin.getConfig().set("ArcherGames.timers.gameOvertimeCountdown", 600); // 10 minutes to play before we force the round to end
			plugin.getConfig().set("ArcherGames.game.startPosition.world", plugin.getServer().getWorlds().get(0)); // Fetch the default world
			plugin.getConfig().set("ArcherGames.game.startPosition.x", 0);
			plugin.getConfig().set("ArcherGames.game.startPosition.y", 64);
			plugin.getConfig().set("ArcherGames.game.startPosition.z", 0);
                        plugin.getConfig().set("ArcherGames.kits.ExampleKitName1", "itemid:damage:enchantid:enchantlvl");
			plugin.saveConfig();
		}
		plugin.voteSites = (java.util.List<String>) plugin.getConfig().getList("ArcherGames.vote.sites");
		plugin.countDown = plugin.getConfig().getInt("ArcherGames.game.countDown");
		plugin.startPosition = new Location(
				plugin.getServer().getWorld(plugin.getConfig().getString("ArcherGames.game.startPosition.world")),
				plugin.getConfig().getInt("ArcherGames.game.startPosition.x"),
				plugin.getConfig().getInt("ArcherGames.game.startPosition.y"),
				plugin.getConfig().getInt("ArcherGames.game.startPosition.z")
				);
                loadKits();
        }
        
        /**
         * Load the Kits form the Config
         * 
         */
           public void loadKits(){
               ArrayList<ItemStack> temp;
               for(String key : plugin.getConfig().getConfigurationSection("ArcherGames.kits").getKeys(false)){
                   temp = new ArrayList<ItemStack>();
                   String s = plugin.getConfig().getString("ArcherGames.kits." + key);
                   for(String str : s.split(",")){
                       ItemStack itemStack = new ItemStack( Material.getMaterial(Integer.parseInt( str.split(":")[0] )), Integer.parseInt(str.split(":")[1]));
                       itemStack.addEnchantment(Enchantment.getById(Integer.parseInt(str.split(":")[2])), Integer.parseInt(str.split(":")[3]));
                       temp.add( itemStack );
                   }
                   plugin.kits.put(key, temp);
               }
        }
}
