package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.pets.Pet;
import me.narwhql.spigotmc.pets.entities.*;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

public class PetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) return false;

        Pet pet = Main.getInstance().getPetManager().getPlayerPet(player);

        if (pet != null) pet.despawn();

        switch (args[0].toLowerCase()) {
            case "wolf":
                new WolfPet(player).spawn();
                break;

            case "enderman":
                new EndermanPet(player).spawn();
                break;

            case "creeper":
                new CreeperPet(player).spawn();
                break;

            case "zombie":
                new ZombiePet(player).spawn();
                break;

            case "sheep":
                SheepPet sheepPet = new SheepPet(player);
                sheepPet.spawn();

                ((Sheep) sheepPet.getEntity()).setColor(DyeColor.WHITE);
                ((Sheep) sheepPet.getEntity()).setBaby();
                break;
        }

        return true;
    }
}
