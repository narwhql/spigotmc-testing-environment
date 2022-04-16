package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.experimental.manager.CommandUtil;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChatColorCommand extends Command {
    private final List<String> validColors = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f");

    public ChatColorCommand() {
        super("chatcolor");
        this.setAliases(Collections.singletonList("cc"));
        this.addRequiredArgument("color");
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {
        String color = args[0].toLowerCase();

        if (!this.validColors.contains(color)) {
            CommandUtil.sendMessage(player, "§cThis color does not exist!", ChatColor.YELLOW);
            return;
        }

        CustomPlayer customPlayer = Main.getInstance().getPlayerManager().getPlayer(player.getName());

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> customPlayer.setChatColor(ChatColor.getByChar(color)));

        player.sendMessage("§aYour chat color has been changed to §" + color + "§lTHIS§a!");
    }
}
