package com.josemarcellio.jlogin;

import com.josemarcellio.jlogin.command.LoginCommand;
import com.josemarcellio.jlogin.command.RegisterCommand;
import com.josemarcellio.jlogin.listener.*;
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

    private final Map<Player, Boolean> loginPlayer;

    public JLogin() {
        loginPlayer = new HashMap<>();
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

        AsyncPlayerChatListener asyncPlayerChatListener =
                new AsyncPlayerChatListener(this);
        getServer().getPluginManager().registerEvents(
                asyncPlayerChatListener, this);

        PlayerCommandPreProcessListener playerCommandPreProcessListener =
                new PlayerCommandPreProcessListener(this);
        getServer().getPluginManager().registerEvents(
                playerCommandPreProcessListener, this);

        PlayerMoveListener playerMoveListener =
                new PlayerMoveListener(this);
        getServer().getPluginManager().registerEvents(
                playerMoveListener, this);

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

    public Map<Player, Boolean> getLoginPlayer() {
        return loginPlayer;
    }
}
