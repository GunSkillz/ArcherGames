/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Daniel
 */
public class ArcherGamesInfoCommandExecutors implements CommandExecutor{

	public ArcherGames plugin;
	
	public ArcherGamesInfoCommandExecutors(ArcherGames plugin){
	this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("vote")){
			sender.sendMessage(org.bukkit.ChatColor.GREEN + "Vote on these sites for $3000 each!");
			for(String s : plugin.voteSites){
				sender.sendMessage(ChatColor.GREEN + s);
				return true;
			}
		}
		return false;
	}
	
}
