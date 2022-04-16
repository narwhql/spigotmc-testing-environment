package me.narwhql.spigotmc.experimental.classes;

import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommand {
    private final String name;

    private boolean asynchronous = false;

    private ChatColor helpLineColor = ChatColor.YELLOW;
    private ChatColor keyColor = ChatColor.YELLOW;
    private ChatColor valueColor = ChatColor.AQUA;

    private final List<String> requiredArguments = new ArrayList<>();
    private final List<String> optionalArguments = new ArrayList<>();

    public BaseCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    protected void setHelpColor(ChatColor lineColor, ChatColor keyColor, ChatColor valueColor) {
        this.helpLineColor = lineColor;
        this.keyColor = keyColor;
        this.valueColor = valueColor;
    }

    public ChatColor getHelpLineColor() {
        return helpLineColor;
    }

    public ChatColor getCommandKeyColor() {
        return keyColor;
    }

    public ChatColor getCommandValueColor() {
        return valueColor;
    }

    public void addRequiredArgument(String argument) {
        this.requiredArguments.add(argument.toLowerCase());
    }

    public void addOptionalArgument(String argument) {
        this.optionalArguments.add(argument.toLowerCase());
    }

    public List<String> getRequiredArguments() {
        return requiredArguments;
    }

    public List<String> getOptionalArguments() {
        return optionalArguments;
    }

    public List<String> getArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(this.requiredArguments);
        arguments.addAll(this.optionalArguments);
        return arguments;
    }

    public abstract void run(CustomPlayer player, String commandName, String[] args);
}
