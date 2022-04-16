package me.narwhql.spigotmc.experimental.classes;

public abstract class SubCommand extends BaseCommand {
    private final Command parent;
    private String description;
    public SubCommand(String name, Command parent) {
        super(name);
        this.parent = parent;
    }

    public Command getParent() {
        return parent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
