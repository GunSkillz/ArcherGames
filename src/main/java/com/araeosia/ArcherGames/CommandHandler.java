package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.utils.Archer;
import com.araeosia.ArcherGames.utils.BookItem;
import com.araeosia.ArcherGames.utils.Kit;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class CommandHandler implements CommandExecutor, Listener {

	public ArcherGames plugin;

	public CommandHandler(ArcherGames plugin) {
		this.plugin = plugin;
	}
	public HashMap<String, Integer> chunkReloads;

	/**
	 *
	 * @param sender
	 * @param cmd
	 * @param commandLabel
	 * @param args
	 * @return
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vote")) {
			sender.sendMessage(plugin.strings.get("voteinfo"));
			for (String s : plugin.voteSites.split("~")) {
				sender.sendMessage(ChatColor.GREEN + s);
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("money")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + sender.getName() + "'s balance is " + plugin.db.getMoney(sender.getName()) + "");
				return true;
			} else {
				sender.sendMessage(ChatColor.GREEN + args[0] + "'s balance is " + plugin.db.getMoney(args[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("who") || cmd.getName().equalsIgnoreCase("online") || cmd.getName().equalsIgnoreCase("players")) {
			String outputAlive = "";
			int alive = 0;
			String outputSpec = "";
			int spec = 0;
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (plugin.serverwide.getArcher(p).getPlaying()) {
					outputAlive += p.getDisplayName() + ", ";
					alive++;
				} else {
					spec++;
					outputSpec += p.getDisplayName() + ", ";
				}
			}
			sender.sendMessage(ChatColor.GRAY + "" + alive + ChatColor.DARK_GRAY + " Archers are currently playing: ");
			sender.sendMessage(outputAlive);
			sender.sendMessage(ChatColor.GRAY + "" + spec + ChatColor.DARK_GRAY + " Spectators are currently watching: ");
			sender.sendMessage(outputSpec);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("kit") || cmd.getName().equalsIgnoreCase("kits")) {
			if (args.length != 0) {
				if (ScheduledTasks.gameStatus != 1) {
					sender.sendMessage(plugin.strings.get("alreadystartedkits"));
				} else {
					Kit selectedKit = new Kit();
					Boolean isOkay = false;
					for (Kit kit : plugin.kits) {
						if (args[0].equalsIgnoreCase(kit.getName())) {
							isOkay = true;
							selectedKit = kit;
						}
					}
					if (isOkay) {
						if (plugin.serverwide.getArcher(sender.getName()).getPlaying()) {
							sender.sendMessage(String.format(plugin.strings.get("alreadyselected"), plugin.serverwide.getArcher(sender.getName()).getKit().getName()));
						}
						if (sender.hasPermission(selectedKit.getPermission())) {
							plugin.serverwide.joinGame(sender.getName(), selectedKit);
							sender.sendMessage(String.format(plugin.strings.get("kitgiven"), selectedKit.getName()));
						} else {
							sender.sendMessage(ChatColor.RED + "You do not have permission to use this kit.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "That is not a valid kit.");
					}
				}
			} else {
				sender.sendMessage(ChatColor.AQUA + "Use /kit (kitname) to select a kit.");
				sender.sendMessage(ChatColor.DARK_GREEN + plugin.strings.get("kitinfo"));
				String kits = "";
				String kitsNo = "";
				for (Kit kit : plugin.kits) {
					if (sender.hasPermission(kit.getPermission())) {
						kits += kit.getName() + ", ";
					} else {
						kitsNo += kit.getName() + ", ";
					}
				}
				sender.sendMessage(ChatColor.GREEN + kits);
				sender.sendMessage(ChatColor.DARK_RED + plugin.strings.get("kitnoaccessible"));
				sender.sendMessage(ChatColor.RED + kitsNo);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("chunk")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.getWorld().unloadChunk(player.getLocation().getChunk());
				player.getWorld().loadChunk(player.getLocation().getChunk());
				player.teleport(player.getLocation());
				player.sendMessage(ChatColor.GREEN + "Chunk Reloaded.");
				return true;
			} else {
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("pay")) {
			if (args.length != 0) {
				if (args.length != 1) {
					try {
						if (Double.parseDouble(args[1]) > 0) {
							if (plugin.db.hasMoney(sender.getName(), Double.parseDouble(args[1]))) {
								plugin.db.takeMoney(sender.getName(), Double.parseDouble(args[1]));
								plugin.db.addMoney(args[0], Double.parseDouble(args[1]));
							} else {
								sender.sendMessage("You cannot afford to send this amount of money!");
							}

							sender.sendMessage(ChatColor.GREEN + "$" + args[0] + " paid to " + args[0]);
						}
					} catch (Exception e) {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("time")) {
			if (!(ScheduledTasks.gameStatus >= 2)) {
				sender.sendMessage(ChatColor.GREEN + ((String.format(plugin.strings.get("starttimeleft"), ((plugin.scheduler.preGameCountdown - plugin.scheduler.currentLoop) / 60 + " minute" + (((plugin.scheduler.preGameCountdown - plugin.scheduler.currentLoop) / 60) == 1 ? "" : "s") + ", " + ((plugin.scheduler.preGameCountdown - plugin.scheduler.currentLoop) % 60) + " second" + ((plugin.scheduler.preGameCountdown - plugin.scheduler.currentLoop != 1) ? "s" : ""))))));
			} else if (!(ScheduledTasks.gameStatus >= 3)) {
				sender.sendMessage(ChatColor.GREEN + ((String.format(plugin.strings.get("invincibilityend"), ((plugin.scheduler.gameInvincibleCountdown - plugin.scheduler.currentLoop) / 60 + " minute" + (((plugin.scheduler.gameInvincibleCountdown - plugin.scheduler.currentLoop) / 60) == 1 ? "" : "s") + ", " + ((plugin.scheduler.gameInvincibleCountdown - plugin.scheduler.currentLoop) % 60) + " second" + ((plugin.scheduler.gameInvincibleCountdown - plugin.scheduler.currentLoop != 1) ? "s" : ""))))));
			} else if (!(ScheduledTasks.gameStatus >= 4)) {
				sender.sendMessage(ChatColor.GREEN + ((plugin.scheduler.gameOvertimeCountdown - plugin.scheduler.currentLoop) % 60 == 0 ? (plugin.scheduler.gameOvertimeCountdown - plugin.scheduler.currentLoop) / 60 + " minutes until overtime starts" : (plugin.scheduler.gameOvertimeCountdown - plugin.scheduler.currentLoop) / 60 + " minutes and " + (plugin.scheduler.gameOvertimeCountdown - plugin.scheduler.currentLoop) % 60 + " seconds until overtime starts."));
			} else {
				sender.sendMessage(ChatColor.RED + "Nothing to time!");
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("timer")) {
			if (args.length != 0) {
				if (sender.hasPermission("ArcherGames.admin")) {
					try {
						plugin.scheduler.preGameCountdown = Integer.parseInt(args[0]);
						plugin.scheduler.currentLoop = 0;
						sender.sendMessage(ChatColor.GREEN + "Time left to start set to " + args[0] + " seconds left.");
						return true;
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "Time could not be set.");
						return true;
					}
				}
			}
		} else if (plugin.debug
				&& cmd.getName().equalsIgnoreCase("ArcherGames")) {

			if (args[0].equalsIgnoreCase("startGame")) {
				ScheduledTasks.gameStatus = 2;
				sender.sendMessage(ChatColor.GREEN + "Game started.");
				plugin.log.info("[ArcherGames/Debug]: Game force-started by " + sender.getName());
				return true;
			}

		} else if (cmd.getName().equalsIgnoreCase("lockdown")) {
			if (sender.hasPermission("archergames.admin")) {
				plugin.getConfig().set("ArcherGames.toggles.lockdownMode", !plugin.configToggles.get("ArcherGames.toggles.lockdownMode"));
				plugin.saveConfig();
				sender.sendMessage(ChatColor.GREEN + "LockDown mode toggled.");
				return true;
			} else {
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("credtop")) {
			sender.sendMessage(ChatColor.GREEN + "Here are the top credit amounts on ArcherGames currently:");
			HashMap<String, Integer> credits = plugin.db.getTopPoints();
			int i = 1;
			for (String playerName : credits.keySet()) {
				sender.sendMessage(ChatColor.GREEN + "" + i + ": " + playerName + " | " + credits.get(playerName));
				i++;
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("baltop")) {
			sender.sendMessage(ChatColor.GREEN + "Here are the top balance amounts on ArcherGames currently:");
			HashMap<String, Integer> balances = plugin.db.getTopPoints();
			int i = 1;
			for (String playerName : balances.keySet()) {
				sender.sendMessage(ChatColor.GREEN + "" + i + ": " + playerName + " | " + balances.get(playerName));
				i++;
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("wintop")) {
			sender.sendMessage(ChatColor.GREEN + "Here are the top amounts of won games on ArcherGames currently:");
			HashMap<String, Integer> wins = plugin.db.getTopWinners();
			int i = 1;
			for (String playerName : wins.keySet()) {
				sender.sendMessage(ChatColor.GREEN + "" + i + ": " + playerName + " | " + wins.get(playerName));
				i++;
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("stats")) {
			String lookup = args.length == 0 ? sender.getName() : args[0];

			sender.sendMessage(ChatColor.GREEN + lookup + "'s Statistics:");
			sender.sendMessage(ChatColor.GREEN + "Wins: " + plugin.db.getWins(lookup));
			sender.sendMessage(ChatColor.GREEN + "Games played: " + plugin.db.getPlays(lookup));
			sender.sendMessage(ChatColor.GREEN + "Credits: " + plugin.db.getPoints(lookup));
			sender.sendMessage(ChatColor.GREEN + "Deaths: " + plugin.db.getDeaths(lookup));
			sender.sendMessage(ChatColor.GREEN + "Time Played: " + plugin.db.getPlayTime(lookup));
			return true;
		} else if (cmd.getName().equalsIgnoreCase("credits")) {
			String lookup = args.length == 0 ? sender.getName() : args[0];
			sender.sendMessage(ChatColor.GREEN + "" + lookup + " has " + plugin.db.getPoints(lookup) + " credits.");

			return true;
		} else if (cmd.getName().equalsIgnoreCase("track")) {
			if (sender.hasPermission("ArcherGames.donor.track")) {
				if (sender instanceof Player) {
					if (args.length != 0) {
						Player player = (Player) sender;
						if (!player.getInventory().contains(Material.COMPASS)) {
							player.getInventory().addItem(new ItemStack(Material.COMPASS));
						}
						player.setCompassTarget(plugin.getServer().getPlayer(args[0]).getLocation());
						player.sendMessage(ChatColor.GREEN + "Compass set to point to " + args[0]);
					} else {
						sender.sendMessage(ChatColor.RED + "You need to sepcify a player to point your compass at!");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You must be a player to execute that command!");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("ride")) {
			if (sender instanceof Player) {
				if (!plugin.serverwide.ridingPlayers.contains(sender.getName())) {
					plugin.serverwide.ridingPlayers.add(sender.getName());
					sender.sendMessage(ChatColor.GREEN + "You are now able to right click and ride players.");
				} else {
					plugin.serverwide.ridingPlayers.remove(sender.getName());
					sender.sendMessage(ChatColor.GREEN + "You are no longer able to right click and ride players.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute that command.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("help")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.getInventory().contains(Material.WRITTEN_BOOK)) {
					BookItem bi = new BookItem(new ItemStack(387, 1));
					bi.setAuthor(plugin.getConfig().getString("ArcherGames.startbook.author"));
					bi.setTitle(plugin.getConfig().getString("ArcherGames.startbook.Title"));
					String[] pages = plugin.getConfig().getStringList("ArcherGames.startbook.pages").toArray(new String[11]);
					bi.setPages(pages);
					player.getInventory().addItem(bi.getItemStack());
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "You already have a help book! Check your inventory!");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute that command!");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("commands")) {
			sendHelp(sender, "kit [kitname]", "Pick or list the kits, depending on arguements.");
			sendHelp(sender, "vote", "List the sites you can vote for the server on.");
			sendHelp(sender, "money [player]", "Show either your or another player's money balance.");
			sendHelp(sender, "stats [player]", "Show either your or another player's stats.");
			sendHelp(sender, "chunk", "Reload your current chunk to fix a loading issue");
			sendHelp(sender, "pay (player) (amt)", "Send the specified player the specified amount of money");
			sendHelp(sender, "time", "Show the amount of time before the next event happens (Game start, Overtime, etc.)");
			sendHelp(sender, "timer (time)", "Set the amount of time left to the start of the game.");
			sendHelp(sender, "who/online/players", "See the players online and who is alive.");
			sendHelp(sender, "credtop", "Show the top players for credits earned.");
			sendHelp(sender, "baltop", "Show the top players for money earned.");
			sendHelp(sender, "wintop", "Show the top players for games won.");
			sendHelp(sender, "stats [player]", "Show the specified player, or your, stats.");
			sendHelp(sender, "track (player)", "Use a compass to point at onother player. (Donor only)");
			sendHelp(sender, "ride", "Toggle the ability to right click players and 'ride' them");
			sendHelp(sender, "help", "Give yourself a help book.");
			sendHelp(sender, "commands", "Show this help page.");
			sendHelp(sender, "goto (player)", "Teleport to another player while spectating.");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("goto")) {
			if (args.length != 0) {
				Player p = null;
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					if (player.getName().contains(args[0])) {
						p = player;
					}
				}
				if (p != null) {
					if (sender instanceof Player) {
						if (!plugin.serverwide.getArcher(sender.getName()).getPlaying()) {
							((Player) sender).teleport(p);
							sender.sendMessage(ChatColor.GREEN + "You have been teleported to " + p.getName());
							return true;
						} else {
							sender.sendMessage("You must be dead to use that command!");
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You must be a player to execute that command!");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "That is not a valid player!");
					return true;
				}
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
		if (!(plugin.serverwide.getArcher(event.getPlayer()).getKit() == null) || !event.getPlayer().hasPermission("archergames.overrides.command")) {
			if (!event.getMessage().contains("kit") && false) { // Needs fixing.
				event.setCancelled(true);
				event.getPlayer().sendMessage(plugin.strings.get("nocommand"));
			}
		}
	}

	private void sendHelp(CommandSender sender, String command, String description) {
		sender.sendMessage(ChatColor.GOLD + "/" + command + ": " + ChatColor.YELLOW + description);
	}
}
