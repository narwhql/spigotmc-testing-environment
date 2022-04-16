package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collections;

public class InventoryCommand extends Command {
    public InventoryCommand() {
        super("inventory");

        this.setAliases(Collections.singletonList("inv"));

        this.setHelpColor(ChatColor.GOLD, ChatColor.GOLD, ChatColor.AQUA);

        this.setSubCommands(Arrays.asList(
                new ListSubCommand(this),
                new SaveSubCommand(this),
                new PreviewSubCommand(this),
                new LoadSubCommand(this),
                new DeleteSubCommand(this)
        ));
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {

    }
}
