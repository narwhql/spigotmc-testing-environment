package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.experimental.manager.CommandUtil;
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
                CommandUtil.sendMessage(player, "§cInventory with ID " + id + " does not exist!");
                return;
            }

            CommandUtil.sendMessage(player, "§aInventory with ID §e" + id + " §ahas been updated!");

            player.updateExistingInventory(id, player.getInventory());

            return;
        }

        if (player.getInventories().size() >= player.getMaxinventoriesSize()) {
            CommandUtil.sendMessage(player, "§cYou cannot have more than 9 inventories!");
            return;
        }

        CommandUtil.sendMessage(player, "§aA new inventory has been saved!");

        player.saveNewInventory(player.getInventory());
    }
}
