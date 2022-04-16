package me.narwhql.spigotmc.experimental.manager;

import me.narwhql.spigotmc.player.CustomPlayer;

public class CommandUtil {
    public static void sendMessage(CustomPlayer player, String message) {
        player.sendMessage("§9§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§9§m-----------------------------------------------------");
    }

    public static void sendMessageSecondary(CustomPlayer player, String message) {
        player.sendMessage("§7§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§7§m-----------------------------------------------------");
    }

    public static void sendMessageSuccess(CustomPlayer player, String message) {
        player.sendMessage("§a§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§a§m-----------------------------------------------------");
    }

    public static void sendMessageDanger(CustomPlayer player, String message) {
        player.sendMessage("§c§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§c§m-----------------------------------------------------");
    }

    public static void sendMessageWarning(CustomPlayer player, String message) {
        player.sendMessage("§e§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§e§m-----------------------------------------------------");
    }

    public static void sendMessageInfo(CustomPlayer player, String message) {
        player.sendMessage("§3§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§3§m-----------------------------------------------------");
    }

    public static void sendMessageLight(CustomPlayer player, String message) {
        player.sendMessage("§f§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§f§m-----------------------------------------------------");
    }

    public static void sendMessageDark(CustomPlayer player, String message) {
        player.sendMessage("§8§m-----------------------------------------------------");
        player.sendMessage(message);
        player.sendMessage("§8§m-----------------------------------------------------");
    }
}
