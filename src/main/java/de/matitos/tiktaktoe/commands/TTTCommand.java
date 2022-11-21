package de.matitos.tiktaktoe.commands;

import de.matitos.tiktaktoe.Main;
import de.matitos.tiktaktoe.util.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

import static de.matitos.tiktaktoe.listeners.InvClickListener.getGameID;

public class TTTCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (!(s instanceof Player)) {
         s.sendMessage(Main.getPrefix() + ChatColor.RED + "You have to be a Player");
         return false;
        }
        if (args.length == 0){
            s.sendMessage(Main.getPrefix() + "Usage: /tiktaktoe <Player>");

            return false;
        }

        Player player = (Player) s;







        if (Objects.equals(args[0], "accept")){
            if (!(Main.challange.get(player) == null)){


                List<Player> currentGames = new ArrayList<>();
                currentGames.add(player); //target = 0
                currentGames.add(Main.challange.get(player)); //challaenger = 1

                //create Game
                Main.gameID = Main.gameID + 1;
                Main.inGame.put(Main.gameID, currentGames);

                Random rand = new Random();
                int playerTrunSelect = rand.nextInt(2);

                Main.turn.put(Main.gameID, playerTrunSelect);

                String ChallenerName = Main.inGame.get(Main.gameID).get(1).getName();
                String targetName = Main.inGame.get(Main.gameID).get(0).getName();

                ArrayList<Integer> sets = new ArrayList<>();
                for (int i = 0; i <=8 ; i++) {
                    sets.add(0);
                }
                Main.setsMap.put((Integer) Main.gameID, sets);

                //create Inv
                ArrayList<Integer> field = new ArrayList<Integer>();
                field.add(12);
                field.add(13);
                field.add(14);
                field.add(21);
                field.add(22);
                field.add(23);
                field.add(30);
                field.add(31);
                field.add(32);


                for (int i = 0; i <= 53 ; i++) {
                    if (field.contains(i)) {

                        Main.playInv.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname(ChatColor.DARK_GRAY + "Click to set Item").setLocalizedName("tick").build());
                    }else if(i == 0) { //links target
                        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwningPlayer(player);
                        skull.setItemMeta(meta);
                        Main.playInv.setItem(i, skull);
                    } else if(i == 8) { //rechts Challenger
                        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwningPlayer(Main.challange.get(player));
                        skull.setItemMeta(meta);
                        Main.playInv.setItem(i, skull);
                    } else if (i == 1) {
                        Main.playInv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname("§3").setLocalizedName(String.valueOf(Main.gameID)).build());
                    } else if (i == 9) {
                        if (Main.turn.get(Main.gameID) == 0){
                            Main.playInv.setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(ChallenerName + "'s trun").build());
                            Main.playInv.setItem(17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(ChallenerName + "'s trun").build());
                        } else {
                            Main.playInv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(targetName + "'s trun").build());
                            Main.playInv.setItem(9, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(targetName + "'s trun").build());
                        }
                    } else if (i == 17) {
                        if (Main.turn.get(Main.gameID) == 0){
                            Main.playInv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(targetName + "'s trun").build());
                            Main.playInv.setItem(9, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(targetName + "'s trun").build());
                        } else {
                            Main.playInv.setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname(ChallenerName + "'s trun").build());
                            Main.playInv.setItem(17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayname(ChallenerName + "'s trun").build());
                        }
                    } else {

                        Main.playInv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname("§3").build());
                    }

                }

                //target -> links(0) -> 0 -> Dia
                //Challanger -> rechts(9) -> 1 -> Emerald



                player.openInventory(Main.playInv);
                Main.challange.get(player).openInventory(Main.playInv);

                Main.challange.remove(player);


            } else {
                player.sendMessage(Main.getPrefix() + "You weren't challenged from anyone.");
            }
        } else{
            Player target = Bukkit.getServer().getPlayer(args[0]);

            if (target == null){
                player.sendMessage(Main.getPrefix() + "That player is not online!");
                return false;
            }

            Main.challange.put(target, player);

            target.sendMessage(Main.getPrefix() + ChatColor.GREEN + player.getName() + ChatColor.DARK_AQUA + " has challenged you!");
            TextComponent challengeMsg = new TextComponent(Main.getPrefix() + "Type " + ChatColor.BOLD + "/ticktaktoe accept " +ChatColor.RESET + ChatColor.DARK_AQUA + "or " + ChatColor.BOLD + ChatColor.UNDERLINE + "Click here to accept");
            challengeMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ttt accept"));
            target.spigot().sendMessage(challengeMsg);
            player.sendMessage(Main.getPrefix() + "You have challenged " + ChatColor.GREEN + target.getName());
        }















        return false;
    }
}
