package me.narwhql.spigotmc.experimental.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Command extends BaseCommand {
    private List<String> aliases = new ArrayList<>();
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public Command(String name) {
        super(name);
    }

    protected void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setSubCommands(List<SubCommand> subCommands) {
        subCommands.forEach(subCommand -> this.subCommands.put(subCommand.getName().toLowerCase(), subCommand));
    }

    public Map<String, SubCommand> getSubCommands() {
        return subCommands;
    }

    public SubCommand getSubCommand(String subCommandName) {
        return this.subCommands.getOrDefault(subCommandName.toLowerCase(), null);
    }

    public boolean hasSubCommand(String subCommandName) {
        return this.subCommands.containsKey(subCommandName.toLowerCase());
    }
}
