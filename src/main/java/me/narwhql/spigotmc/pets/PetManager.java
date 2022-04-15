package me.narwhql.spigotmc.pets;

import me.narwhql.spigotmc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.HashMap;
import java.util.Map;

public class PetManager implements Listener {
    private final Main plugin;

    private final Map<Player, Pet> petMap = new HashMap<>();

    public PetManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.init();
    }

    private void init() {
        Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
            this.petMap.values().forEach(pet -> {
                Player owner = pet.getOwner();

                if (owner == null || owner.isFlying() || !owner.isOnGround()) return;

                double distance = pet.getEntity().getLocation().distance(owner.getLocation());

                if (distance > 7) pet.getEntity().teleport(owner);
                else if (distance > 4) pet.moveToLocation(owner.getLocation());
            });
        }, 5L, 5L);
    }

    public void savePlayerPet(Pet pet) {
        this.petMap.put(pet.getOwner(), pet);
    }

    public void removePlayerPet(Player player) {
        this.petMap.remove(player);
    }

    public Pet getPlayerPet(Player player) {
        return this.petMap.getOrDefault(player, null);
    }

    public void despawnAllPets() {
        this.petMap.values().forEach(pet -> pet.despawn());
    }

    public boolean isPet(Entity entity) {
        return petMap.values().stream().filter(pet -> pet.getEntity() == entity).findFirst().orElse(null) != null;
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (this.isPet(event.getDamager())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityMount(EntityMountEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (this.isPet(event.getEntity())) event.setCancelled(true);
    }
}
