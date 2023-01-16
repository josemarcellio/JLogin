package com.josemarcellio.jlogin.command.subcommand;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
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

                byte[] newHashedPassword =
                        plugin.getEncryption().encryptPassword(
                                newPassword);
                String newHashedPasswordString = DatatypeConverter
                        .printHexBinary(newHashedPassword);

                playerData.set("playerdata." + targetId +
                        ".password", newHashedPasswordString);

                try {
                    playerData.save(playerFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                "&6&lJLogin &7> &eSuccessfully changed "
                                        + targetPlayer.getName()
                                        + " password"));

            } else {

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                "&6&lJLogin &7> &ePlayer with name "
                                        + targetPlayer.getName() + " not found!"));
            }
        } else {

            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            "&6&lJLogin &7> &eUsage: " +
                                    "/jlogin changepassword <player> <password>"));
        }
    }
}
