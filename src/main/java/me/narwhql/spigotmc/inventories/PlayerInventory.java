package me.narwhql.spigotmc.inventories;

import org.bukkit.inventory.Inventory;

public class PlayerInventory {
    private final int id;
    private final long createdTimestamp;
    private final long lastUpdateTimestamp;
    private final Inventory inventory;

    public PlayerInventory(int id, long createdTimestamp, long lastUpdateTimestamp, Inventory inventory) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
