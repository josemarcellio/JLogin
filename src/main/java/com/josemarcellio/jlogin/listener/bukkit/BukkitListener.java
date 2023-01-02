package com.josemarcellio.jlogin.listener.bukkit;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BukkitListener
        implements Listener {

    private final JLogin plugin;

    public BukkitListener(
            JLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(
            PlayerJoinEvent event) {

        Player player = event.getPlayer();
        JLoginEvent jloginEvent = new JLoginEvent(plugin,
                player, Status.PRE);

        Bukkit.getServer().getPluginManager().callEvent(
                jloginEvent);
    }

    @EventHandler
    public void onAsyncPlayerChat(
            AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(
            PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            if (!event.getMessage().startsWith("/register")
                    && !event.getMessage().startsWith("/login")) {

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(
            PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(
            BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(
            BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }
}