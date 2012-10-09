package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.utils.Archer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;


public class PlayerLoginEventListener implements Listener{
    
    public ArcherGames plugin;
    
    public PlayerLoginEventListener(ArcherGames plugin){
        this.plugin = plugin;
    }
    
    /**
     * 
     * @param event 
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onLoginEvent(final PlayerLoginEvent event){
        Archer a = new Archer(event.getPlayer().getName());
        ArcherGames.players.add(a);
    }
}
