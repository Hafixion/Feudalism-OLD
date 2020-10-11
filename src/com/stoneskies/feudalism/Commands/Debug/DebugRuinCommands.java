package com.stoneskies.feudalism.Commands.Debug;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Ruin.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.command.CommandSender;

public class DebugRuinCommands {
    public static void cmd(CommandSender sender, String[] args) {
        boolean result = false;
        if(args.length != 0) {
            switch(args[0]) {
                case "purge":
                    RuinAPI.PurgeRuinedTowns();
                    break;
                case "purgeexpired":
                    RuinAPI.PurgeExpiredRuinedTowns();
                    break;
                case "mayor":
                    if(args.length >= 2) {
                        try {
                            // check mayor's town
                            Resident mayor = TownyUniverse.getInstance().getDataSource().getResident(args[1]);
                            Town town = mayor.getTown();
                            // return whatever the isRuined() value of said town is.
                            result = RuinAPI.isRuined(town);
                        } catch (NotRegisteredException e) {
                            e.printStackTrace();
                        }
                        // send it to the player
                        sender.sendMessage(String.valueOf(result));
                    } else {
                        sender.sendMessage(ChatInfo.msg("&cSpecify a mayor!"));
                    }
                    break;
                default:
                    sender.sendMessage(ChatInfo.msg("&c" + args[0] + " is not registered"));
            }
        } else {
            sender.sendMessage(ChatInfo.msg("&cPlease specify an argument!"));
        }
        return;
    }
}
