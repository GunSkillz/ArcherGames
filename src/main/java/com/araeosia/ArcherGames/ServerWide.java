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
	public ArrayList<String> livingPlayers;
	public ArrayList<String> deadPlayers;
	
	public ServerWide(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	public void killPlayer(Player player){
		livingPlayers.remove(player.getName());
		deadPlayers.add(player.getName());
	}
	public static ArrayList<Archer> getNotReadyPlayers(){
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
		for(String pName : livingPlayers){
			plugin.getServer().getPlayer(pName).sendMessage(message);
		}
	}
	public void sendMessageToAllPlayers(String message){
		// Send a message to every online player
		for(Player p : plugin.getServer().getOnlinePlayers()){
			p.sendMessage(message);
		}
	}
}
