package com.hideyourfire.ralphhogaboom.OutComeTheWolves;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Plugin plugin;
	private int aggressiveWolvesChance;
	private int changeFromOtherMobsChance;
	private String notifications;

	public void onEnable() {	
		plugin = this;
		registerEvents(this, new EventListener());
		this.saveDefaultConfig(); // For first run, save default config file.
		this.getConfig();
		aggressiveWolvesChance = this.getConfig().getInt("aggressiveWolvesChance");
		changeFromOtherMobsChance = this.getConfig().getInt("changeFromOtherMobsChance");
		notifications = this.getConfig().getString("notifications");
	}
	
	private void registerEvents(org.bukkit.plugin.Plugin plugin, Listener...listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	public void onDisable() {
		this.getConfig().set("aggressiveWolvesChance", aggressiveWolvesChance);
		this.getConfig().set("changeFromOtherMobsChance", changeFromOtherMobsChance);
		this.getConfig().set("notifications", notifications);
		plugin = null; // Stops memory leaks.
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

	public void setNotifications(String notify) {
		if (notify.equalsIgnoreCase("yes")) {
			notifications = "yes";
		}
		if (notify.equalsIgnoreCase("no")) {
			notifications = "no";
		}
	}
	
	public boolean getNotifications(String notify) {
		if (notifications.equalsIgnoreCase("yes")) {
			return true;
		}
		return false;
	}

	public int getKey(String key) {
		if (key.equalsIgnoreCase("aggressiveWolvesChance")) {
			return aggressiveWolvesChance;
		}
		if (key.equalsIgnoreCase("changeFromOtherMobsChance")) {
			return changeFromOtherMobsChance;
		}
		return 0;
	}
	
	public void setKey(String key, int value) {
		if (key.equalsIgnoreCase("aggressiveWolvesChance")) {
			aggressiveWolvesChance = value;
		}
		if (key.equalsIgnoreCase("changeFromOtherMobsChance")) {
			changeFromOtherMobsChance = value;
		}
	}
	
}
