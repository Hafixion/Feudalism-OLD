package com.github.hafixion.events;

import com.github.hafixion.FeudalismMain;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.command.TownyAdminCommand;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownRuin implements Listener {
    public Town town;
    private Towny plugin;
    public String originalname;
    int random;
    TownyAdminCommand adminCommand = new TownyAdminCommand(plugin);
    @EventHandler
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
        town.setBoard(town.getName() + " has fallen into ruin!");
        originalname = town.getName();
        town.setName("Ruined_Town_of_" + town.getName());
        town.getMayor().setTitle("Ruined Mayor ");
        town.setPublic(false);
        town.setOpen(false);
        long time = System.currentTimeMillis();
        FeudalismMain.SaveRuinedTown(town, originalname, time, town.getName());
    }
    @EventHandler
    public void onNewDay(NewDayEvent event) {
        FeudalismMain.ruinedtown.getPath();
    }

    public static void deleteRuinedTown(String townname) {
        try {
            Town ruinedtown = TownyUniverse.getInstance().getDataSource().getTown(townname);
            adminCommand.parseAdminTownCommand(new String[] {town.getName(), "delete"});
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        } catch (TownyException e) {
            e.printStackTrace();
        }
    }
}