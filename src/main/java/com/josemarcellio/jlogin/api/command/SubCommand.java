package com.josemarcellio.jlogin.api.command;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract
    String getCommandName();

    public abstract
    String getPermission();

    public abstract
    void execute(
            CommandSender sender, String[] args);
}