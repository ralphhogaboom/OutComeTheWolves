package com.hideyourfire.ralphhogaboom.OutComeTheWolves;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Plugin plugin;
	private int aggressiveWolvesChance;
	private int changeOtherMobsChance;

	public void onEnable() {	
		plugin = this;
		registerEvents(this, new EventListener(this));
		this.saveDefaultConfig(); // For first run, save default config file.
		this.getConfig();
		aggressiveWolvesChance = this.getConfig().getInt("aggressiveWolves");
		changeOtherMobsChance = this.getConfig().getInt("otherMobsIntoWolves");
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
}