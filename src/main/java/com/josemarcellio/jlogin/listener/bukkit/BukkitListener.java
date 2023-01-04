package com.josemarcellio.jlogin.listener.bukkit;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

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

        this.plugin.getCaptcha().put(
                player, RandomStringUtils
                        .randomAlphanumeric(7));

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
                    && !event.getMessage().startsWith("/login")
                    && !event.getMessage().startsWith("/changepassword")) {

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

    @EventHandler
    public void onPlayerPickupItem(
            PlayerPickupItemEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(
            PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(
            PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (plugin.getLoginStatus()
                .get(player) == Status.PRE) {

            event.setCancelled(true);
        }
    }
}