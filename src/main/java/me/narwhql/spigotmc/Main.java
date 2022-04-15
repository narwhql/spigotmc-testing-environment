package me.narwhql.spigotmc;

import me.narwhql.spigotmc.commands.ArmorStandCommand;
import me.narwhql.spigotmc.commands.PetCommand;
import me.narwhql.spigotmc.pets.PetManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    private PetManager petManager;

    @Override
    public void onEnable() {
        instance = this;

        this.initCommands();
        this.initListeners();

        this.petManager = new PetManager(this);
    }

    @Override
    public void onDisable() {
        this.petManager.despawnAllPets();
    }

    public static Main getInstance() {
        return instance;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    private void initCommands() {
        this.getCommand("armorstand").setExecutor(new ArmorStandCommand());
        this.getCommand("send").setExecutor(new SendCommand());
        this.getCommand("pet").setExecutor(new PetCommand());
        this.getCommand("block").setExecutor(new BlockCommand());
    }

    private void initListeners() {

    }
}
