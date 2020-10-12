package com.stoneskies.feudalism.Commands.Debug;

import com.stoneskies.feudalism.Interfaces.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class DebugCommands {
    public static void exec(CommandSender sender, String[] args) {
        // args here: /fd debug args[1] args[2] args[3]...
        if (!(args.length >= 2)) {
            // not enough arguments, return is required here!
            sender.sendMessage(ChatInfo.msg("&cSpecify debug command!"));
            return;
        }
        String[] newargs = Arrays.copyOfRange(args, 2, args.length); // args[] without the first 2 elements
        switch(args[1]) {
            case "townruin":
                // set the executor to be the debug ruin commands
                DebugRuinCommands.cmd(sender, newargs);
                break;

            case "npcclear":
                RuinAPI.clearresidentNPCs();
                sender.sendMessage(ChatInfo.msg("Task Executed."));
                break;

            default:
                // debug command invalid
                sender.sendMessage(ChatInfo.msg("&c" + args[1] + " is not registered"));
                break;
        }
    }
}
