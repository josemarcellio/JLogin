package com.josemarcellio.jlogin.hex;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColor {

    public final Pattern HEX_PATTERN = Pattern.compile(
            "&#(\\w{5}[0-9a-f])");

    public String getColor(
            String string) {

        Matcher matcher = HEX_PATTERN.matcher(string);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer,
                    ChatColor.of(
                            "#" + matcher.group(1))
                            .toString());
        }

        return ChatColor.translateAlternateColorCodes(
                '&',
                matcher.appendTail(buffer).toString());

    }
}
