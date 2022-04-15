package me.narwhql.spigotmc.pets.entities;

import me.narwhql.spigotmc.pets.Pet;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class WolfPet extends Pet {
    public WolfPet(Player owner) {
        super(owner, EntityType.WOLF);
        this.setDespawnSound(Sound.WOLF_DEATH, 2, 1);
    }
}