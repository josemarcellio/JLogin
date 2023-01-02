package com.josemarcellio.jlogin.command.subcommand;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.command.SubCommand;
import com.josemarcellio.jlogin.util.MessageDigestUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ChangePasswordSubCommand extends SubCommand {

    private final JLogin plugin;

    public ChangePasswordSubCommand(
            JLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommandName() {
        return "changepassword";
    }

    @Override
    public String getPermission() {
        return "jlogin.admin";
    }

    @Override
    public void execute(
            CommandSender sender, String[] args) {

        FileConfiguration configuration = plugin.getConfig();

        if (args.length == 3) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);
            UUID targetId = targetPlayer.getUniqueId();
            String newPassword = args[2];

            File playerFile = new File(plugin
                    .getDataFolder(), "/data/playerdata.yml");

            YamlConfiguration playerData = YamlConfiguration
                    .loadConfiguration(playerFile);

            if (playerData.contains("playerdata." + targetId.toString())) {
                playerData.set("playerdata." + targetId, null);

                byte[] newHashedPassword = MessageDigestUtils
                        .getSHA256Hash(newPassword);
                String newHashedPasswordString = DatatypeConverter
                        .printHexBinary(newHashedPassword);

                playerData.set("playerdata." + targetId +
                        ".password", newHashedPasswordString);

                try {
                    playerData.save(playerFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String passwordChanged = configuration
                        .getString("Messages.Password-Changed-Other");

                passwordChanged = passwordChanged
                        .replace("{player}", targetPlayer.getName());

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                passwordChanged));

            } else {
                String unknownPlayer = configuration
                        .getString("Messages.Player-Not-Found");

                unknownPlayer = unknownPlayer
                        .replace("{player}", targetPlayer.getName());

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                unknownPlayer));
            }
        } else {
            String changePasswordOtherUsage = configuration
                    .getString("Messages.Change-Password-Other-Usage");

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            changePasswordOtherUsage));
        }
    }
}
