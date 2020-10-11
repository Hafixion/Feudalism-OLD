package com.stoneskies.feudalism.Ruin;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import java.io.File;
import java.util.List;

import static com.stoneskies.feudalism.Ruin.RuinAPI.*;

public class RuinedTown {
    static String name;

    public File getFile() {
        String filename = name + ".yml";
        File[] ruinedtowns = datafolder.toFile().listFiles();
        File result = null;
        // if database is not empty
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                if (ruinedtown.getName().equals(filename)) {
                    result = ruinedtown;
                }
            }
        }
        return result;
    }

    public RuinedTown(String yourString) {
        name = yourString;
    }

    public void delete() {
        String filename = name + ".yml";
        File[] ruinedtowns = datafolder.toFile().listFiles();
        // if database is not empty
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                if (ruinedtown.getName().equals(filename)) {
                    try {
                        deleteTown(TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown());
                    } catch (NotRegisteredException e) {
                        e.printStackTrace();
                    }
                    ruinedtown.delete();
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public Town getTown() {
        String filename = name + ".yml";
        File[] ruinedtowns = datafolder.toFile().listFiles();
        // if database is not empty
        Town result = null;
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                if (ruinedtown.getName().equals(filename)) {
                    try {
                        result = TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown();
                    } catch (NotRegisteredException e) {
                        e.printStackTrace();
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public List<Resident> getResidents() {
        String filename = name + ".yml";
        File[] ruinedtowns = datafolder.toFile().listFiles();
        // if database is not empty
        List<Resident> result = null;
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                if (ruinedtown.getName().equals(filename)) {
                    try {
                        result = TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown().getResidents();
                    } catch (NotRegisteredException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}