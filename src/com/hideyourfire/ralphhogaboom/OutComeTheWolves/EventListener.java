package com.hideyourfire.ralphhogaboom.OutComeTheWolves;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EventListener implements Listener {
	
	@EventHandler
	public void onWolfSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity entity = event.getEntity();
		if (entity instanceof Wolf) {
			Wolf wolf = (Wolf)event.getEntity();
			wolf.setAngry(true);
		}
	}
}
