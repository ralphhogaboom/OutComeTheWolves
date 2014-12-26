package com.hideyourfire.ralphhogaboom.OutComeTheWolves;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import com.hideyourfire.ralphhogaboom.OutComeTheWolves.*;

public class EventListener implements Listener {
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	@EventHandler
	public void onMobTarget(EntityTargetEvent event) {
		Entity entity = event.getEntity();
		Entity target = event.getTarget();
		if (target instanceof Player) {
			Player player = (Player)target;
			if (player.getName().equalsIgnoreCase("mrcreeper") || player.getName().equalsIgnoreCase("cyclone56724")) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onWolfSpawn(CreatureSpawnEvent event) {
		Location loc = event.getLocation();
		Entity entity = event.getEntity();
		int random = randInt(0, 99);
		
		if (entity.getType() == EntityType.WOLF) {
			if (Main.getPlugin().getConfig().getString("notifications").equalsIgnoreCase("yes")) {
				Main.getPlugin().getLogger().info(entity.getType() + " spawned near " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
			}
			if (random < Main.getKey("aggressiveWolvesChance")) {
				Wolf wolf = (Wolf)entity;
				wolf.setAngry(true);
				// get nearby entities
				for (Entity nearbyEntity : wolf.getNearbyEntities(loc.getX(), loc.getY(), loc.getZ())) {
					if (nearbyEntity instanceof Player) {
						Player target = (Player)nearbyEntity;
						if (target.getName().equalsIgnoreCase("mrcreeper") || target.getName().equalsIgnoreCase("cyclone56724")) {
							// Do nothing.
							wolf.setOwner(target);
						} else {
							wolf.setTarget((LivingEntity) nearbyEntity);
						}
					}
				}
			}
		} else {
			if (random < Main.getPlugin().getKey("changeOtherMobsChance")) {
				if (loc.getY() > 65) {
					event.setCancelled(true);
					entity = loc.getWorld().spawnEntity(loc, EntityType.WOLF);
				}
			}
		}
	}
}
