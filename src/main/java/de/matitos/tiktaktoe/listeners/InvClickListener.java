package de.matitos.tiktaktoe.listeners;

import de.matitos.tiktaktoe.Main;
import de.matitos.tiktaktoe.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

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
                        addset(event.getSlot(), "challanger");
                        checkWin();
                        Main.turn.put(Main.gameID, 0);
                        Main.playInv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(challangerName + "'s trun").build());
                        Main.playInv.setItem(9, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(challangerName + "'s trun").build());
                    } else if (turnID == 0) {
                        Main.playInv.setItem(event.getSlot(), new ItemBuilder(Material.EMERALD).setDisplayname(ChatColor.GREEN + "Set from " + targetName).build());
                        addset(event.getSlot(), "target");
                        checkWin();
                        Main.turn.put(Main.gameID, 1);
                        Main.playInv.setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(targetName + "'s trun").build());
                        Main.playInv.setItem(17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(targetName + "'s trun").build());
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
    public static void addset(Integer slot, String player){

        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(12);
        slots.add(13);
        slots.add(14);
        slots.add(21);
        slots.add(22);
        slots.add(23);
        slots.add(30);
        slots.add(31);
        slots.add(32);

        int slotIndex = slots.indexOf(slot);

        ArrayList<Integer> sets;
        sets = (ArrayList<Integer>) Main.setsMap.get((Integer) getGameID());

        switch (player){
            case "challanger":
                sets.set(slotIndex, 1);

                break;
            case "target":
                sets.set(slotIndex, 2);

                break;
        }



    }

    public static void checkWin(){

        ArrayList<Integer> sets;
        ArrayList<Player> players;
        sets = (ArrayList<Integer>) Main.setsMap.get((Integer) getGameID());

        players = (ArrayList<Player>) Main.inGame.get((Integer) getGameID());

        for (int i = 1; i <=2 ; i++) {

            if (sets.get(0) == i && sets.get(1) == i && sets.get(2) == i){ // First line
                winnerLoop(i);
            } else if (sets.get(3) == i && sets.get(4) == i && sets.get(5) == i) { // Second line
                winnerLoop(i);
            } else if (sets.get(6) == i && sets.get(7) == i && sets.get(8) == i) { // third line
                winnerLoop(i);
            } else if (sets.get(0) == i && sets.get(3) == i && sets.get(6) == i) { //first column
                winnerLoop(i);
            } else if (sets.get(1) == i && sets.get(4) == i && sets.get(7) == i) { //second column
                winnerLoop(i);
            } else if (sets.get(2) == i && sets.get(5) == i && sets.get(8) == i) { // third column
                winnerLoop(i);
            } else if (sets.get(0) == i && sets.get(4) == i && sets.get(8) == i) { //first diagonal
                winnerLoop(i);
            } else if (sets.get(2) == i && sets.get(4) == i && sets.get(6) == i) { //second diagonal
                winnerLoop(i);
            }
        }


    }

    private static void winnerLoop(Integer playerId){
        ArrayList<Player> players;
        players = (ArrayList<Player>) Main.inGame.get((Integer) getGameID());
        for (Player player:players) {
            player.closeInventory();
            if (playerId == 1) {
                player.sendMessage(Main.getPrefix() + "Player " + players.get(1).getName() + " wins the Game");
            } else if (playerId == 2) {
                player.sendMessage(Main.getPrefix() + "Player " + players.get(0).getName() + " wins the Game");
            } else {
                player.sendMessage("Game is Broken");
            }

        }
    }

}
