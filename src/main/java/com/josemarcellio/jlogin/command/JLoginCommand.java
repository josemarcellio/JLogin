package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.command.SubCommand;
import com.josemarcellio.jlogin.command.subcommand.ChangePasswordSubCommand;
import com.josemarcellio.jlogin.command.subcommand.ReloadSubCommand;
import com.josemarcellio.jlogin.command.subcommand.UnregisterSubCommand;
import com.josemarcellio.jlogin.util.Utility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

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

        List<String> helpCommand = configuration
                .getStringList("Messages.Help-Command");

        if (args.length == 0) {

            Utility.sendMessage(sender, helpCommand);

            return true;
        }

        SubCommand subCommand = getSubCommand(args[0]);
        if (subCommand == null) {

            Utility.sendMessage(sender, helpCommand);

            return true;
        }

        if (!sender.hasPermission(subCommand.getPermission())) {

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            "&6&lJLogin &7> &eYou don't have permission!"));
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }

    private SubCommand getSubCommand(String commandName) {
        SubCommand[] subCommands = {
                new ChangePasswordSubCommand(plugin),
                new UnregisterSubCommand(plugin),
                new ReloadSubCommand(plugin)
        };

        for (SubCommand subCommand : subCommands) {
            if (subCommand.getCommandName().equalsIgnoreCase(commandName)) {
                return subCommand;
            }
        }
        return null;
    }
}