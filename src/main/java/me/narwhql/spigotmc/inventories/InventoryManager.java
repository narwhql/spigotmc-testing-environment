package me.narwhql.spigotmc.inventories;

import me.narwhql.spigotmc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventoryManager implements Listener {

    public static String INVENTORY_LIST_TITLE = "Your Inventories";
    public static String PREVIEW_INVENTORY_TITLE = "Previewing Inventory";
    public static String BACK_ITEM_NAME = "§e§lBack";

    public InventoryManager(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Inventory getInventoryFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, (ItemStack) dataInput.readObject());

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public String convertInventoryToBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) dataOutput.writeObject(inventory.getItem(i));

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(PREVIEW_INVENTORY_TITLE)) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;

            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(BACK_ITEM_NAME)) {
                event.getWhoClicked().closeInventory();
                Bukkit.dispatchCommand(event.getWhoClicked(), "inventory list");
            }
        }

        else if (event.getInventory().getName().equals(INVENTORY_LIST_TITLE)) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;

            String id = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("Inventory - ", ""));

            if (event.getClick() == ClickType.LEFT) {
                event.getWhoClicked().closeInventory();
                Bukkit.dispatchCommand(event.getWhoClicked(), "inventory load " + id);
            }

            else if (event.getClick() == ClickType.RIGHT) {
                event.getWhoClicked().closeInventory();
                Bukkit.dispatchCommand(event.getWhoClicked(), "inventory preview " + id);
            }

            else if (event.getClick() == ClickType.MIDDLE) {
                event.getWhoClicked().closeInventory();
                Bukkit.dispatchCommand(event.getWhoClicked(), "inventory delete " + id);
            }
        }
    }
}
