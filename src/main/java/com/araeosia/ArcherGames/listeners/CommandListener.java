package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import com.araeosia.ArcherGames.utils.Archer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

	public ArcherGames plugin;

	public CommandListener(ArcherGames plugin) {
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
		} else if (cmd.getName().equalsIgnoreCase("listkits")) {
			sender.sendMessage(plugin.strings.get("kitinfo"));
			String kits = new String();
			for (String s : plugin.kits.keySet()) {
				kits += s + ", ";
			}
			sender.sendMessage(ChatColor.GREEN + kits);
		} else if (args.length != 0) {
			if (plugin.kits.containsKey(args[0])) {
				Archer.getByName(sender.getName()).selectKit(args[0]);
				sender.sendMessage(plugin.strings.get("kitinfo").format(args[0]));
				return true;
			}
		} else if (plugin.debug) {
			if (args[0].equalsIgnoreCase("startGame")) {
				ScheduledTasks.gameStatus = 2;
				plugin.log.info("[ArcherGames/Debug]: Game force-started by " + sender.getName());
				return true;
			}
		}
		return false;
	}
	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void onCommandPreProccessEvent(final PlayerCommandPreprocessEvent event) {
	}
}
