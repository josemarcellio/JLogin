package com.josemarcellio.jlogin.util;

import com.cryptomorin.xseries.messages.Titles;
import com.josemarcellio.jlogin.hex.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Utility {

    public static Byte version() {
        return Byte.parseByte( Bukkit.getServer()
                .getClass().getName().split("\\.")[3]
                .split("_")[1]);
    }

    public static String getColor(
            String format) {

        if (version() < 16) {
            return ChatColor.translateAlternateColorCodes(
                    '&',
                    format);
        } else {
            return new HexColor().getColor(format);
        }
    }

    public static void sendTitle(
            Player player, String format) {

        if (format == null) {
            return;
        }

        String[] parts = format
                .split(" \\| ");

        String title = getColor(parts[0]);

        String subtitle = parts.length > 1 ?
                getColor(parts[1]) : "";

        Titles.sendTitle(
                player, 20, 20, 20,
                title, subtitle);
    }

    public static void sendMessage(
            Player player, List<String> message) {

        for (String line : message) {
            player.sendMessage(
                    getColor(line));
        }
    }

    public static void sendMessage(
            CommandSender sender, List<String> message) {

        for (String line : message) {
            sender.sendMessage(
                    getColor(line));
        }
    }
}
