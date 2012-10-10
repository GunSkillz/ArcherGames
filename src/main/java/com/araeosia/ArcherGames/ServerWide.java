
package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.utils.Archer;
import java.util.ArrayList;
import org.bukkit.entity.Player;

/**
 *
 * @author Bruce
 */
public class ServerWide {
	
	public ArcherGames plugin;
	public ArrayList<Player> livingPlayers;
	public ArrayList<Player> deadPlayers;
	
	public ServerWide(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	public void killPlayer(Player player){
		livingPlayers.remove(player);
		deadPlayers.add(player);
	}
	public ArrayList<Archer> getNotReadyPlayers(){
	ArrayList<Archer> archers = new ArrayList<Archer>();
	   for(Archer a : ArcherGames.players){
		if(!a.isReady()){
		    archers.add(a);
		}
	    }
		return archers;
	}
	public void sendToLivingPlayers(String message){
		// Send a message to every living archer
		for(Player p : livingPlayers){
			p.sendMessage(message);
		}
	}
	public void sendMessageToAllPlayers(String message){
		// Send a message to every online player
		for(Player p : plugin.getServer().getOnlinePlayers()){
			p.sendMessage(message);
		}
	}
	
	public Player getPlayer(Archer a){
	    for(Player p : plugin.getServer().getOnlinePlayers()){
		if(p.getName().equalsIgnoreCase(a.getName())){
		    return p;
		}
	    }
	    return null;
	}
}
