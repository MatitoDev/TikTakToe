package de.matitos.tiktaktoe.listeners;

import de.matitos.tiktaktoe.Main;
import de.matitos.tiktaktoe.game.GameMechanics;
import de.matitos.tiktaktoe.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClickListener implements Listener {
    @EventHandler
    public static Object getGameID(){
        return Integer.valueOf(Main.playInv.getItem(1).getItemMeta().getLocalizedName());
    }
    @EventHandler
    public static void onInvClick(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        if (!(event.getView().getTitle().equals("§2Tik Tak Toe"))) return;
        event.setCancelled(true);
        if (event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
            return;
        }






        if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
            if ("tick".equals(event.getCurrentItem().getItemMeta().getLocalizedName())) {

                Integer turnID = Main.turn.get((Integer) getGameID());
                String challangerName = Main.inGame.get((Integer)getGameID()).get(1).getName();
                String targetName = Main.inGame.get((Integer) getGameID()).get(0).getName();
                if (event.getWhoClicked() == Main.inGame.get((Integer) getGameID()).get(turnID)){
                    if(turnID == 1){
                        Main.playInv.setItem(event.getSlot(), new ItemBuilder(Material.DIAMOND).setDisplayname(ChatColor.AQUA + "Set from " + challangerName).build());
                        GameMechanics.addset(event.getSlot(), "challanger");
                        GameMechanics.checkWin();
                        Main.turn.put(Main.gameID, 0);
                        Main.playInv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(ChatColor.AQUA + challangerName + "'s trun").build());
                        Main.playInv.setItem(9, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(ChatColor.AQUA + challangerName + "'s trun").build());
                    } else if (turnID == 0) {
                        Main.playInv.setItem(event.getSlot(), new ItemBuilder(Material.EMERALD).setDisplayname(ChatColor.GREEN + "Set from " + targetName).build());
                        GameMechanics.addset(event.getSlot(), "target");
                        GameMechanics.checkWin();
                        Main.turn.put(Main.gameID, 1);
                        Main.playInv.setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(ChatColor.GREEN + targetName + "'s trun").build());
                        Main.playInv.setItem(17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(ChatColor.GREEN + targetName + "'s trun").build());
                    }
                }

                //target -> links -> 0 -> Dia
                //Challanger -> rechts -> 1 -> Emerald


            }





            //ToDo more beautiful and clear Code / commend code
            //ToDo GiveUp Button && check or Cancel inv CloseEvent
            //ToDo spawn Rocket by winning player
            //ToDo SFX adden

        }


    }

}
