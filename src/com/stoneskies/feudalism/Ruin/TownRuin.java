package com.stoneskies.feudalism.Ruin;

import com.palmergames.bukkit.towny.command.TownyAdminCommand;
import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownRuin implements Listener {
    public static Town town;
    static TownyAdminCommand adminCommand = new TownyAdminCommand(null);

    @EventHandler
    public void onTownDelete(PreDeleteTownEvent event) {
        town = event.getTown();
        if(FeudalismMain.plugin.getConfig().getBoolean("enabled")) {
            if (!town.getMayor().isNPC()) {
                event.setCancelled(true);
                try {
                    adminCommand.adminSet(new String[]{"mayor", town.getName(), "npc"});
                } catch (TownyException e) {
                    e.printStackTrace();
                }
                if (FeudalismMain.plugin.getConfig().getBoolean("enable-permissions")) {
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
                    adminCommand.parseAdminTownCommand(new String[]{town.getName(), "set", "perm", "reset"});
                } catch (Exception e) {
                    System.out.println("Problem propagating perm changes to individual plots");
                    e.printStackTrace();
                }
                town.setBoard(town.getName() + " has fallen into ruin!");
                town.getMayor().setTitle("Ruined Mayor ");
                town.setPublic(false);
                town.setOpen(false);
                long time = System.currentTimeMillis();
                RuinAPI.SaveRuinedTown(town, time);
                Bukkit.broadcastMessage("§6[Feudalism] §7" + town.getName() + " has become a ruined town.");
            } else {
                event.setCancelled(false);
            }
        }
    }
    public static void deleteRuinedTown(Town town) {
        try {
            adminCommand.parseAdminTownCommand(new String[] {town.getName(), "delete"});
        } catch (TownyException e) {
            e.printStackTrace();
        }
    }
}
