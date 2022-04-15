package me.narwhql.spigotmc;

import me.narwhql.spigotmc.commands.ArmorStandCommand;
import me.narwhql.spigotmc.commands.BlockCommand;
import me.narwhql.spigotmc.commands.PetCommand;
import me.narwhql.spigotmc.commands.SendCommand;
import me.narwhql.spigotmc.listeners.AsyncPlayerChatListener;
import me.narwhql.spigotmc.pets.PetManager;
import me.narwhql.spigotmc.redstonepvp.RedstoneBlockManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {
    private static Main instance;
    private JDA client;

    private PetManager petManager;
    private RedstoneBlockManager redstoneBlockManager;

    @Override
    public void onEnable() {
        instance = this;

        this.initCommands();
        this.initListeners();
        // this.initDiscord();

        this.petManager = new PetManager(this);
        this.redstoneBlockManager = new RedstoneBlockManager(this);
    }

    @Override
    public void onDisable() {
        this.petManager.despawnAllPets();
        this.client.shutdownNow();
    }

    public static Main getInstance() {
        return instance;
    }

    public JDA getClient() {
        return client;
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
        // this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
    }

    public void initDiscord() {
        JDABuilder builder = JDABuilder.createDefault("ODc5NDA3Njk1ODU2Mjk1OTkw.YSPSPQ.aITSmZ-NXDy0rQrJe27PSSG3jJY");

        builder.setBulkDeleteSplittingEnabled(false);

        builder.addEventListeners(
                new AsyncPlayerChatListener()
        );

        try {
            this.client = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
