package com.stoneskies.feudalism.Objects;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.Interfaces.RuinAPI;

import java.io.File;
import java.util.List;

import static com.stoneskies.feudalism.Interfaces.RuinAPI.*;

public class RuinedTown {
    static String name;

    public File getFile() {
        String filename = name + ".yml";
        File result = null;
        // if database is not empty
        if (checkDatabase(filename)) {
            // return the file
            result = ruinedtown;
        }
        return result;
    }

    public RuinedTown(String yourString) {
        name = yourString;
    }
    public RuinedTown(Town town) {
        name = town.getName();
    }

    public void delete() {
        String filename = name + ".yml";
        // if database is not empty
        if (checkDatabase(filename)) {
            try {
                // delete the mayor's town
                deleteTown(TownyUniverse.getInstance().getDataSource().getResident(ruinedtowndata.getString("mayor")).getTown());
            } catch (NotRegisteredException e) {
                e.printStackTrace();
            }
            // delete the file in the database
            ruinedtown.delete();
        }
    }

    public String getName() {
        return name;
    }

    public Town getTown() {
        String filename = name + ".yml";
        // if database is not empty
        Town result = null;
        if (RuinAPI.checkDatabase(filename)) {
            try {
                // get the mayor's town, normal get town just outputs null
                result = TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown();
            } catch (NotRegisteredException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<Resident> getResidents() {
        String filename = name + ".yml";
        // if database is not empty
        List<Resident> result = null;
        if (RuinAPI.checkDatabase(filename)) {
            try {
                // list the mayor's town's residents
                result = TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown().getResidents();
            } catch (NotRegisteredException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Nation getNation() {
        Nation result;
        try {
            // get the town's nation
            result = getTown().getNation();
        } catch (NotRegisteredException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public void reclaim(Resident resident) {
        String filename = name + ".yml";
        // if database is not empty
        if (checkDatabase(filename)) {
            try {
                // save the old mayor to memory
                Resident mayor = resident.getTown().getMayor();
                // set the reclaimer to be the mayor
                resident.getTown().SetMayor(resident);
                try {
                    // reset permissions to normal
                    for (String element : new String[]{"outsiderBuild",
                            "outsiderDestroy", "outsiderSwitch",
                            "outsiderItemUse", "allyBuild", "allyDestroy",
                            "allySwitch", "allyItemUse", "nationBuild", "nationDestroy",
                            "nationSwitch", "nationItemUse",
                            "pvp", "fire", "explosion", "mobs"}) {
                        resident.getTown().getPermissions().set(element, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // delete the old mayor from the towny db
                TownyAPI.getInstance().getDataSource().deleteResident(mayor);
                // set the new board
                resident.getTown().setBoard(getTown().getName() + "has returned under the leadership of " + mayor.getName());

            } catch (TownyException e) {
                e.printStackTrace();
            }
            // delete the file in the database
            ruinedtown.delete();
        }
    }
}