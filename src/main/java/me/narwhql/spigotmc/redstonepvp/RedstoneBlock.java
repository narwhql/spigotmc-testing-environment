package me.narwhql.spigotmc.redstonepvp;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class RedstoneBlock extends ItemStack {
    public abstract void onPlace(BlockPlaceEvent event);

    public abstract void onBreak(BlockBreakEvent event);

    public abstract void onRightClick(PlayerInteractEvent event);

    public abstract void onLeftClick(PlayerInteractEvent event);
}
