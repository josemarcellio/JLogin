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
import java.util.Arrays;
import java.util.UUID;

public class LoginCommand
        implements CommandExecutor {

    private final JLogin plugin;

    public LoginCommand(
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

            if (playerData.contains("playerdata." + playerId.toString())) {

                if (args.length == 1) {
                    String password = args[0];

                    String storedHashedPasswordString = playerData
                            .getString("playerdata." + playerId + ".password");

                    byte[] storedHashedPassword = DatatypeConverter
                            .parseHexBinary(
                                    storedHashedPasswordString);

                    byte[] enteredHashedPassword = MessageDigestUtils
                            .getSHA256Hash(
                                    password);

                    if (Arrays.equals(storedHashedPassword,
                            enteredHashedPassword)) {

                        plugin.getLoginPlayer().put(
                                player, true);

                        String successfullyLoggedIn = configuration
                                .getString("Messages.Successfully-Logged-In");

                        player.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        successfullyLoggedIn));

                    } else {

                        String wrongPassword = configuration
                                .getString("Messages.Wrong-Password");

                        player.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        wrongPassword));
                    }
                } else {

                    String loginUsage = configuration
                            .getString("Messages.Login-Usage");

                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    loginUsage));
                }
            } else {

                String notRegistered = configuration
                        .getString("Messages.Not-Registered");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                notRegistered));
            }
        } else {
            sender.sendMessage("Only player can use this command!");
        }
        return true;
    }
}