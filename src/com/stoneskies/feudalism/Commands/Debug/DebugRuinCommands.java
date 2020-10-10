package com.stoneskies.feudalism.Commands.Debug;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Ruin.RuinAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugRuinCommands {
    public static boolean cmd(CommandSender sender, Command cmd, String label, String[] args) {
        boolean result = false;
        if(args.length != 0) {
            try {
                Resident mayor = TownyUniverse.getInstance().getDataSource().getResident(args[0]);
                Town town = mayor.getTown();
                result = RuinAPI.isRuined(town);
            } catch (NotRegisteredException e) {
                e.printStackTrace();
            }
            sender.sendMessage(String.valueOf(result));
            return true;
        }
        return false;
    }
}
