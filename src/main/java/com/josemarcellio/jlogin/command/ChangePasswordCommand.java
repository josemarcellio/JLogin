package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.status.Status;
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

public class ChangePasswordCommand
        implements CommandExecutor {

    private final JLogin plugin;

    public ChangePasswordCommand(
            JLogin plugin) {
        this.plugin = plugin;
    }

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

            if (plugin.getLoginStatus().get(player) == Status.PRE) {

                String alreadyLoggedIn = configuration
                        .getString("Messages.Not-Logged-In");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                alreadyLoggedIn));
                return true;
            }


            if (args.length == 2) {
                String oldPassword = args[0];
                String newPassword = args[1];

                byte[] oldHashedPassword = MessageDigestUtils
                        .getSHA256Hash(oldPassword);

                String oldHashedPasswordString = DatatypeConverter
                        .printHexBinary(oldHashedPassword);

                String storedHashedPassword = playerData
                        .getString("playerdata." + playerId
                                + ".password");

                if (oldHashedPasswordString.equals(storedHashedPassword)) {

                    byte[] newHashedPassword = MessageDigestUtils
                            .getSHA256Hash(newPassword);

                    String newHashedPasswordString = DatatypeConverter
                            .printHexBinary(newHashedPassword);

                    playerData.set("playerdata." + playerId + ".password",
                            newHashedPasswordString);

                    try {
                        playerData.save(playerFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String passwordChanged = configuration
                            .getString("Messages.Password-Changed");

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    passwordChanged));
                } else {
                    String incorrectPassword = configuration
                            .getString("Messages.Wrong-Password");

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    incorrectPassword));
                }
            } else {
                String changePasswordUsage = configuration
                        .getString("Messages.Change-Password-Usage");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                changePasswordUsage));
            }
        } else {
            sender.sendMessage("Only player can use this command!");
        }
        return true;
    }
}
