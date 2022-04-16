package me.narwhql.spigotmc;

import me.narwhql.spigotmc.commands.ChatColorCommand;
import me.narwhql.spigotmc.pets.commands.PetCommand;
import me.narwhql.spigotmc.inventories.InventoryManager;
import me.narwhql.spigotmc.listeners.AsyncPlayerChatListener;
import me.narwhql.spigotmc.listeners.Listeners;
import me.narwhql.spigotmc.pets.PetManager;
import me.narwhql.spigotmc.player.PlayerManager;
import me.narwhql.spigotmc.experimental.manager.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    private MySQL mySQL;

    private CommandManager commandManager;
    private PlayerManager playerManager;
    private PetManager petManager;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        instance = this;

        this.mySQL = new MySQL();
        this.mySQL.connect();

        this.initListeners();

        this.commandManager = new CommandManager(this);
        this.playerManager = new PlayerManager(this);
        this.petManager = new PetManager(this);
        this.inventoryManager = new InventoryManager(this);

        this.commandManager.registerCommand(new ChatColorCommand());
        this.commandManager.registerCommand(new PetCommand());
    }

    @Override
    public void onDisable() {
        this.petManager.despawnAllPets();
    }

    public static Main getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
    }
}
