package com.araeosia.ArcherGames;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

public class ScheduledTasks {

	public ArcherGames plugin;
	public int gameStatus;
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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			public void run() {
				switch (gameStatus) {
					case 1:
						// Pre-game
						if((preGameCountdown-currentLoop)==60){
							// 60 seconds left.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format("1 minute"));
						}
						if((preGameCountdown-currentLoop)==15){
							// 15 seconds left.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format("15 seconds"));
						}
						if((preGameCountdown-currentLoop)<=10 && (preGameCountdown-currentLoop)!=0){
							// Less than 10 but not 0 seconds left.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("starttimeleft").format((preGameCountdown-currentLoop) + " second(s)"));
						}
						if (currentLoop >= preGameCountdown) {
							// Time to start.
							if (plugin.serverwide.livingPlayers.size() < minPlayersToStart) { // There aren't enough players.
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("startnotenoughplayers"));
							} else { // There's enough players, let's start!
								plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("starting"));
								gameStatus = 2;
							}
							currentLoop = -1;
						}
						currentLoop++;
						break;
					case 2:
						// Invincibility
						if (currentLoop >= gameInvincibleCountdown) {
							// Invincibility is over.
							plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("invincibilityend"));
							gameStatus = 3;
							currentLoop = -1;
						}
						currentLoop++;
						break;
					case 3:
						// Game time
						if (currentLoop >= gameOvertimeCountdown) {
							// Game time is up.
							plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("overtimestart"));
							for (String playerName : plugin.serverwide.livingPlayers) {
								plugin.getServer().getPlayer(playerName).teleport(plugin.startPosition);
							}
							gameStatus = 4;
							currentLoop = -1;
							// TODO: World border shrinking.
						}
						currentLoop++;
						break;
					case 4:
						// Overtime
						if (plugin.serverwide.livingPlayers.size() <= 1) {
							// Game is finally over. We have a winner.
							plugin.serverwide.sendMessageToAllPlayers(plugin.strings.get("gameended"));
							gameStatus = 5;
							currentLoop = -1;
						}
						currentLoop++;
						break;
					case 5:
						// Game finished, waiting for reset to pre-game
						if (currentLoop >= shutdownTimer) {
							for (Player p : plugin.getServer().getOnlinePlayers()) {
								p.kickPlayer(plugin.strings.get("serverclosekick"));
							}
							plugin.getServer().shutdown();
						}
						break;
				}
			}
		}, 1 * 20, 1 * 20);
	}
}
