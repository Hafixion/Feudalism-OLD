package com.stoneskies.feudalism.Util;

import org.bukkit.ChatColor;

public class ChatInfo {
    public static String msg(String text) {
        return ChatColor.translateAlternateColorCodes('&', "&6[Feudalism]&r&o " + text);
    }
}
