package me.narwhql.spigotmc.redstonepvp.blocks;

import me.narwhql.spigotmc.redstonepvp.RedstoneBlock;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class ExperienceBlock extends RedstoneBlock {
    public ExperienceBlock() {
        this.setType(Material.ANVIL);
        this.setAmount(1);
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName("§e§lRepair Items");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(itemMeta);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
    }

    @Override
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
    }

    @Override
    public void onBreak(BlockBreakEvent event) {

    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {

    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {

    }
}
