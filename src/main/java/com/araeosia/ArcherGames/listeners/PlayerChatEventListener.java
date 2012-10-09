package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class PlayerChatEventListener implements Listener {

	public ArcherGames plugin;
	
	public PlayerChatEventListener(ArcherGames plugin) {
		this.plugin = plugin;
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerChatEvent(final AsyncPlayerChatEvent event){
            
	}
}
