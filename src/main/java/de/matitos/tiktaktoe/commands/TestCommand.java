package de.matitos.tiktaktoe.commands;

import de.matitos.tiktaktoe.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = Bukkit.getServer().getPlayer(args[0]);
        target.sendMessage(Main.getPrefix() + "test");
        return false;
    }
}
