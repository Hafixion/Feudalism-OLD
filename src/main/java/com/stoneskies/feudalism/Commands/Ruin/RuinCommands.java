package com.stoneskies.feudalism.Commands.Ruin;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Interfaces.RuinAPI;
import com.stoneskies.feudalism.Objects.RuinedTown;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class RuinCommands {

    public static void exec(CommandSender sender, String[] args) {
        Resident resident = null;
        RuinedTown ruinedTown;
        try {
            resident = TownyAPI.getInstance().getDataSource().getResident(sender.getName());
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }
        if (args.length >= 2) {
            switch (args[1]) {
                // reclaim command
                case "reclaim":
                    if (resident != null) {
                        if (FeudalismMain.plugin.getConfig().getBoolean("enable-resident-reclaiming")) {
                            if (resident.hasTown()) {
                                try {
                                    // see if the resident's town is ruined
                                    if (RuinAPI.isRuined(resident.getTown())) {
                                        // create a new ruinedtown obj
                                        ruinedTown = new RuinedTown(resident.getTown());
                                        // reclaim the town under the resident's name
                                        ruinedTown.reclaim(resident);
                                        resident.getPlayer().sendMessage(ChatInfo.msg("&7Town reclaimed, lead it into a better era."));
                                        Bukkit.broadcastMessage(ChatInfo.msg("&7" + resident.getName() + " has reclaimed " + resident.getTown().getName()));
                                    } else {
                                        resident.getPlayer().sendMessage(ChatInfo.msg("&cYour town isn't ruined."));
                                    }
                                } catch (NotRegisteredException e) {
                                    e.printStackTrace();
                                    resident.getPlayer().sendMessage(ChatInfo.msg("&cYou aren't in a town."));
                                }
                            } else {
                                resident.getPlayer().sendMessage(ChatInfo.msg("&cYou aren't in a town."));
                            }
                        }
                    } else {
                        sender.sendMessage(ChatInfo.msg("&cResident reclamation is disabled."));
                    }
            }
        } else {
            sender.sendMessage(ChatInfo.msg("&cPlease specify an argument!"));
        }
    }
}
