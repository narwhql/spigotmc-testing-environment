package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.redstonepvp.blocks.ExperienceBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) return false;

        switch (args[0].toLowerCase()) {
            case "experience":
                player.getInventory().addItem(new ExperienceBlock());
                break;
        }

        return true;
    }
}
