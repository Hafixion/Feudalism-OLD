package com.stoneskies.feudalism.Interfaces;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.command.TownyAdminCommand;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import com.stoneskies.feudalism.Objects.RuinedTown;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RuinAPI {
    public static File ruinedtown;
    public static YamlConfiguration ruinedtowndata;
    public static Runnable ExpiredRuinedTownPurge = RuinAPI::PurgeExpiredRuinedTowns;
    public static Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
    private static final TownyAdminCommand adminCommand = new TownyAdminCommand(null);

    /**
     * Purges the entire ruined database.
     */

    //TODO merge PurgeRuinedTowns() and PurgeExpiredRuinedTowns() into one method in the future, since they have a lot of duplicate code
    public static void PurgeRuinedTowns() {
        Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        // if there are files in the database
        if (!(ruinedtowns == null)) {
            for (File ruinedtown : ruinedtowns) {
                try {
                    ruinedtowndata.load(ruinedtown);
                    Bukkit.broadcastMessage(ChatInfo.msg("&7" + ruinedtowndata.getString("name") + " has finally fallen into history"));
                    // process the deletion
                    RuinedTown ruinedTown = new RuinedTown(ruinedtowndata.getString("name"));
                    ruinedTown.delete();
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // database is empty, send error.
            Bukkit.getConsoleSender().sendMessage(ChatInfo.msg("&7No files found to purge"));
        }
    }

    /**
     * Purges current expired ruined towns in the database.
     */
    public static void PurgeExpiredRuinedTowns() {
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        // if database is not empty
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                try {
                    ruinedtowndata.load(ruinedtown);
                    if (ruinedtowndata.contains("time-fallen")) {
                        long time = ruinedtowndata.getLong("time-fallen");
                        // calculate if time passed since fallen if greater than the time till expiration
                        if (System.currentTimeMillis() - time >= FeudalismMain.plugin.getConfig().getLong("time-till-expiration")) {
                            Bukkit.broadcastMessage(ChatInfo.msg("&7" + ruinedtowndata.get("name") + " has finally fallen into history"));
                            // process the deletion
                            RuinedTown ruinedTown = new RuinedTown(ruinedtowndata.getString("name"));
                            ruinedTown.delete();
                        }
                    }
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // no ruined towns found in the database
            Bukkit.getConsoleSender().sendMessage(ChatInfo.msg("&7No files found to purge"));
        }
    }
    /**
     * Adds a town to the ruined town database
     *
     * @param town town entity
     * @param time current time (used to count when town will fall)
     */
    public static void SaveRuinedTown(Town town, long time) {
        // name of the town file, town.yml
        String ruinedtownstring = town.getName() + ".yml";
        ruinedtown = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        // if the file doesn't exist
        if (!ruinedtown.exists()) {
            try {
                // create it
                ruinedtown.getParentFile().mkdir();
                ruinedtown.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // load the file's data
        try {
            ruinedtowndata.load(ruinedtown);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        // set the variables
        ruinedtowndata.set("time-fallen", time);
        ruinedtowndata.set("name", town.getName());
        ruinedtowndata.set("mayor", town.getMayor().getName());
        try {
            // save the file
            ruinedtowndata.save(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes specified town
     * @param town Town to delete
     */
    public static void deleteTown(Town town) {
        try {
            // delete town specified
            adminCommand.parseAdminTownCommand(new String[] {town.getName(), "delete"});
        } catch (TownyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string if the specified town is ruined, otherwise returns null
     * @param town town you want to check
     * @return string
     */
    public static RuinedTown getRuinedTown(Town town) {
        // name of the file, town.yml
        String ruinedtownstring = town.getName() + ".yml";
        // file of the inputted town
        File townie = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        if(townie.exists()) {
           return new RuinedTown(town.getName());
        } else {return null;}
    }
    public static boolean isRuined(Town town) {
        // name of the file, town.yml
        String ruinedtownstring = town.getName() + ".yml";
        // file of the inputted town
        File townie = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        RuinAPI.ruinedtowndata = new YamlConfiguration();
        boolean result = false;
        // if the file exists
        if (townie.exists()) {
            result = true;
        }
        return result;
    }

    public static boolean checkDatabase(String filename) {
        File[] ruinedtowns = datafolder.toFile().listFiles();
        boolean result = false;
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                result = ruinedtown.getName().equals(filename);
            }
        } return result;
    }

    public static void clearNPCs() {
        // get a list of the residents in the towny db
        List<Resident> residentList = TownyUniverse.getInstance().getDataSource().getResidents();
        // get an array out of the above list
        Resident[] residents = residentList.toArray(new Resident[0]);
        for(Resident resident : residents) {
            // if resident is npc
            if(resident.isNPC()) {
                // delete them
                TownyAPI.getInstance().getDataSource().deleteResident(resident);
            }
        }
    }
}
