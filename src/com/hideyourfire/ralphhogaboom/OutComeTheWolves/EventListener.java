package com.hideyourfire.ralphhogaboom.OutComeTheWolves;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.hideyourfire.ralphhogaboom.OutComeTheWolves.Main;

public class EventListener implements Listener {
	
	Main thisInstance;

	public EventListener(Main instance) {
		  thisInstance = instance;
		}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	@EventHandler
	public void onMobTarget(EntityTargetEvent event) {
		Entity mob = event.getEntity();
		if (mob instanceof Wolf) {
			Entity target = event.getTarget();
			if (target instanceof Player) {
				Player player = (Player)target;
				if (player.hasPermission("outcomethewolves.bypass")) {
					event.setCancelled(true);
					if (thisInstance.doDebug()) {
						Main.getPlugin().getLogger().info(player.getName() + " has outcomethewolves.bypass permission node, skipping mob targetting.");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onWolfSpawn(CreatureSpawnEvent event) {
		if (!(event.getLocation().getWorld().getEnvironment().toString().contains("NORMAL"))) {
			return;
		}
		Location loc = event.getLocation();
		Entity entity = event.getEntity();
		int random = randInt(0, 99);
		if (entity.getType() == EntityType.WOLF) {
			if (random < thisInstance.getKey("aggressiveWolvesChance")) {
				if (thisInstance.doDebug()) {
					Main.getPlugin().getLogger().info("Setting wolf angry ...");
				}
				Wolf wolf = (Wolf)entity;
				wolf.setAngry(true);
				for (Entity nearbyEntity : wolf.getNearbyEntities(loc.getX(), loc.getY(), loc.getZ())) {
					if (nearbyEntity instanceof Player) {
						Player target = (Player)nearbyEntity;
						if (target.hasPermission("outcomethewolves.own")) {
							if (thisInstance.doDebug()) {
								Main.getPlugin().getLogger().info(target.getName() + " has outcomethewolves.own permission node, converting wolf into pet.");
							}
							wolf.setOwner(target);
						} else {
							wolf.setTarget(target);
						}
					}
				}
			}
		} else {
			if (random < thisInstance.getKey("changeOtherMobsChance")) {
				if (loc.getY() > 65) {
					event.setCancelled(true);
					entity = loc.getWorld().spawnEntity(loc, EntityType.WOLF);
					if (thisInstance.doDebug()) {
						Main.getPlugin().getLogger().info("Spawning WOLF in " + loc.getWorld().getName() + " near " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
					}

				}
			}
		}
	}
}
