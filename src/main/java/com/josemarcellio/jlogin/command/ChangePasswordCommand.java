package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.status.Status;
import com.josemarcellio.jlogin.util.MessageDigestUtils;
import com.josemarcellio.jlogin.util.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

                List<String> alreadyLoggedIn = configuration
                        .getStringList("Messages.Not-Logged-In");

                Utility.sendMessage(player, alreadyLoggedIn);
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
                    List<String> passwordChanged = configuration
                            .getStringList("Messages.Password-Changed");

                    Utility.sendMessage(player, passwordChanged);
                } else {
                    List<String> incorrectPassword = configuration
                            .getStringList("Messages.Wrong-Password");

                    Utility.sendMessage(player, incorrectPassword);
                }
            } else {
                List<String> changePasswordUsage = configuration
                        .getStringList("Messages.Change-Password-Usage");

                Utility.sendMessage(player, changePasswordUsage);
            }
        } else {
            sender.sendMessage("Only player can use this command!");
        }
        return true;
    }
}
