package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class PlayerDamageEventListener implements Listener{
	
	public ArcherGames plugin;
	
	public PlayerDamageEventListener(ArcherGames plugin){
		this.plugin = plugin;
	}

	/**
	 * 
	 * @param event 
	 */
	@EventHandler
	public void onDamageEvent(final PlayerDamageEventListener event){
		
	}
}
