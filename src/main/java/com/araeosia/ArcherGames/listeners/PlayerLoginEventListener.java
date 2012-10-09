package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.event.player.PlayerLoginEvent;


public class PlayerLoginEventListener {
    
    public ArcherGames plugin;
    
    public PlayerLoginEventListener(ArcherGames plugin){
        this.plugin = plugin;
    }
    
    public void onLoginEvent(final PlayerLoginEvent event){
        
    }
}
