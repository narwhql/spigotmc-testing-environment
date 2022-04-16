package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.experimental.manager.CommandUtil;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import me.narwhql.spigotmc.player.CustomPlayer;

public class DeleteSubCommand extends SubCommand {
    public DeleteSubCommand(Command parent) {
        super("delete", parent);
        this.setDescription("Delete an inventory");
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

        CommandUtil.sendMessage(player, "§aInventory with ID §e" + id + " §ahas been deleted!");

        player.deleteInventory(id);
    }
}
