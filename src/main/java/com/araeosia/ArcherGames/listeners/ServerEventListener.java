package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import com.araeosia.ArcherGames.utils.Time;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerEventListener implements Listener {
	public ArcherGames plugin;
	
	public ServerEventListener(ArcherGames plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void onPing(final ServerListPingEvent event){
		String motdOutput = new String();
		switch(ScheduledTasks.gameStatus){
			case 1:
				String timeLeft = Time.getShortString(plugin.scheduler.preGameCountdown-plugin.scheduler.currentLoop);
				motdOutput = String.format(plugin.strings.get("pingreply1"), timeLeft);
				if(plugin.debug){
					plugin.log.info(motdOutput);
				}
				break;
			case 2:
				motdOutput = plugin.strings.get("pingreply2");
				break;
			case 3:
				motdOutput = plugin.strings.get("pingreply3");
				break;
			case 4:
				motdOutput = plugin.strings.get("pingreply4");
				break;
			case 5:
				motdOutput = String.format(plugin.strings.get("pingreply5"), plugin.serverwide.winner);
				break;
		}
		event.setMotd(motdOutput);
	}
}
