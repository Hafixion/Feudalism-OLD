package com.github.hafixion.events.Ruin;

import com.github.hafixion.FeudalismMain;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.command.TownyAdminCommand;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.event.PreDeleteTownEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TownRuin implements Listener {
    public static Town town;
    private static Towny plugin;
    public String originalname;
    static TownyAdminCommand adminCommand = new TownyAdminCommand(plugin);

    @EventHandler
    public void onTownDelete(PreDeleteTownEvent event) {
        town = event.getTown();
        if (!town.getMayor().isNPC()) {
            event.setCancelled(true);
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
                adminCommand.parseAdminTownCommand(new String[]{town.getName(), "set", "perm", "reset"});

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
            RuinAPI.SaveRuinedTown(town, originalname, time);
        } else {
            event.setCancelled(false);
        }
    }
    @EventHandler
    public void onNewDay(NewDayEvent event) {
        RuinAPI.PurgeExpiredRuinedTowns();
    }

    public static void deleteRuinedTown(Town town) {
        try {
            adminCommand.parseAdminTownCommand(new String[] {town.getName(), "delete"});
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        } catch (TownyException e) {
            e.printStackTrace();
        }
    }
}