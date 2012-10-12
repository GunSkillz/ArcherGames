/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.araeosia.ArcherGames.commands;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Daniel
 */
public class ArcherGamesInfoCommandExecutors implements CommandExecutor {

	public ArcherGames plugin;

	public ArcherGamesInfoCommandExecutors(ArcherGames plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vote")) {
			sender.sendMessage(plugin.strings.get("voteinfo"));
			for (String s : plugin.voteSites) {
				sender.sendMessage(ChatColor.GREEN + s);
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("money")) {
			if (args.length == 0) {
				//sender.sendMessage(plugin.econ.getBalance(sender.getName()).getBalance()); //Or something
				return true;
			} else {
				//player.sendMessage(plugin.econ.getBalance(args[0]).getBalance()); //Or something
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("stats")) {
			if (args.length == 0) {
				//plugin.getStats(sender.getName());
				return true;
			} else {
				//plugin.getStats(args[0]);
				return true;
			}
		}
		return false;
	}
}
