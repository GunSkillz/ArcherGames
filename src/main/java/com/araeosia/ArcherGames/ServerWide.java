package com.araeosia.ArcherGames;

import com.araeosia.ArcherGames.utils.Archer;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Bruce
 */
public class ServerWide {

	public ArrayList<Archer> livingPlayers = new ArrayList<Archer>();
	public ArcherGames plugin;
	public String winner;
	public Random r = new Random();

	public ServerWide(ArcherGames plugin){
		this.plugin = plugin;
	}

	public void killPlayer(String playerName) {
		livingPlayers.remove(Archer.getByName(playerName));
		Archer.getByName(playerName).kill();
		sendMessageToAllPlayers(String.format(plugin.strings.get("playersleft"), livingPlayers.size()));
	}

	public ArrayList<Archer> getNotReadyPlayers() {
		ArrayList<Archer> archers = new ArrayList<Archer>();
		for (Archer a : ArcherGames.players) {
			if (!a.isReady()) {
				archers.add(a);
			}
		}
		return archers;
	}

	public void sendToLivingPlayers(String message) {
		// Send a message to every living archer
		for (Archer a : livingPlayers) {
			getPlayer(a).sendMessage(message);
		}
	}

	public void sendMessageToAllPlayers(String message) {
		// Send a message to every online player
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.sendMessage(message);
		}
		plugin.log.info("[ArcherGames]: " + message);
	}

	public Player getPlayer(Archer a) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(a.getName())) {
				return p;
			}
		}
		return null;
	}

	public Archer getArcher(Player p) {
		for (Archer a : ArcherGames.players) {
			if (a.getName().equalsIgnoreCase(p.getName())) {
				return a;
			}
		}
		return null;
	}
	public void handleGameStart(){
		// Handle the game start. Populate player inventories, teleport players, etc.
	}
	public void handleGameEnd(){
		// Announce the winner, announce the runners up, give winners their money.
		winner = livingPlayers.get(0).getName();
		sendMessageToAllPlayers(plugin.strings.get("gameended"));
	}

	public void tpToRandomLocation(Player player) {
		int x = r.nextInt(64 + 1) - 32;
		int z = r.nextInt(64 + 1) - 32;
		
		x = player.getWorld().getSpawnLocation().getBlockX() + x;
		z = player.getWorld().getSpawnLocation().getBlockZ() + z;
		
		player.teleport(new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z));
	}
}
