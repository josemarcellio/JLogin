package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import com.josemarcellio.jlogin.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.util.Arrays;
import java.util.List;
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

            if (plugin.getLoginStatus().get(player) != Status.PRE) {

                List<String> alreadyLoggedIn = configuration
                        .getStringList("Messages.Already-Logged-In");

                Utility.sendMessage(player, alreadyLoggedIn);
                return true;
            }

            if (playerData.contains("playerdata." + playerId.toString())) {

                if (args.length == 2) {
                    String password = args[0];
                    String enteredCaptcha = args[1];

                    String expectedCaptcha = plugin.getCaptcha().get(player);
                    if (!enteredCaptcha.equals(expectedCaptcha)) {
                        List<String> wrongCaptcha = configuration
                                .getStringList("Messages.Wrong-Captcha");

                        Utility.sendMessage(player, wrongCaptcha);
                        return true;
                    }

                    String storedHashedPasswordString = playerData
                            .getString("playerdata." + playerId + ".password");

                    byte[] storedHashedPassword = DatatypeConverter
                            .parseHexBinary(
                                    storedHashedPasswordString);

                    byte[] enteredHashedPassword =
                            plugin.getEncryption().encryptPassword(
                                    password);

                    if (Arrays.equals(storedHashedPassword,
                            enteredHashedPassword)) {

                        plugin.getLoginStatus().put(
                                player, Status.LOGIN);

                        List<String> successfullyLoggedIn = configuration
                                .getStringList("Messages.Successfully-Logged-In");

                        Utility.sendMessage(player, successfullyLoggedIn);

                        JLoginEvent jloginEvent = new JLoginEvent(plugin,
                                player, Status.LOGIN);
                        Bukkit.getServer().getPluginManager().callEvent(
                                jloginEvent);

                    } else {

                        List<String> wrongPassword = configuration
                                .getStringList("Messages.Wrong-Password");

                        Utility.sendMessage(player, wrongPassword);
                    }
                } else {

                    List<String> loginUsage = configuration
                            .getStringList("Messages.Login-Usage");

                    loginUsage.replaceAll(line ->
                            line.replace("{captcha}",
                                    plugin.getCaptcha().get(player)));

                    Utility.sendMessage(player, loginUsage);
                }
            } else {

                List<String> notRegistered = configuration
                        .getStringList("Messages.Not-Registered");

                Utility.sendMessage(player, notRegistered);
            }
        } else {
            sender.sendMessage("Only player can use this command!");
        }
        return true;
    }
}