package com.stoneskies.feudalism.Commands.Debug;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class DebugCommands {
    public static void exec(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(args.length >= 2)) {
            // not enough arguments
            sender.sendMessage(ChatColor.RED + "specify debug command!");
            return;
        }
        String[] newargs = Arrays.copyOfRange(args, 2, args.length); // args[] without the first 2 elements
        switch(args[1]) {
            case "townruin":
                // set the executor to be the debug ruin commands
                DebugRuinCommands.cmd(sender, cmd, label, newargs);
                break;

            default:
                // debug command invalid
                sender.sendMessage(ChatColor.RED + "invalid debug command!");
        }
    }
}
