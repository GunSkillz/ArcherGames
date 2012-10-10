package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import com.araeosia.ArcherGames.ServerWide;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class PlayerDeathEventListener implements Listener{
	
	public ArcherGames plugin;
	
	public PlayerDeathEventListener(ArcherGames plugin){
		this.plugin = plugin;
	}

	/**
	 * 
	 * @param event 
	 */
	@EventHandler
	public void onDeathEvent(final PlayerDeathEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(!(ScheduledTasks.gameStatus == 1) || !(ScheduledTasks.gameStatus == 2) || !(ScheduledTasks.gameStatus == 5) || (ServerWide.getArcher(player).isAlive())){
					ServerWide.killPlayer(event.getEntity());
			}
		}
	}
}

