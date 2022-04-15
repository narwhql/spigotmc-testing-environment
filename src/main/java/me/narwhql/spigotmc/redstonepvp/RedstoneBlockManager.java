package me.narwhql.spigotmc.redstonepvp;

import me.narwhql.spigotmc.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class RedstoneBlockManager implements Listener {
    private final Main plugin;

    public RedstoneBlockManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();

        if (itemStack == null || !(itemStack instanceof RedstoneBlock)) return;

        event.getPlayer().sendMessage("f");
     }
}
