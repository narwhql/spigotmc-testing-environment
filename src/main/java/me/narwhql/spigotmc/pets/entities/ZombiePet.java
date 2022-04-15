package me.narwhql.spigotmc.pets.entities;

import me.narwhql.spigotmc.pets.Pet;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ZombiePet extends Pet {
    public ZombiePet(Player owner) {
        super(owner, EntityType.ZOMBIE);
        this.setDespawnSound(Sound.ZOMBIE_DEATH, 2, 1);
    }
}