package com.stoneskies.feudalism.Commands;

import com.stoneskies.feudalism.Commands.Debug.DebugCommands;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Feudalism implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // args here: /fd args[0] args[1] args[2]...
        if(label.equalsIgnoreCase("fd")) {
            if(args.length != 0) {
                switch(args[0]) {
                    case "debug":
                        // set the executor to be the debug commands class
                        DebugCommands.exec(sender, args);
                        break;

                    default:
                        // argument invalid
                        sender.sendMessage(ChatInfo.msg("&c" + args[0] + " is not registered"));
                        break;
                }
            } else {
                // send plugin information
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[Stoneskies Feudalism]"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eVersion: 0.00.010"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eAuthors: zydde, Hafixion"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eGithub: https://github.com/Stoneskies/Feudalism"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7A plugin that adds complex war to towny"));
            }
        }
        return false;
    }
}
