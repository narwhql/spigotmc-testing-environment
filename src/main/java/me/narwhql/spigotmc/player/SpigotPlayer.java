package me.narwhql.spigotmc.player;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.inventories.InventoryManager;
import me.narwhql.spigotmc.inventories.PlayerInventory;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpigotPlayer extends CraftPlayer {
    private ChatColor color;

    public SpigotPlayer(EntityPlayer entity) {
        super((CraftServer) Bukkit.getServer(), entity);
    }

    public void init() {
        try {
            Connection connection = Main.getInstance().getMySQL().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ChatColors WHERE name = ?");
            preparedStatement.setString(1, this.getName());

            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                this.color = ChatColor.WHITE;

                PreparedStatement insertStatement = connection.prepareStatement("INSERT IGNORE INTO ChatColors (name, color) VALUES (?, 'f')");
                insertStatement.setString(1, this.getName());
                insertStatement.executeUpdate();
                return;
            }

            this.color = ChatColor.getByChar(result.getString("ChatColors.color"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setChatColor(ChatColor color) {
        this.color = color;

        try {
            PreparedStatement preparedStatement = Main.getInstance().getMySQL().getConnection().prepareStatement("UPDATE ChatColors SET color = ? WHERE name = ?");
            preparedStatement.setString(1, color.getChar() + "");
            preparedStatement.setString(2, this.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChatColor getChatColor() {
        return color;
    }

    public List<PlayerInventory> getInventories() {
        Connection connection = Main.getInstance().getMySQL().getConnection();

        try {
            List<PlayerInventory> inventories = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PlayerInventories WHERE name = ?");
            preparedStatement.setString(1, this.getName());

            ResultSet result = preparedStatement.executeQuery();

            InventoryManager inventoryManager = Main.getInstance().getInventoryManager();

            while (result.next()) {
                PlayerInventory inventory = new PlayerInventory(
                        result.getInt("id"),
                        result.getLong("created_timestamp"),
                        result.getLong("last_update_timestamp"),
                        inventoryManager.getInventoryFromBase64(result.getString("inventory"))
                );
                inventories.add(inventory);
            }

            return inventories;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void saveNewInventory(Inventory inventory) {
        Connection connection = Main.getInstance().getMySQL().getConnection();

        InventoryManager inventoryManager = Main.getInstance().getInventoryManager();
        String base64 = inventoryManager.convertInventoryToBase64(inventory);
        long timestamp = new Date().getTime();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO PlayerInventories (name, created_timestamp, last_update_timestamp, inventory) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, this.getName());
            preparedStatement.setLong(2, timestamp);
            preparedStatement.setLong(3, timestamp);
            preparedStatement.setString(4, base64);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerInventory getExistingInventory(int id) {
        Connection connection = Main.getInstance().getMySQL().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PlayerInventories WHERE id = ? AND name = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, this.getName());

            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) return null;

            InventoryManager inventoryManager = Main.getInstance().getInventoryManager();

            Inventory inventory = inventoryManager.getInventoryFromBase64(result.getString("inventory"));

            return new PlayerInventory(
                    result.getInt("id"),
                    result.getLong("created_timestamp"),
                    result.getLong("last_update_timestamp"),
                    inventory
            );
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateExistingInventory(int id, Inventory newInventory) {
        Connection connection = Main.getInstance().getMySQL().getConnection();

        InventoryManager inventoryManager = Main.getInstance().getInventoryManager();
        String base64 = inventoryManager.convertInventoryToBase64(newInventory);
        long timestamp = new Date().getTime();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PlayerInventories SET inventory = ? , last_update_timestamp = ? WHERE id = ? AND name = ?");
            preparedStatement.setString(1, base64);
            preparedStatement.setLong(2, timestamp);
            preparedStatement.setInt(3, id);
            preparedStatement.setString(4, this.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInventory(int id) {
        Connection connection = Main.getInstance().getMySQL().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PlayerInventories WHERE id = ? AND name = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, this.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxinventoriesSize() {
        return 9;
    }
}
