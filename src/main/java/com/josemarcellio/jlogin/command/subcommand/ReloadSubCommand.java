package com.josemarcellio.jlogin.command.subcommand;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends SubCommand {

    private final JLogin plugin;

    public ReloadSubCommand(
            JLogin plugin) {
        this.plugin = plugin;
    }


    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "jlogin.admin";
    }

    @Override
    public void execute(
            CommandSender sender, String[] args) {

        plugin.reloadConfig();

        sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        "&6&lJLogin &7> &eConfig reloaded!"));
    }
}
