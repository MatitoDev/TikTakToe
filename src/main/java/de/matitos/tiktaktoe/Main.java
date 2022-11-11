package de.matitos.tiktaktoe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Properties;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        final Properties properties = new Properties();
        try {
            properties.load(this.getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + properties.getProperty("plugin") + "enabled");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Version: " + properties.getProperty("version"));
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "");

    }

    @Override
    public void onDisable() {
    }
    public String getPrefix(){
        return ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TicTakToe" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_AQUA;
    }
}
