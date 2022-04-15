package me.narwhql.spigotmc.player;

import me.narwhql.spigotmc.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
