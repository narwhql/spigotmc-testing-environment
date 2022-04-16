package me.narwhql.spigotmc.inventories.commands;

import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.inventories.InventoryManager;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PreviewSubCommand extends SubCommand {
    public PreviewSubCommand(Command parent) {
        super("preview", parent);
        this.setDescription("Preview an inventory");
        this.addRequiredArgument("id");
    }

    @Override
    public void run(CustomPlayer player, String commandName, String[] args) {
        if (!Util.isNumeric(args[1])) return;

        int id = Integer.parseInt(args[1]);

        PlayerInventory inventory = player.getExistingInventory(id);

        if (inventory == null) {
            player.sendMessage("§3§m-----------------------------------------------------");
            player.sendMessage("§cInventory with ID " + id + " does not exist!");
            player.sendMessage("§3§m-----------------------------------------------------");
            return;
        }

        Inventory previewInventory = Bukkit.createInventory(player, inventory.getInventory().getSize() + 9, InventoryManager.PREVIEW_INVENTORY_TITLE);

        previewInventory.setContents(inventory.getInventory().getContents());

        ItemStack ironBar = new ItemStack(Material.IRON_FENCE);
        ItemMeta ironBarMeta = ironBar.getItemMeta();
        ironBarMeta.setDisplayName("§e");
        ironBar.setItemMeta(ironBarMeta);

        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName(InventoryManager.BACK_ITEM_NAME);
        backItem.setItemMeta(backItemMeta);

        previewInventory.setItem(previewInventory.getSize() - 1, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 2, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 3, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 4, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 5, backItem);
        previewInventory.setItem(previewInventory.getSize() - 6, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 7, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 8, ironBar);
        previewInventory.setItem(previewInventory.getSize() - 9, ironBar);

        player.openInventory(previewInventory);
    }
}
