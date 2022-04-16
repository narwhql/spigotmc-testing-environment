package me.narwhql.spigotmc.pets.commands;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.pets.Pet;
import me.narwhql.spigotmc.pets.entities.*;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;

public class PetCommand extends Command {
    public PetCommand() {
        super("pet");
        this.addRequiredArgument("pet name");
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {
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
    }
}
