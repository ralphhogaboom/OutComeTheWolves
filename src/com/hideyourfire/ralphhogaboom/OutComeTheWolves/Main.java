package com.hideyourfire.ralphhogaboom.OutComeTheWolves;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {
	private static Plugin plugin;
	private int aggressiveWolvesChance;
	private int changeOtherMobsChance;
	private boolean debug = false;
	private boolean alwaysHunting = false;
	
	public void onEnable() {	
		plugin = this;
		registerEvents(this, new EventListener(this));
		this.saveDefaultConfig(); // For first run, save default config file.
		this.getConfig();
		aggressiveWolvesChance = this.getConfig().getInt("aggressiveWolves");
		changeOtherMobsChance = this.getConfig().getInt("otherMobsIntoWolves");
		debug = this.getConfig().getBoolean("debug");
		alwaysHunting = this.getConfig().getBoolean("alwaysHunting");
		Main.getPlugin().getLogger().info("Debug is " + doDebug());
		
		if (doDebug()) {
			Main.getPlugin().getLogger().info("alwaysHunting is " + alwaysHunting);
		}
		
		if (alwaysHunting) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					doWolvesCheckForPlayers();
				}
			}, 0L, 400);
		}
		
	}
	
	private void doWolvesCheckForPlayers() {
		// Loop through mobs, if wolf, look for players to target
		if (doDebug()) {
			Main.getPlugin().getLogger().info("Now looking for wolves near players to target ...");
		}
		for (World world : plugin.getServer().getWorlds()) {
			for (Entity entity : plugin.getServer().getWorld(world.getName()).getEntities()) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (livingEntity instanceof Wolf) {
						if (doDebug()) {
							Main.getPlugin().getLogger().info("Found a wolf; looking for players nearby.");
						}
						Wolf wolf = (Wolf) livingEntity;
						Location loc = wolf.getLocation();
						wolf.setAngry(true);
						for (Entity nearbyEntity : wolf.getNearbyEntities(loc.getX(), loc.getY(), loc.getZ())) {
							if (nearbyEntity instanceof Player) {
								if (doDebug()) {
									Main.getPlugin().getLogger().info("... and we found a player nearby! Now to set it to ATTACK.");
								}
								Player target = (Player)nearbyEntity;
								wolf.setTarget(target);
								if (target.hasPermission("outcomethewolves.own")) {
									if (doDebug()) {
										Main.getPlugin().getLogger().info(target.getName() + " has outcomethewolves.own permission node, converting wolf into pet.");
									}
									wolf.setOwner(target);
								} else {
									wolf.setTarget(target);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void registerEvents(org.bukkit.plugin.Plugin plugin, Listener...listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	public void onDisable() {
		plugin = null; // Stops memory leaks.
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public int getKey(String key) {
		if (key.equalsIgnoreCase("aggressiveWolvesChance")) {
			return aggressiveWolvesChance;
		}
		if (key.equalsIgnoreCase("changeOtherMobsChance")) {
			return changeOtherMobsChance;
		}
		return 0;
	}
	
	public void setKey(String key, int value) {
		if (key.equalsIgnoreCase("aggressiveWolvesChance")) {
			aggressiveWolvesChance = value;
		}
		if (key.equalsIgnoreCase("changeOtherMobsChance")) {
			changeOtherMobsChance = value;
		}
	}
	
	public boolean doDebug() {
		return debug;
	}
}