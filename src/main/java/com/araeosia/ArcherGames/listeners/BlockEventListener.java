package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import com.araeosia.ArcherGames.ScheduledTasks;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockEventListener implements Listener {

	public ArcherGames plugin;

	public BlockEventListener(ArcherGames plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockBreak(final BlockBreakEvent event){
		if(ScheduledTasks.gameStatus==1 && !event.getPlayer().hasPermission("archergames.overrides.blockedit")){
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.strings.get("noblockediting"));
		}
	}
	@EventHandler
	public void onBlockBurn(final BlockBurnEvent event){
		if(ScheduledTasks.gameStatus==1){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent event){
		if(ScheduledTasks.gameStatus==1 && !event.getPlayer().hasPermission("archergames.overrides.blockedit")){
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.strings.get("noblockediting"));
		}
	}
	@EventHandler
	public void onBlockIgnite(final BlockIgniteEvent event){
		if(ScheduledTasks.gameStatus==1 && !event.getPlayer().hasPermission("archergames.overrides.blockedit")){
			event.setCancelled(true);
			event.getPlayer().sendMessage(plugin.strings.get("noblockediting"));
		}
	}
	@EventHandler
	public void onInventoryOpen(final InventoryOpenEvent event){
		if(ScheduledTasks.gameStatus==1 && !event.getPlayer().hasPermission("archergames.overrides.invedit")){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockInteract(final PlayerInteractEvent event){
		if(event.hasBlock()){
			if(event.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign) event.getClickedBlock().getState();
				if(sign.getLine(1)=="§3[Enchant]"){
					// Line 2: Any, Line 3: Enchantment:Level Line 4: Price
					double price = new Double(sign.getLine(4).substring(1));
					if(plugin.econ.hasBalance(event.getPlayer().getName(), price)){
						String[] data = sign.getLine(3).split(":");
					}
				}else if(sign.getLine(2)=="§3[Buy]"){
					// Line 2: Quantity, Line 3: Item name, Line 4: Price
				}
			}
		}
	}
}
