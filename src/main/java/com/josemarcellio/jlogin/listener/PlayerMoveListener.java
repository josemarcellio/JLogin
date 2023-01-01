package com.josemarcellio.jlogin.listener;

import com.josemarcellio.jlogin.JLogin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.util.UUID;

public class PlayerMoveListener
        implements Listener {

    private final JLogin plugin;

    public PlayerMoveListener(JLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(
            PlayerMoveEvent event) {

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        File playerFile = new File(plugin
                .getDataFolder(), "/data/playerdata.yml");

        YamlConfiguration playerData = YamlConfiguration
                .loadConfiguration(playerFile);

        FileConfiguration configuration = plugin.getConfig();

        if (!(plugin.getLoginPlayer().containsKey(player))) {

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
            event.setCancelled(true);
        }
    }
}
