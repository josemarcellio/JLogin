package com.josemarcellio.jlogin.listener;

import com.josemarcellio.jlogin.JLogin;
import com.josemarcellio.jlogin.api.events.JLoginEvent;
import com.josemarcellio.jlogin.api.status.Status;
import com.josemarcellio.jlogin.runnable.JLoginRunnable;
import com.josemarcellio.jlogin.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
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
    public void onJLogin(
            JLoginEvent event) {

        Player player = event.getPlayer();

        FileConfiguration configuration = plugin.getConfig();

        long delay = configuration.getLong("Task.Delay");
        long period = configuration.getLong("Task.Period");

        new JLoginRunnable(plugin, player, event)
                .runTaskTimer(plugin, delay, period);


        if (event.getStatus() == Status.REGISTER) {

            String captcha = plugin.getCaptcha().get(player);

            String registered = configuration
                    .getString("Titles.Registered");

            registered = registered
                    .replace("{captcha}", captcha);

            Utility.sendTitle(
                    player, registered);
        }

        if (event.getStatus() == Status.LOGIN) {

            String loggedIn = configuration
                    .getString("Titles.Logged-In");

            Utility.sendTitle(
                    player, loggedIn);
        }
    }
}
