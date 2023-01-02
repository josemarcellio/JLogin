package com.josemarcellio.jlogin.runnable;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public class JLoginRunnable extends BukkitRunnable {

    private final Player player;

    private final JLoginEvent event;

    private final JLogin plugin;

    public JLoginRunnable(
            JLogin plugin, Player player, JLoginEvent event) {
        this.plugin = plugin;
        this.player = player;
        this.event = event;
    }

    @Override
    public void run() {

        UUID playerId = player.getUniqueId();
        File playerFile = new File(plugin
                .getDataFolder(), "/data/playerdata.yml");

        YamlConfiguration playerData = YamlConfiguration
                .loadConfiguration(playerFile);

        FileConfiguration configuration = plugin.getConfig();

        if (event.getStatus() == Status.PRE) {
            if (!playerData.contains("playerdata." + playerId)) {
                String notRegistered = configuration
                        .getString("Messages.Not-Registered");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                notRegistered));
            } else {

                String notLoggedIn = configuration
                        .getString("Messages.Not-Logged-In");

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                notLoggedIn));
            }
        } else {
            cancel();
        }
    }
}
