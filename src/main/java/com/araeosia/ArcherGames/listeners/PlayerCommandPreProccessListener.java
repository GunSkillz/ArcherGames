package com.araeosia.ArcherGames.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.event.EventHandler;


public class PlayerCommandPreProccessListener implements Listener {
    
    public ArcherGames plugin;
	
    public PlayerCommandPreProccessListener(ArcherGames plugin) {
        this.plugin = plugin;
    }

    /**
     * 
     * @param event
     */
    @EventHandler
    public void onCommandPreProccessEvent(final PlayerCommandPreprocessEvent event){
        
    }
    
}
