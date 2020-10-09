package com.github.hafixion.events;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.command.TownyAdminCommand;
import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.event.Listener;


public class TownRuin implements Listener {
    public Town town;
    private Towny plugin;
    TownyAdminCommand adminCommand = new TownyAdminCommand(plugin);

    public void onTownDelete(PreDeleteTownEvent event) {
        event.setCancelled(true);
        town = event.getTown();
        try {
            adminCommand.adminSet(new String[]{"mayor", town.getName(), "npc"});
        } catch (TownyException e) {
            e.printStackTrace();
        }
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
        try {
            TownyAdminCommand adminCommand = new TownyAdminCommand(plugin);
            adminCommand.parseAdminTownCommand(new String[]{town.getName(),"set", "perm", "reset"});

        } catch (Exception e) {
            System.out.println("Problem propogating perm changes to individual plots");
            e.printStackTrace();
        }
    }
}