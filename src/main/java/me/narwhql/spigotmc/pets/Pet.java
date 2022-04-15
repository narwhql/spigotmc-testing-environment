package me.narwhql.spigotmc.pets;

import me.narwhql.spigotmc.Main;
import net.minecraft.server.v1_8_R3.EntityCreature;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Pet {
    private final Player owner;
    private final EntityType entityType;

    private Entity entity;
    private Sound despawnSound;
    private float volume;
    private float pitch;

    public Pet(Player owner, EntityType entityType) {
        this.owner = owner;
        this.entityType = entityType;

        Main.getInstance().getPetManager().savePlayerPet(this);
    }

    public void spawn() {
        if (this.owner == null) return;

        this.entity = this.owner.getWorld().spawnEntity(this.owner.getLocation(), entityType);
        this.entity.setCustomName("§9" + this.owner.getName() + "'s §7Custom Pet");
    }

    public Entity getEntity() {
        return entity;
    }

    public Player getOwner() {
        return owner;
    }

    public void setDespawnSound(Sound sound, float volume, float pitch) {
        this.despawnSound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void despawn() {
        if (this.entity == null) return;
        if (this.despawnSound != null) this.entity.getWorld().playSound(this.entity.getLocation(), this.despawnSound, this.volume, this.pitch);
        for (int i = 0; i < 6; i++) this.entity.getWorld().playEffect(this.entity.getLocation(), Effect.SMOKE, 10, 10);
        this.entity.remove();
    }

    public void moveToLocation(Location location){
        EntityCreature cr = ((EntityCreature) ((CraftEntity) this.entity).getHandle());
        cr.getNavigation().a(location.getX(), location.getY(), location.getZ(), 1.3);
    }
}
