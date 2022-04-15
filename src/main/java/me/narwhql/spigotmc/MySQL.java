package me.narwhql.spigotmc;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private Connection connection;

    public void connect() {
        String host = "localhost";
        String port = "3306";
        String database = "testing";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8", user, password);
            loadTables();
            Bukkit.getLogger().info("[MySQL] Connected to MySQL!");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[MySQL] Could not connect to MySQL!");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void loadTables() throws SQLException {
        this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ChatColors (name VARCHAR(16), color VARCHAR(1), PRIMARY KEY(name))").executeUpdate();
        this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerInventories (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(16), created_timestamp BIGINT, last_update_timestamp BIGINT, inventory LONGTEXT, PRIMARY KEY(id))").executeUpdate();
    }
}
