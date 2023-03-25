package de.matitos.tiktaktoe.game;

import de.matitos.tiktaktoe.Main;
import de.matitos.tiktaktoe.commands.TTTCommand;
import de.matitos.tiktaktoe.listeners.InvClickListener;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;

public class GameMechanics {
    public static void addset(Integer slot, String player){

        ArrayList<Integer> slots = new ArrayList<>();
        TTTCommand.createFieldNumbers(slots);

        int slotIndex = slots.indexOf(slot);

        ArrayList<Integer> sets;
        sets = (ArrayList<Integer>) Main.setsMap.get((Integer) InvClickListener.getGameID());

        switch (player){
            case "challenger":
                sets.set(slotIndex, 1);

                break;
            case "target":
                sets.set(slotIndex, 2);

                break;
        }



    }

    public static void checkWin(){

        ArrayList<Integer> sets;
        sets = (ArrayList<Integer>) Main.setsMap.get((Integer) InvClickListener.getGameID());


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
        players = (ArrayList<Player>) Main.inGame.get((Integer) InvClickListener.getGameID());

        for (Player player:players) {
            player.closeInventory();
            if (playerId == 1) {
                player.sendMessage(Main.getPrefix() + "Player " + players.get(1).getName() + " wins the Game");
                spawnFireworks(players.get(1).getLocation());
            } else if (playerId == 2) {
                player.sendMessage(Main.getPrefix() + "Player " + players.get(0).getName() + " wins the Game");
                spawnFireworks(players.get(2).getLocation());
            } else {
                player.sendMessage("Game is Broken");
            }

        }
    }

    public static void spawnFireworks(Location location){
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwMeta = fw.getFireworkMeta();

        fwMeta.setPower(2);

        fwMeta.addEffect(FireworkEffect.builder().withColor(Color.GREEN).flicker(true).build());

        fw.setFireworkMeta(fwMeta);
        fw.detonate();

        for(int i = 0;i<4; i++){
            Firework fw2 = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwMeta);
        }
    }
}
