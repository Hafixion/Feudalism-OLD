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
                        // set the executor to be the debug commands class
                        DebugCommands.exec(sender, cmd, label, args);
                        break;

                    default:
                        // argument invalid
                        sender.sendMessage(ChatColor.RED + args[0] + " is not registered");
                        return false;
                }
            } else {
                // send plugin information
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[Stoneskies Feudalism]"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eVersion: 0.00.010"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eAuthors: zydde, Hafixion"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eGithub: https://github.com/Stoneskies/Feudalism"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7A plugin that adds complex war to towny"));
                return true;
            }
        }
        return false;
    }
}
