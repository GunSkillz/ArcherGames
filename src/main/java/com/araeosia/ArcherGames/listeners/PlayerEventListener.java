package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import com.araeosia.ArcherGames.utils.Archer;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventListener implements Listener {

	public ArcherGames plugin;
	public int howLongToWait; // How long do we wait?
	public HashMap<String, Integer> playersDisconnected;

	public PlayerEventListener(ArcherGames plugin) {
		this.plugin = plugin;
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLoginEvent(final PlayerLoginEvent event) {
		Archer a = new Archer(event.getPlayer().getName());
		ArcherGames.players.add(a);
		event.getPlayer().sendMessage(plugin.strings.get("joinedgame").format(event.getPlayer().getName(), plugin.strings.get("servername")));
		if (playersDisconnected.containsKey(event.getPlayer().getName())) {
			plugin.getServer().getScheduler().cancelTask(playersDisconnected.get(event.getPlayer().getName())); // Cancel that kill task.
		}
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void onPlayerChatEvent(final AsyncPlayerChatEvent event) {
		// If the player is allowed to talk, pass their message on, Else cancel the event
		Archer archer = Archer.getByName(event.getPlayer().getName());
		if (!archer.canTalk && !event.getPlayer().hasPermission("archergames.chat.override")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.strings.get("nochat"));
		}
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void onDamageEvent(final EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (ScheduledTasks.gameStatus == 1 || ScheduledTasks.gameStatus == 2 || ScheduledTasks.gameStatus == 5 || !(plugin.serverwide.getArcher(player).isAlive())) {
				if (event.getCause() != EntityDamageEvent.DamageCause.VOID) {
					event.setCancelled(true);
				}
			}
		}
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void onDeathEvent(final PlayerDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (!(ScheduledTasks.gameStatus == 1) || !(ScheduledTasks.gameStatus == 2) || !(ScheduledTasks.gameStatus == 5) || (plugin.serverwide.getArcher(player).isAlive())) {
				plugin.serverwide.killPlayer(event.getEntity().getName());
				// TODO: Get the cause. If it's a player, give them some points.
			}
		}
	}

	@EventHandler
	public void onQuitEvent(final PlayerQuitEvent event) {
		if (!Archer.getByName(event.getPlayer().getName()).isAlive() && !event.getPlayer().hasPermission("archergames.quitkill.override")) {
			int taskID = plugin.scheduler.playerKillCountdown(event.getPlayer().getName());
		}
	}
}
