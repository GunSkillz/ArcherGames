package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;

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
		
	}
}
