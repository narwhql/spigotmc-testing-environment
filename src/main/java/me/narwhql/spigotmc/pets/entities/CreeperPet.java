package me.narwhql.spigotmc.pets.entities;

import me.narwhql.spigotmc.pets.Pet;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CreeperPet extends Pet {
    public CreeperPet(Player owner) {
        super(owner, EntityType.CREEPER);
        this.setDespawnSound(Sound.CREEPER_DEATH, 2, 1);
    }
}