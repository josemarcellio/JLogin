package com.josemarcellio.jlogin;

import com.josemarcellio.jlogin.api.status.Status;
import com.josemarcellio.jlogin.command.LoginCommand;
import com.josemarcellio.jlogin.command.RegisterCommand;
import com.josemarcellio.jlogin.listener.*;
import com.josemarcellio.jlogin.listener.bukkit.BukkitListener;
import com.josemarcellio.jlogin.log4j.Log4JFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class JLogin extends JavaPlugin {

    private final Map<Player, Status> loginStatus;

    public JLogin() {
        loginStatus = new HashMap<>();
    }

    @Override
    public void onEnable() {

        new Metrics(this, 17255);

        getLogger().info("JLogin by JoseMarcellio");

        saveDefaultConfig();

        RegisterCommand registerCommand =
                new RegisterCommand(this);
        getCommand("register").setExecutor(registerCommand);

        LoginCommand loginCommand =
                new LoginCommand(this);
        getCommand("login").setExecutor(loginCommand);

        BukkitListener bukkitListener =
                new BukkitListener(this);
        getServer().getPluginManager().registerEvents(
                bukkitListener, this);

        JLoginListener jLoginListener =
                new JLoginListener(this);
        getServer().getPluginManager().registerEvents(
                jLoginListener, this);

        LoggerContext context = (LoggerContext) LogManager
                .getContext(false);
        Configuration config = context.getConfiguration();
        LoggerConfig loggerConfig = config
                .getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

        Log4JFilter log4JFilter = new Log4JFilter();
        loggerConfig.addFilter(log4JFilter);
        context.updateLoggers();
    }

    @Override
    public void onDisable() {
        getLogger().info("JLogin by JoseMarcellio");
    }

    public Map<Player, Status> getLoginStatus() {
        return loginStatus;
    }
}
