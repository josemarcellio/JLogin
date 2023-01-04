package com.josemarcellio.jlogin.api.events;


import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.status.Status;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class JLoginEvent extends Event {
    private static final HandlerList HANDLERS =
            new HandlerList();
    private final Player player;
    private final JLogin plugin;

    public JLoginEvent(
            JLogin plugin, Player player, Status status) {
        this.plugin = plugin;
        this.player = player;
        this.plugin.getLoginStatus().put(
                player, status);
    }

    public Player getPlayer() {
        return player;
    }

    public Status getStatus() {
        return this.plugin.getLoginStatus()
                .get(player);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}