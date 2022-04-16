package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
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
            player.sendMessage("§3§m-----------------------------------------------------");
            player.sendMessage("§cInventory with ID " + id + " does not exist!");
            player.sendMessage("§3§m-----------------------------------------------------");
            return;
        }

        player.sendMessage("§3§m-----------------------------------------------------");
        player.sendMessage("§aInventory with ID §e" + id + " §ahas been deleted!");
        player.sendMessage("§3§m-----------------------------------------------------");

        player.deleteInventory(id);
    }
}
