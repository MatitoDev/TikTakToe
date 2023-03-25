package de.matitos.tiktaktoe;

import de.matitos.tiktaktoe.commands.TTTCommand;
import de.matitos.tiktaktoe.listeners.InvClickListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class Main extends JavaPlugin {

    public static Inventory playInv = Bukkit.createInventory(null, 6*9, "§2Tik Tak Toe");
    public static Integer gameID = 0;
    public static Map<Player, Player> challenge = new HashMap<>();
    public static Map<Integer, Integer> turn = new HashMap<>();
    public static Map<Integer, List<Player>> inGame = new HashMap<>();
    public static Map<Integer, List<Integer>> setsMap = new HashMap<>();

    @Override
    public void onEnable() {

        final Properties properties = new Properties();
        try {
            properties.load(this.getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + properties.getProperty("plugin") + " enabled");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Version: " + properties.getProperty("version"));
        Bukkit.getConsoleSender().sendMessage("");

        //commands
        getCommand("tiktaktoe").setExecutor(new TTTCommand());

        //Listeners
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InvClickListener(), this);

    }

    @Override
    public void onDisable() {
    }
    public static String getPrefix(){
        return ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TicTakToe" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_AQUA;
    }
}
