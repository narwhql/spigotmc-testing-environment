package me.narwhql.spigotmc.listeners;

import me.narwhql.spigotmc.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.awt.*;

public class AsyncPlayerChatListener extends ListenerAdapter implements Listener {
    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(event.getPlayer().getName(), null, "https://minotar.net/helm/" + event.getPlayer().getName() + "/100.png")
                .setDescription(event.getMessage())
                .setColor(Color.GREEN);
        Main.getInstance().getClient().getTextChannelById("964196501653971124").sendMessageEmbeds(builder.build()).queue();;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild() || event.getMessage().getAuthor().isBot() || !event.getChannel().getId().equals("964196501653971124")) return;

        Message message = event.getMessage();

        Bukkit.broadcastMessage("§d" + message.getAuthor().getAsTag() + "§7: §f" + message.getContentRaw());
    }
}
