package me.narwhql.spigotmc.pets.entities;

import me.narwhql.spigotmc.pets.Pet;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SheepPet extends Pet {
    public SheepPet(Player owner) {
        super(owner, EntityType.SHEEP);
        this.setDespawnSound(Sound.SHEEP_IDLE, 2, 1);
    }
}