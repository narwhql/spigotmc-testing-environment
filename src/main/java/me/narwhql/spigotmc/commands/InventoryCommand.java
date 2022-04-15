package me.narwhql.spigotmc.commands;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.Util;
import me.narwhql.spigotmc.inventories.InventoryManager;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import me.narwhql.spigotmc.player.SpigotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InventoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        SpigotPlayer player = Main.getInstance().getPlayerManager().getPlayer(sender.getName());

        if (args.length == 0) {
            player.sendMessage("§e§m-----------------------------------------------------");
            player.sendMessage("§e/" + label + " list§7: §bDisplay a list of your inventories");
            player.sendMessage("§e/" + label + " save§7: §bSave a new inventory");
            player.sendMessage("§e/" + label + " save <id>§7: §bUpdate an existing inventory");
            player.sendMessage("§e/" + label + " preview <id>§7: §bPreview an inventory");
            player.sendMessage("§e/" + label + " load <id>§7: §bLoad an inventory");
            player.sendMessage("§e/" + label + " delete <id>§7: §bDelete an inventory");
            player.sendMessage("§e§m-----------------------------------------------------");
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            switch (args[0].toLowerCase()) {
                case "list": {
                    List<PlayerInventory> inventories = player.getInventories();

                    if (inventories.size() == 0) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cYou do not have any inventories saved!");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

                    Inventory bukkitInventory = Bukkit.createInventory(player, 9, InventoryManager.INVENTORY_LIST_TITLE);

                    inventories.forEach(inventory -> {
                        ItemStack itemStack = new ItemStack(Material.BOOK, 1);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName("§e§lInventory - " + inventory.getId());
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
                break;

                case "save": {
                    if (args.length >= 2) {
                        if (!Util.isNumeric(args[1])) return;

                        int id = Integer.parseInt(args[1]);

                        PlayerInventory inventory = player.getExistingInventory(id);

                        if (inventory == null) {
                            player.sendMessage("§3§m-----------------------------------------------------");
                            player.sendMessage("§cInventory with ID " + id + " does not exist!");
                            player.sendMessage("§3§m-----------------------------------------------------");
                            return;
                        }

                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§aInventory with ID §e" + id + " §ahas been updated!");
                        player.sendMessage("§3§m-----------------------------------------------------");

                        player.updateExistingInventory(id, player.getInventory());

                        return;
                    }

                    if (player.getInventories().size() >= player.getMaxinventoriesSize()) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cYou cannot have more than 9 inventories!");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

                    player.sendMessage("§3§m-----------------------------------------------------");
                    player.sendMessage("§aA new inventory has been saved!");
                    player.sendMessage("§3§m-----------------------------------------------------");

                    player.saveNewInventory(player.getInventory());
                }
                break;

                case "preview": {
                    if (args.length < 2) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cUsage: /" + label + " preview <id>");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

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
                break;

                case "load": {
                    if (args.length < 2) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cUsage: /" + label + " load <id>");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

                    if (!Util.isNumeric(args[1])) return;

                    int id = Integer.parseInt(args[1]);

                    PlayerInventory inventory = player.getExistingInventory(id);

                    if (inventory == null) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cInventory with ID " + id + " does not exist!");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

                    player.sendMessage("§3§m-----------------------------------------------------");
                    player.sendMessage("§aInventory loaded from ID §e" + id + "§a!");
                    player.sendMessage("§3§m-----------------------------------------------------");

                    player.getInventory().setContents(inventory.getInventory().getContents());
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2, 2);
                }
                break;

                case "delete": {
                    if (args.length < 2) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cUsage: /" + label + " delete <id>");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

                    if (!Util.isNumeric(args[1])) return;

                    int id = Integer.parseInt(args[1]);

                    PlayerInventory inventory = player.getExistingInventory(id);

                    if (inventory == null) {
                        player.sendMessage("§3§m-----------------------------------------------------");
                        player.sendMessage("§cInventory with ID " + id + " does not exist!");
                        player.sendMessage("§3§m-----------------------------------------------------");
                        return;
                    }

                    player.sendMessage("§3§m-----------------------------------------------------");
                    player.sendMessage("§aInventory with ID §e" + id + " §ahas been deleted!");
                    player.sendMessage("§3§m-----------------------------------------------------");

                    player.deleteInventory(id);
                }
                break;
            }
        });

        return true;
    }
}