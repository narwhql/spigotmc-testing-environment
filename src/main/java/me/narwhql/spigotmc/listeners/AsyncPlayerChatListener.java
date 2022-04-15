package me.narwhql.spigotmc.listeners;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.player.SpigotPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        SpigotPlayer player = Main.getInstance().getPlayerManager().getPlayer(event.getPlayer().getName());
        String message = ChatColor.translateAlternateColorCodes('&', player.getChatColor() == null ? event.getMessage() : player.getChatColor() + event.getMessage());
        event.setFormat(ChatColor.GRAY + player.getName() + " §8» §r" + message);
    }
}