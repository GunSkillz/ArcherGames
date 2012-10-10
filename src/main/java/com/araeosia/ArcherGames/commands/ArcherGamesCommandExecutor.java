package com.araeosia.ArcherGames.commands;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArcherGamesCommandExecutor implements CommandExecutor{
	
	public ArcherGames plugin;
	
	public ArcherGamesCommandExecutor(ArcherGames plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length == 0) return false;
		
		if(args[0].equalsIgnoreCase("help")){
			// Display help
			return true;
		}
		if(args[0].equalsIgnoreCase("commands")){
			// Display commands
			return true;
		}
		else if(plugin.debug){
			if(args[0].equalsIgnoreCase("startGame")){
				ScheduledTasks.gameStatus = 2;
				plugin.log.info("[ArcherGames/Debug]: Game force-started by " + sender.getName());
				return true;
			}
		}
		return false;
	}
	
	
}
