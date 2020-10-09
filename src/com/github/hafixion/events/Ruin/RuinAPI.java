package com.github.hafixion.events.Ruin;

import com.github.hafixion.FeudalismMain;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RuinAPI {
    public static File ruinedtown;
    public static YamlConfiguration ruinedtowndata;

    /**
     * Purges the entire ruined database.
     */
    public static void PurgeRuinedTowns() {
        Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        for(File ruinedtown : ruinedtowns) {
            try {
                ruinedtowndata.load(ruinedtown);
                try {
                    TownRuin.deleteRuinedTown(TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown());
                    ruinedtown.delete();
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Purges current expired ruined towns in the database.
     */
    public static void PurgeExpiredRuinedTowns() {
        Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        for(File ruinedtown : ruinedtowns) {
            try {
                ruinedtowndata.load(ruinedtown);
                if(ruinedtowndata.contains("time-fallen")) {
                    long time = (long) ruinedtowndata.get("time-fallen");
                    if(System.currentTimeMillis() - time > 86400000) {
                        try {
                            TownRuin.deleteRuinedTown(TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown());
                            ruinedtown.delete();
                        } catch (NotRegisteredException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isRuined(Town town) {
        Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        for (File ruinedtown : ruinedtowns) {
            try {
                ruinedtowndata.load(ruinedtown);
                return ruinedtowndata.get("mayor") == town.getMayor().getName();
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * Adds a town to the ruined town database
     * @param town town entity
     * @param originalname original name of the town (before getting ruined)
     * @param time current time (used to count when town will fall)
     */
    public static void SaveRuinedTown(Town town, String originalname, long time) {
        String ruinedtownstring = town.getName() + ".yml";
        ruinedtown = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        if(!ruinedtown.exists()) {
            try {
                ruinedtown.getParentFile().mkdir();
                ruinedtown.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ruinedtowndata.load(ruinedtown);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        ruinedtowndata.set("time-fallen", time);
        ruinedtowndata.set("original-name", originalname);
        ruinedtowndata.set("mayor", town.getMayor());
        try {
            ruinedtowndata.save(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
