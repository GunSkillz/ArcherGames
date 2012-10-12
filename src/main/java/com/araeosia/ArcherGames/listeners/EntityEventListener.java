package com.araeosia.ArcherGames.listeners;

import com.araeosia.ArcherGames.ArcherGames;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class EntityEventListener implements Listener {

	public ArcherGames plugin;

	public EntityEventListener(ArcherGames plugin) {
		this.plugin = plugin;
	}

	// TODO: Mob targeting triggers, mob damage triggers.
	
	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 2F);
			if(plugin.configToggles.get("arrowDelete")){
				event.getEntity().remove();
			}
		}
	}
}
