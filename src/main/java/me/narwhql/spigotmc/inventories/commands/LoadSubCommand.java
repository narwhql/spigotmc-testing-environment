package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.experimental.manager.CommandUtil;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.Sound;

public class LoadSubCommand extends SubCommand {
    public LoadSubCommand(Command parent) {
        super("load", parent);
        this.setDescription("Load an inventory");
        this.addRequiredArgument("id");
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {
        if (!Util.isNumeric(args[1])) return;

        int id = Integer.parseInt(args[1]);

        PlayerInventory inventory = player.getExistingInventory(id);

        if (inventory == null) {
            CommandUtil.sendMessage(player, "§cInventory with ID " + id + " does not exist!");
            return;
        }

        CommandUtil.sendMessage(player, "§aInventory loaded from ID §e" + id + "§a!");

        player.getInventory().setContents(inventory.getInventory().getContents());
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2, 2);
    }
}
