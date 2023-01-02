package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.command.SubCommand;
import com.josemarcellio.jlogin.command.subcommand.ChangePasswordSubCommand;
import com.josemarcellio.jlogin.command.subcommand.UnregisterSubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class JLoginCommand
        implements CommandExecutor {

    private final JLogin plugin;

    public JLoginCommand(
            JLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender, Command command, String label,
            String[] args) {

        FileConfiguration configuration = plugin.getConfig();

        String messagesHelp = configuration
                .getString("Messages.JLogin-Help");

        if (args.length == 0) {

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            messagesHelp));
            return true;
        }

        SubCommand subCommand = getSubCommand(args[0]);
        if (subCommand == null) {

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            messagesHelp));
            return true;
        }

        if (!sender.hasPermission(subCommand.getPermission())) {
            String messagesNoPermission = configuration
                    .getString("Messages.No-Permission");
            messagesNoPermission = messagesNoPermission
                    .replace("{permission}", subCommand.getPermission());

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            messagesNoPermission));
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }

    private SubCommand getSubCommand(String commandName) {
        SubCommand[] subCommands = {
                new ChangePasswordSubCommand(plugin),
                new UnregisterSubCommand(plugin)
        };

        for (SubCommand subCommand : subCommands) {
            if (subCommand.getCommandName().equalsIgnoreCase(commandName)) {
                return subCommand;
            }
        }
        return null;
    }
}