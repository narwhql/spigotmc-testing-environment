package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.experimental.manager.CommandUtil;
import me.narwhql.spigotmc.inventories.InventoryManager;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListSubCommand extends SubCommand {
    public ListSubCommand(Command parent) {
        super("list", parent);
        this.setDescription("Display a list of inventories");
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {
        List<PlayerInventory> inventories = player.getInventories();

        if (inventories.size() == 0) {
            CommandUtil.sendMessage(player, "§cYou do not have any inventories saved!");
            return;
        }

        Inventory bukkitInventory = Bukkit.createInventory(player, 9, InventoryManager.INVENTORY_LIST_TITLE);

        inventories.forEach(inventory -> {
            ItemStack itemStack = new ItemStack(Material.BOOK, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§a§lInventory - " + inventory.getId());
            itemMeta.setLore(Arrays.asList(
                    "",
                    "§7ID: §b" + inventory.getId(),
                    "§7Created at: §b" + new Date(inventory.getCreatedTimestamp()).toGMTString(),
                    "§7Last update: §b" + new Date(inventory.getLastUpdateTimestamp()).toGMTString(),
                    "",
                    "§e➤ Left click to load",
                    "§e➤ Right click to preview",
                    "§e➤ Middle click to update",
                    "",
                    "§c§l⚠ Shift + click to delete"
            ));
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);
            itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            bukkitInventory.addItem(itemStack);
        });

        player.openInventory(bukkitInventory);
    }
}
