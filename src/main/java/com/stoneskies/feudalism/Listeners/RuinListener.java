package com.stoneskies.feudalism.Listeners;

import com.palmergames.bukkit.towny.command.TownyAdminCommand;
import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Interfaces.RuinAPI;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RuinListener implements Listener {
    public static Town town;
    private static final TownyAdminCommand adminCommand = new TownyAdminCommand(null);


    @EventHandler
    public void onTownDelete(PreDeleteTownEvent event) {
        town = event.getTown();
        RuinAPI.clearresidentNPCs();
        // if ruined towns in config are enabled
        if(FeudalismMain.plugin.getConfig().getBoolean("ruin-enabled")) {
            // if the mayor is npc, helps with ruined town purge
            if (!town.getMayor().isNPC()) {
                event.setCancelled(true);
                // make the mayor an npc
                try {
                    adminCommand.adminSet(new String[]{"mayor", town.getName(), "npc"});
                } catch (TownyException e) {
                    e.printStackTrace();
                }
                if (FeudalismMain.plugin.getConfig().getBoolean("enable-permissions")) {
                    // if permissions are enabled, set perms to rnao to allow for raiding
                    try {
                        for (String element : new String[]{"residentBuild",
                                "residentDestroy", "residentSwitch",
                                "residentItemUse", "outsiderBuild",
                                "outsiderDestroy", "outsiderSwitch",
                                "outsiderItemUse", "allyBuild", "allyDestroy",
                                "allySwitch", "allyItemUse", "nationBuild", "nationDestroy",
                                "nationSwitch", "nationItemUse",
                                "pvp", "fire", "explosion", "mobs"}) {
                            town.getPermissions().set(element, true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TownyAdminCommand adminCommand = new TownyAdminCommand(null);
                    // make all plots have default permission, helps with raids in owned plots.
                    adminCommand.parseAdminTownCommand(new String[]{town.getName(), "set", "perm", "reset"});
                } catch (Exception e) {
                    System.out.println("Problem propagating perm changes to individual plots");
                    e.printStackTrace();
                }
                town.setBoard(town.getName() + " has fallen into ruin!");
                town.getMayor().setTitle("Ruined Mayor ");
                town.setPublic(false);
                town.setOpen(false);
                // save the current time to memory
                long time = System.currentTimeMillis();
                // save the ruined town to database
                RuinAPI.SaveRuinedTown(town, time);
                Bukkit.broadcastMessage(ChatInfo.msg("&7" + town.getName() + " has become a ruined town."));
            } else {
                // if config ruin-enabled is false don't do anything
                event.setCancelled(false);
            }
        }
    }
}
