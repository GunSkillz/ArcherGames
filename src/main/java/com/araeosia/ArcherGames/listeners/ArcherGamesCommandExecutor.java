package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArcherGamesCommandExecutor implements CommandExecutor{
	
	public ArcherGames plugin;
	
	public ArcherGamesCommandExecutor(ArcherGames plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return false;
	}
	
	
}
