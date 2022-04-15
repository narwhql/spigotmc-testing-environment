package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.player.SpigotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ChatColorCommand implements CommandExecutor {
    private final List<String> validColors = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0 || !validColors.contains(args[0].toLowerCase())) {
            player.sendMessage("§cYou need to select a valid color in the first argument.");
            return false;
        }

        String color = args[0].toLowerCase();

        SpigotPlayer spigotPlayer = Main.getInstance().getPlayerManager().getPlayer(player.getName());

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> spigotPlayer.setChatColor(ChatColor.getByChar(color)));

        player.sendMessage("§aYour chat color has been changed to §" + color + "§lTHIS§a!");

        return true;
    }
}
