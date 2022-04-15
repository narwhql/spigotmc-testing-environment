package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) return false;

        String message = String.join(" ", args);

        TextChannel textChannel = Main.getInstance().getClient().getTextChannelById("964196501653971124");

        textChannel.sendMessage(message).queue();

        player.sendMessage("Sending message: §e" + message);
        return true;
    }
}
