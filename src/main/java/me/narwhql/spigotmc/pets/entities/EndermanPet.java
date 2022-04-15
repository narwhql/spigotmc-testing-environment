package me.narwhql.spigotmc.pets.entities;

import me.narwhql.spigotmc.pets.Pet;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EndermanPet extends Pet {
    public EndermanPet(Player owner) {
        super(owner, EntityType.ENDERMAN);
        this.setDespawnSound(Sound.ENDERMAN_TELEPORT, 2, 1);
    }
}