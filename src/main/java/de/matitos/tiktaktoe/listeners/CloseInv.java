package de.matitos.tiktaktoe.listeners;

import de.matitos.tiktaktoe.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class CloseInv implements Listener {
    @EventHandler
    public static void onInvClose(InventoryCloseEvent event){
        Bukkit.getConsoleSender().sendMessage(String.valueOf(event));
        if (event.getView().getTitle().equals("§2Tik Tak Toe")){
            Bukkit.getConsoleSender().sendMessage(Main.inGame.toString());
            Integer getGameID = Integer.valueOf(event.getInventory().getItem(1).getItemMeta().getLocalizedName());
            Main.inGame.get(getGameID).get(1).closeInventory();
            Main.inGame.get(getGameID).get(2).closeInventory();

        }
    }
}
