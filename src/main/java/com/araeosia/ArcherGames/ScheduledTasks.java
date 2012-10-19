package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.listeners.PlayerEventListener;
import com.araeosia.ArcherGames.utils.Archer;
import com.araeosia.ArcherGames.utils.Kit;
import com.wimbli.WorldBorder.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.kitteh.vanish.staticaccess.VanishNoPacket;
import org.kitteh.vanish.staticaccess.VanishNotLoadedException;

public class ScheduledTasks {

	public ArcherGames plugin;
	public static int gameStatus = 1;
	public int currentLoop; // In loops
	public int preGameCountdown; // Time before anything starts. Players choose kits.
	public int gameInvincibleCountdown; // Time while players are invincible.
	public int gameOvertimeCountdown; // Time until overtime starts.
	public int shutdownTimer; // Time after the game ends until the server shuts down.
	public int minPlayersToStart;
	public int schedulerTaskID;
	public int nagTime;
	public int overtimeWorldRadius;

	public ScheduledTasks(ArcherGames plugin) {
		this.plugin = plugin;
	}

	/**
	 * This will do the action every second (20 TPS)
	 */
	public void everySecondCheck() {
		if (schedulerTaskID != -1) {
			Config.removeBorder(plugin.startPosition.getWorld().getName());
			schedulerTaskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

				public void run() {
					switch (gameStatus) {
						case 1:
							if (plugin.debug) {
								plugin.log.info((preGameCountdown - currentLoop) + " seconds until game starts.");
							}
							// Pre-game
							if ((preGameCountdown - currentLoop % 3600 == 0 || preGameCountdown - currentLoop == 60 || preGameCountdown - currentLoop % 60 == 0 || (preGameCountdown - currentLoop <= 10 && preGameCountdown - currentLoop > 0) || preGameCountdown - currentLoop == 15 || preGameCountdown - currentLoop == 30) && preGameCountdown - currentLoop != 0) {
								plugin.serverwide.sendMessageToAllPlayers(String.format(plugin.strings.get("starttimeleft"), (((preGameCountdown - currentLoop) % 3600) == 0) ? ((preGameCountdown - currentLoop) / 60) + " hour" + ((((preGameCountdown - currentLoop) / 60) == 1) ? "s" : "") : ((preGameCountdown - currentLoop) % 60 == 0 ? (preGameCountdown - currentLoop) / 60 + " minute" + (((preGameCountdown - currentLoop) / 60) == 1 ? "" : "s") : (preGameCountdown - currentLoop) + " second" + ((preGameCountdown - currentLoop != 1) ? "s" : ""))));
							}
							if (currentLoop >= preGameCountdown) {
								if (plugin.debug) {
									plugin.log.info("Attempting to start...");
								}
								// Time to start.
								if (plugin.serverwide.livingPlayers.size() <= minPlayersToStart) { // There aren't enough players.
									plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("startnotenoughplayers"));
								} else { // There's enough players, let's start!
									ScheduledTasks.gameStatus = 2;
									plugin.scheduler.startGame();
								}
								currentLoop = -1;
							}
							currentLoop++;
							break;
						case 2:
							// Invincibility
							if (plugin.debug) {
								plugin.log.info((gameInvincibleCountdown - currentLoop) + " seconds until invincibility ends.");
							}
							checkGameEnd();
							if (currentLoop >= gameInvincibleCountdown) {
								if (plugin.debug) {
									plugin.log.info("Invincibility has ended.");
								}
								// Invincibility is over.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("invincibilityend"));
								gameStatus = 3;
								currentLoop = -1;
							}
							currentLoop++;
							break;
						case 3:
							// Game time
							if (plugin.debug) {
								plugin.log.info((gameOvertimeCountdown - currentLoop) + " seconds until overtime starts.");
							}
							if ((currentLoop - gameOvertimeCountdown) % 60 == 0 || (((currentLoop - gameOvertimeCountdown) / 60) <= 60 && ((((currentLoop - gameOvertimeCountdown) / 60) == 30 || ((currentLoop - gameOvertimeCountdown) / 60) == 15 || (((currentLoop - gameOvertimeCountdown) / 60) <= 10 && !(((currentLoop - gameOvertimeCountdown) / 60) == 0)))))) {
								if (!((currentLoop - gameOvertimeCountdown) == 0)) {
									plugin.serverwide.sendMessageToAllPlayers( (gameOvertimeCountdown-currentLoop ) % 60 == 0 ? (gameOvertimeCountdown-currentLoop) / 60 + " minutes until overtime starts" : (gameOvertimeCountdown-currentLoop) / 60 +" minutes and " + (gameOvertimeCountdown-currentLoop) % 60 + " seconds until overtime starts.");
								}
							}
							checkGameEnd();
							if (currentLoop >= gameOvertimeCountdown) {
								if (plugin.debug) {
									plugin.log.info("Overtime has started.");
								}
								// Game time is up.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("overtimestart"));
								for (Player p : plugin.getServer().getOnlinePlayers()) {
									if (plugin.serverwide.getArcher(p).isReady) {
										p.teleport(plugin.startPosition);
									}
								}
								Config.setBorder(plugin.startPosition.getWorld().getName(), overtimeWorldRadius, plugin.startPosition.getBlockX(), plugin.startPosition.getBlockZ(), true); // World border
								gameStatus = 4;
								currentLoop = -1;
								// TODO: World border shrinking.
							}
							currentLoop++;
							break;
						case 4:
							// Overtime
							checkGameEnd();
							currentLoop++;
							break;
						case 5:
							// Game finished, waiting for reset to pre-game
							if (plugin.debug) {
								plugin.log.info((shutdownTimer - currentLoop) + " seconds until server reboots.");
							}
							if (currentLoop % 5 == 0) {
								plugin.serverwide.sendMessageToAllPlayers(ChatColor.GREEN + "" + plugin.serverwide.winner + " is the winner!");
							}
							if (currentLoop >= shutdownTimer) {
								if (plugin.debug) {
									plugin.log.info("Kicking all players, then shutting down.");
								}
								for (Player p : plugin.getServer().getOnlinePlayers()) {
									p.kickPlayer(plugin.strings.get("serverclosekick"));
								}
								plugin.getServer().shutdown();
							}
							currentLoop++;
							break;
					}
				}
			}, 20L, 20L);
			if (plugin.debug) {
				plugin.log.info("Task ID is " + schedulerTaskID);
			}
		} else {
			plugin.log.severe("Scheduler task start was attempted, but scheduler task already running!");
		}
	}

	public void startGame() {
		plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("starting"));
		currentLoop = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			Archer a = Archer.getByName(p.getName());
			if (!plugin.serverwide.livingPlayers.contains(a)) {
				Kit selectedKit = new Kit();
				for (Kit kit : plugin.kits) {
					if (kit.getName() == "Explode") {
						selectedKit = kit;
					}
				}
				plugin.serverwide.joinGame(p.getName(), selectedKit);
			}
			if (plugin.serverwide.livingPlayers.contains(a)) {
				if (plugin.getServer().getPlayer(a.getName()).getInventory().contains(Material.BOOK)) {
					plugin.getServer().getPlayer(a.getName()).getInventory().remove(Material.BOOK);
				}
				a.ready();
				a.getKit().giveToPlayer(plugin.getServer().getPlayer(a.getName())); // There's an error here somewhere.
				plugin.serverwide.tpToRandomLocation(plugin.serverwide.getPlayer(a));
				//plugin.serverwide.getPlayer(a).teleport(plugin.startPosition);
				plugin.serverwide.getPlayer(a).setAllowFlight(false);
			} else {
				try {
					if (!VanishNoPacket.isVanished(a.getName())) {
						VanishNoPacket.toggleVanishSilent(p);
					}
				} catch (VanishNotLoadedException ex) {
				}
			}
		}

		for (int task : PlayerEventListener.naggerTask.values()) {
			plugin.getServer().getScheduler().cancelTask(task); // No point in nagging them when they can't do anything about it.
		}
	}

	public int nagPlayerKit(final String playerName) {
		plugin.getServer().getPlayer(playerName).sendMessage(plugin.strings.get("kitnag"));
		return plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

			public void run() {
				if (ScheduledTasks.gameStatus == 1) {
					plugin.getServer().getPlayer(playerName).sendMessage(plugin.strings.get("kitnag"));
				}
			}
		}, new Long(nagTime * 20), new Long(nagTime * 20));
	}
	public void checkGameEnd(){
		int alivePlayers = 0;
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (Archer.getByName(p.getName()).isAlive()) {
				alivePlayers++;
			}
		}
		if (alivePlayers <= 1) {
			if (plugin.debug) {
				plugin.log.info("Game has ended.");
			}
			// Game is finally over. We have a winner.
			currentLoop = 0;
			gameStatus = 5;
			plugin.serverwide.handleGameEnd();
		}
	}
}
