package com.github.hafixion.Ruin;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugRuinCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean result = false;
        if (label.equalsIgnoreCase("townruin")) {
            if (args.length != 0) {
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
        }
        return false;
    }
}