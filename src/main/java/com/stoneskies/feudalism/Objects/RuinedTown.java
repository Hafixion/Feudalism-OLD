package com.stoneskies.feudalism.Objects;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
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
}