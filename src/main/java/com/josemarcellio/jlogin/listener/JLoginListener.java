package com.josemarcellio.jlogin.listener;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.runnable.JLoginRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JLoginListener
        implements Listener {

    private final JLogin plugin;

    public JLoginListener(
            JLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJLogin(JLoginEvent event) {
        Player player = event.getPlayer();

        new JLoginRunnable(plugin, player, event)
                .runTaskTimer(plugin, 20L, 20L);
    }
}
