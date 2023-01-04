package com.josemarcellio.jlogin.command;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import com.josemarcellio.jlogin.util.MessageDigestUtils;
import com.josemarcellio.jlogin.util.Utility;
import org.bukkit.Bukkit;
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
import java.util.List;
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

            if (plugin.getLoginStatus().get(player) != Status.PRE) {

                List<String> alreadyLoggedIn = configuration
                        .getStringList("Messages.Already-Logged-In");

                Utility.sendMessage(player, alreadyLoggedIn);
                return true;
            }

            if (!playerData.contains("playerdata." + playerId.toString())) {

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
                    plugin.getLoginStatus().put(
                            player, Status.REGISTER);

                    List<String> successfullyRegistered = configuration
                            .getStringList("Messages.Successfully-Registered");

                    Utility.sendMessage(player, successfullyRegistered);

                    JLoginEvent jloginEvent = new JLoginEvent(plugin,
                            player, Status.REGISTER);
                    Bukkit.getServer().getPluginManager().callEvent(
                            jloginEvent);

                } else {

                    List<String> registeredUsage = configuration
                            .getStringList("Messages.Registered-Usage");

                    registeredUsage.replaceAll(line ->
                            line.replace("{captcha}",
                                    plugin.getCaptcha().get(player)));

                    Utility.sendMessage(player, registeredUsage);
                }
            } else {

                List<String> alreadyRegistered = configuration
                        .getStringList("Messages.Already-Registered");

                Utility.sendMessage(player, alreadyRegistered);
            }
        } else {
            sender.sendMessage("Only player can use this command!");
        }
        return true;
    }

}