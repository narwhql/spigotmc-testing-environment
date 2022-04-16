package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import me.narwhql.spigotmc.player.CustomPlayer;

public class SaveSubCommand extends SubCommand {
    public SaveSubCommand(Command parent) {
        super("save", parent);
        this.setDescription("Save an inventory");
        this.addOptionalArgument("id");
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {
        if (args.length >= 2) {
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
            player.sendMessage("§aInventory with ID §e" + id + " §ahas been updated!");
            player.sendMessage("§3§m-----------------------------------------------------");

            player.updateExistingInventory(id, player.getInventory());

            return;
        }

        if (player.getInventories().size() >= player.getMaxinventoriesSize()) {
            player.sendMessage("§3§m-----------------------------------------------------");
            player.sendMessage("§cYou cannot have more than 9 inventories!");
            player.sendMessage("§3§m-----------------------------------------------------");
            return;
        }

        player.sendMessage("§3§m-----------------------------------------------------");
        player.sendMessage("§aA new inventory has been saved!");
        player.sendMessage("§3§m-----------------------------------------------------");

        player.saveNewInventory(player.getInventory());
    }
}
