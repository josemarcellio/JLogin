package com.josemarcellio.jlogin.command.subcommand;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UnregisterSubCommand extends SubCommand {

    private final JLogin plugin;

    public UnregisterSubCommand(
            JLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommandName() {
        return "unregister";
    }

    @Override
    public String getPermission() {
        return "jlogin.admin";
    }

    @Override
    public void execute(
            CommandSender sender, String[] args) {

        if (args.length == 2) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);
            UUID targetId = targetPlayer.getUniqueId();
            File playerFile = new File(plugin
                    .getDataFolder(), "/data/playerdata.yml");

            YamlConfiguration playerData = YamlConfiguration
                    .loadConfiguration(playerFile);

            if (playerData.contains("playerdata." + targetId.toString())) {
                playerData.set("playerdata." + targetId, null);

                try {
                    playerData.save(playerFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                "&6&lJLogin &7> &e" + targetPlayer.getName()
                                        + " has been unregistered"));

            } else {

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                "&6&lJLogin &7> &ePlayer with name "
                                        + targetPlayer.getName() + " not found!"));
            }
        } else {

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            "&6&lJLogin &7> &eUsage: /jlogin unregister <player>"));
            }
        }
    }
