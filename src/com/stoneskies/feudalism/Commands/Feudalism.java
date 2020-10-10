package com.stoneskies.feudalism.Commands;

import com.stoneskies.feudalism.Commands.Debug.DebugCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Feudalism implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("fd")) {
            if(args.length != 0) {
                switch(args[0]) {
                    case "debug":
                        DebugCommands.exec(sender, cmd, label, args);
                        break;

                    default:
                        sender.sendMessage("invalid arg!");
                        return false;
                }
            } else {
                //todo HAF print basic plugin info, something like stoneskies feudalism, its version, repo, authors etc
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&lStoneskies Feudalism"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "//"));
                return true;
            }
        }
        return false;
    }
}
