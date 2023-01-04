package com.josemarcellio.jlogin.util;

import com.cryptomorin.xseries.messages.Titles;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Utility {

    public static void sendTitle(
            Player player, String format) {

        if (format == null) {
            return;
        }

        String[] parts = format
                .split(" \\| ");

        String title = ChatColor.translateAlternateColorCodes('&',
                parts[0]);

        String subtitle = parts.length > 1 ?
                ChatColor.translateAlternateColorCodes('&',
                        parts[1]) : "";

        Titles.sendTitle(
                player, 20, 20, 20,
                title, subtitle);
    }

    public static void sendMessage(
            Player player, List<String> message) {

        for (String line : message) {
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            line));
        }
    }

    public static void sendMessage(
            CommandSender sender, List<String> message) {

        for (String line : message) {
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            line));
        }
    }
}
