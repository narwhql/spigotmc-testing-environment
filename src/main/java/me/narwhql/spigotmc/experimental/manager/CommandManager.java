package me.narwhql.spigotmc.experimental.manager;

import me.narwhql.spigotmc.Main;
import me.narwhql.spigotmc.experimental.classes.Command;
import me.narwhql.spigotmc.experimental.classes.SubCommand;
import me.narwhql.spigotmc.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandManager implements Listener {
    private final Main plugin;

    private final Map<String, Command> commandMap = new HashMap<>();
    private final Map<String, String> commandAliases = new HashMap<>();

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void registerCommand(Command command) {
        this.commandMap.put(command.getName().toLowerCase(), command);
        command.getAliases().forEach(alias -> this.commandAliases.put(alias.toLowerCase(), command.getName().toLowerCase()));
    }

    private void runCommand(Command command, CustomPlayer player, String label, String[] args) {
        // in this case it's a normal command
        if (command.getSubCommands().size() == 0) {
            // checking if all required arguments exist, if not then send usage message
            if (args.length < command.getRequiredArguments().size()) {
                String usageMessage = "§cUsage: /" +
                        label +
                        " " +
                        command.getRequiredArguments().stream().map(arg -> "<" + arg.replaceAll(" ", "_") + ">").collect(Collectors.joining(" ")) +
                        " " +
                        command.getOptionalArguments().stream().map(arg -> "(" + arg.replaceAll(" ", "_") + ")").collect(Collectors.joining(" "));

                CommandUtil.sendMessage(player, usageMessage);
                return;
            }

            // run the command
            if (command.isAsynchronous()) Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> command.run(player, command.getName(), args));
            else command.run(player, command.getName(), args);

            return;
        }

        // in this case it could be a subcommand
        // the subcommands aren't given, so we send a help menu
        if (args.length == 0 || !command.hasSubCommand(args[0].toLowerCase())) {
            player.sendMessage(command.getHelpLineColor() + "§m-----------------------------------------------------");
            command.getSubCommands().forEach((name, subCommand) -> {
                StringBuilder builder = new StringBuilder();

                builder
                        .append(command.getCommandKeyColor())
                        .append("/")
                        .append(label)
                        .append(" ")
                        .append(subCommand.getName());

                if (subCommand.getArguments().size() != 0) {
                    if (subCommand.getRequiredArguments().size() != 0) {
                        builder
                                .append(" ")
                                .append(subCommand.getRequiredArguments().stream().map(arg -> "<" + arg.replaceAll(" ", "_") + ">").collect(Collectors.joining(" ")));
                    }
                    if (subCommand.getOptionalArguments().size() != 0) {
                        builder
                                .append(" ")
                                .append(subCommand.getOptionalArguments().stream().map(arg -> "(" + arg.replaceAll(" ", "_") + ")").collect(Collectors.joining(" ")));
                    }
                }

                if (subCommand.getDescription() != null) {
                    builder
                            .append("§7: §r")
                            .append(subCommand.getCommandValueColor())
                            .append(subCommand.getDescription());
                }

                player.sendMessage(builder.toString());
            });
            player.sendMessage(command.getHelpLineColor() + "§m-----------------------------------------------------");
            return;
        }

        String subCommandName = args[0].toLowerCase();
        SubCommand subCommand = command.getSubCommand(subCommandName);

        // check if all required arguments are given
        if ((args.length - 1) < subCommand.getRequiredArguments().size()) {
            String usageMessage = "§cUsage: /" +
                    label +
                    " " +
                    subCommand.getName() +
                    " " +
                    subCommand.getRequiredArguments().stream().map(arg -> "<" + arg.replaceAll(" ", "_") + ">").collect(Collectors.joining(" ")) +
                    " " +
                    subCommand.getOptionalArguments().stream().map(arg -> "(" + arg.replaceAll(" ", "_") + ")").collect(Collectors.joining(" "));

            CommandUtil.sendMessage(player, usageMessage);
            return;
        }

        // running the subcommand
        if (subCommand.isAsynchronous()) Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> subCommand.run(player, command.getName(), args));
        else subCommand.run(player, subCommand.getName(), args);
    }

    @EventHandler
    private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().substring(1).split(" ");

        String commandName = args[0].toLowerCase();

        CustomPlayer player = Main.getInstance().getPlayerManager().getPlayer(event.getPlayer().getName());

        if (this.commandMap.containsKey(commandName)) {
            event.setCancelled(true);
            Command command = this.commandMap.get(commandName);
            args = Arrays.copyOfRange(args, 1, args.length);
            this.runCommand(command, player, commandName, args);
        }

        if (this.commandAliases.containsKey(commandName)) {
            event.setCancelled(true);
            Command command = this.commandMap.getOrDefault(this.commandAliases.get(commandName), null);
            if (command == null) return;
            args = Arrays.copyOfRange(args, 1, args.length);
            this.runCommand(command, player, commandName, args);
        }
    }
}
