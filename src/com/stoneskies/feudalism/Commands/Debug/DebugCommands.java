package com.stoneskies.feudalism.Commands.Debug;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class DebugCommands {
    public static void exec(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(args.length >= 2)) {
            sender.sendMessage("specify debug cmd!");
            return;
        }
        String[] newargs = Arrays.copyOfRange(args, 2, args.length); // args[] without the first 2 elements
        switch(args[1]) {
            case "townruin":
                DebugRuinCommands.cmd(sender, cmd, label, newargs);
                break;

            default:
                sender.sendMessage("invalid debug cmd!");
                return;
        }
    }
}
