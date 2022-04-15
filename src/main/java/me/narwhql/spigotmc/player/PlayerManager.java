package me.narwhql.spigotmc.player;

import me.narwhql.spigotmc.Main;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {
    private final Main plugin;

    private final Map<String, SpigotPlayer> playerMap = new HashMap<>();

    public PlayerManager(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            SpigotPlayer spigotPlayer = new SpigotPlayer(((CraftPlayer) player).getHandle());
            this.savePlayer(spigotPlayer);
            spigotPlayer.init();
        }));
    }

    public void savePlayer(SpigotPlayer player) {
        this.playerMap.put(player.getName().toLowerCase(), player);
    }

    public void removePlayer(String username) {
        this.playerMap.remove(username.toLowerCase());
    }

    public SpigotPlayer getPlayer(String username) {
        return this.playerMap.getOrDefault(username.toLowerCase(), null);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        SpigotPlayer player = new SpigotPlayer(((CraftPlayer) event.getPlayer()).getHandle());
        this.savePlayer(player);
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, player::init);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        this.removePlayer(event.getPlayer().getName());
    }
}
