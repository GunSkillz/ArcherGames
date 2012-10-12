package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import com.araeosia.ArcherGames.utils.Archer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerEventListener implements Listener {

	public ArcherGames plugin;

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
	}
	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void onPlayerChatEvent(final AsyncPlayerChatEvent event) {
		// If the player is allowed to talk, pass their message on, Else cancel the event
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
				plugin.serverwide.killPlayer(event.getEntity());
				// TODO: Get the cause. If it's a player, give them some points.
			}
		}
	}
}
