package com.josemarcellio.jlogin.runnable;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import com.josemarcellio.jlogin.util.Utility;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class JLoginRunnable extends BukkitRunnable {

    private final Player player;

    private final JLoginEvent event;

    private final JLogin plugin;

    private int timeOutTime;

    public JLoginRunnable(
            JLogin plugin, Player player, JLoginEvent event) {
        this.plugin = plugin;
        this.player = player;
        this.event = event;
        this.timeOutTime = plugin.getConfig()
                .getInt("TimeOut.Delay");
    }

    @Override
    public void run() {

        UUID playerId = player.getUniqueId();
        File playerFile = new File(plugin
                .getDataFolder(), "/data/playerdata.yml");

        YamlConfiguration playerData = YamlConfiguration
                .loadConfiguration(playerFile);

        FileConfiguration configuration = plugin.getConfig();

        if (!player.isOnline()) {
            cancel();
        }

        if (event.getStatus() == Status.PRE) {
            timeOutTime--;
            if (timeOutTime < 1) {

                String timeOut = configuration
                        .getString("TimeOut.Kick-Messages");
                player.kickPlayer(
                        ChatColor.translateAlternateColorCodes('&',
                                timeOut));
                cancel();
            }

            if (!playerData.contains("playerdata." + playerId)) {

                List<String> notRegistered = configuration
                        .getStringList("Messages.Not-Registered");

                notRegistered.replaceAll(line ->
                        line.replace("{captcha}",
                                plugin.getCaptcha().get(player))
                                .replace("{timeout}",
                                        String.valueOf(timeOutTime)));

                Utility.sendMessage(
                        player, notRegistered);

                String titlesNotRegistered = configuration
                        .getString("Titles.Not-Registered");

                titlesNotRegistered = titlesNotRegistered
                        .replace("{captcha}",
                                plugin.getCaptcha().get(player))
                        .replace("{timeout}",
                                String.valueOf(timeOutTime));

                Utility.sendTitle(
                        player, titlesNotRegistered);

            } else {

                List<String> notLoggedIn = configuration
                        .getStringList("Messages.Not-Logged-In");

                notLoggedIn.replaceAll(line ->
                        line.replace("{captcha}",
                                plugin.getCaptcha().get(player))
                                .replace("{timeout}",
                                        String.valueOf(timeOutTime)));

                Utility.sendMessage(player, notLoggedIn);

                String titlesNotLoggedIn = configuration
                        .getString("Titles.Not-Logged-In");

                titlesNotLoggedIn = titlesNotLoggedIn
                        .replace("{captcha}",
                                plugin.getCaptcha().get(player))
                        .replace("{timeout}",
                                String.valueOf(timeOutTime));

                Utility.sendTitle(
                        player, titlesNotLoggedIn);

            }
        } else {
            cancel();
        }
    }
}
