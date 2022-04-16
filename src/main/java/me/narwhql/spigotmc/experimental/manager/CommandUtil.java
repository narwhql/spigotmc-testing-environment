package me.narwhql.spigotmc.experimental.manager;

import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.ChatColor;

public class CommandUtil {
    public static void sendMessage(CustomPlayer player, String message) {
        player.sendMessage("§9§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§9§m-----------------------------------------------------");
    }

    public static void sendMessage(CustomPlayer player, String message, ChatColor lineColor) {
        player.sendMessage(lineColor + "§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage(lineColor + "§9§m-----------------------------------------------------");
    }
}
