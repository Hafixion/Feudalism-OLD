package com.github.hafixion.Ruin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugPurgeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("townpurge")) {
            RuinAPI.PurgeExpiredRuinedTowns();
            RuinAPI.PurgeRuinedTowns();
            return true;
        }
        return false;
    }
}
