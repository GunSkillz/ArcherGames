package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.utils.Archer;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

public class ScheduledTasks {

	public ArcherGames plugin;
	public static int gameStatus;
	public int currentLoop; // In loops
	public int preGameCountdown; // Time before anything starts. Players choose kits.
	public int gameInvincibleCountdown; // Time while players are invincible.
	public int gameOvertimeCountdown; // Time until overtime starts.
	public int shutdownTimer; // Time after the game ends until the server shuts down.
	public int minPlayersToStart;

	public ScheduledTasks(ArcherGames plugin) {
		this.plugin = plugin;
	}

	/**
	 * This will do the action every second (20 TPS)
	 */
	public void everySecondCheck() {
		int Success = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			public void run() {
				switch (gameStatus) {
					case 1:
						if(plugin.debug){ plugin.log.info((preGameCountdown-currentLoop)+" seconds until game starts."); }
						// Pre-game
						switch((preGameCountdown-currentLoop)){
							case 60:
								ServerWide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format("1 minute"));
								break;
							case 30:
								ServerWide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format("30 seconds"));
								break;
							case 15:
								ServerWide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format("15 seconds"));
								break;
							case 10:
							case 9:
							case 8:
							case 7:
							case 6:
							case 5:
							case 4:
							case 3:
							case 2:
							case 1:
								ServerWide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format((preGameCountdown-currentLoop) + " second(s)"));
								break;
						}
						if (currentLoop >= preGameCountdown) {
							if(plugin.debug){ plugin.log.info("Attempting to start..."); }
							// Time to start.
							if (ServerWide.livingPlayers.size() < minPlayersToStart) { // There aren't enough players.
								ServerWide.sendMessageToAllPlayers(plugin.strings.get("startnotenoughplayers"));
							} else { // There's enough players, let's start!
								ServerWide.sendMessageToAllPlayers(plugin.strings.get("starting"));
								gameStatus = 2;
//								for(Archer a : plugin.getArchers) for(ItemStack is : plugin.kits.get(Archer.getKit())) ServerWide.getPlayer(a).getInventory().add(is);
								for(Player p : plugin.getServer().getOnlinePlayers()){
									if(ServerWide.getArcher(p).isReady){
										p.teleport(plugin.startPosition);
									}
								}
							}
							currentLoop = -1;
						}
						currentLoop++;
						break;
					case 2:
						// Invincibility
						if(plugin.debug){ plugin.log.info((gameInvincibleCountdown-currentLoop)+" seconds until invincibility ends."); }
						if (currentLoop >= gameInvincibleCountdown) {
							if(plugin.debug){ plugin.log.info("Invincibility has ended."); }
							// Invincibility is over.
							ServerWide.sendMessageToAllPlayers(plugin.strings.get("invincibilityend"));
							gameStatus = 3;
							currentLoop = -1;
						}
						currentLoop++;
						break;
					case 3:
						// Game time
						if(plugin.debug){ plugin.log.info((gameOvertimeCountdown-currentLoop)+" seconds until overtime starts."); }
						if (currentLoop >= gameOvertimeCountdown) {
							if(plugin.debug){ plugin.log.info("Overtime has started."); }
							// Game time is up.
							ServerWide.sendMessageToAllPlayers(plugin.strings.get("overtimestart"));
							for (Archer a : ServerWide.livingPlayers) {
								ServerWide.getPlayer(a).teleport(plugin.startPosition);
							}
							gameStatus = 4;
							currentLoop = -1;
							// TODO: World border shrinking.
						}
						currentLoop++;
						break;
					case 4:
						// Overtime
						if (ServerWide.livingPlayers.size() <= 1) {
							if(plugin.debug){ plugin.log.info("Game has ended."); }
							// Game is finally over. We have a winner.
							ServerWide.sendMessageToAllPlayers(plugin.strings.get("gameended"));
							gameStatus = 5;
							currentLoop = -1;
						}
						currentLoop++;
						break;
					case 5:
						// Game finished, waiting for reset to pre-game
						if(plugin.debug){ plugin.log.info((shutdownTimer-currentLoop)+" seconds until server reboots."); }
						if (currentLoop >= shutdownTimer) {
							if(plugin.debug){ plugin.log.info("Kicking all players, then shutting down."); }
							for (Player p : plugin.getServer().getOnlinePlayers()) {
								p.kickPlayer(plugin.strings.get("serverclosekick"));
							}
							plugin.getServer().shutdown();
						}
						break;
				}
			}
		}, 20L, 20L);
		if(plugin.debug){ plugin.log.info("Task ID is "+Success); }
	}
}
