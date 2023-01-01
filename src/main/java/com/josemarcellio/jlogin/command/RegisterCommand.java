package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.util.MessageDigestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RegisterCommand
        implements CommandExecutor {

    private final JLogin plugin;

    public RegisterCommand(
            JLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender, Command command, String label,
            String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerId = player.getUniqueId();
            File playerFile = new File(plugin
                    .getDataFolder(), "/data/playerdata.yml");

            YamlConfiguration playerData = YamlConfiguration
                    .loadConfiguration(playerFile);

            FileConfiguration configuration = plugin.getConfig();

            if (plugin.getLoginPlayer().containsKey(player)) {

                String alreadyLoggedIn = configuration
                        .getString("Messages.Already-Logged-In");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                alreadyLoggedIn));
                return true;
            }

            if (!playerData.contains("playerdata." + playerId.toString())) {

                if (args.length == 1) {
                    String password = args[0];

                    byte[] hashedPassword =
                            MessageDigestUtils.getSHA256Hash(
                                    password);

                    String hashedPasswordString =
                            DatatypeConverter.printHexBinary(
                                    hashedPassword);

                    playerData.set(
                            "playerdata." + playerId
                                    + ".username", player.getName());

                    playerData.set(
                            "playerdata." + playerId
                                    + ".password", hashedPasswordString);

                    try {
                        playerData.save(playerFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    plugin.getLoginPlayer().put(
                            player, true);

                    String successfullyRegistered = configuration
                            .getString("Messages.Successfully-Registered");

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    successfullyRegistered));
                } else {

                    String registeredUsage = configuration
                            .getString("Messages.Registered-Usage");

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    registeredUsage));
                }
            } else {

                String alreadyRegistered = configuration
                        .getString("Messages.Already-Registered");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                alreadyRegistered));
            }
        } else {
            sender.sendMessage("Only player can use this command!");
        }
        return true;
    }

}