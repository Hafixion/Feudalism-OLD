package com.stoneskies.feudalism.Ruin;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.stoneskies.feudalism.FeudalismMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RuinAPI {
    public static File ruinedtown;
    public static YamlConfiguration ruinedtowndata;
    public static Runnable ExpiredRuinedTownPurge = RuinAPI::PurgeExpiredRuinedTowns;
    public static Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");

    /**
     * Purges the entire ruined database.
     */
    public static void PurgeRuinedTowns() {
        Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        if (!(ruinedtowns == null)) {
            for (File ruinedtown : ruinedtowns) {
                try {
                    ruinedtowndata.load(ruinedtown);
                    try {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[Feudalism] &7" + ruinedtowndata.get("name") + " has finally fallen into history"));
                        TownRuin.deleteRuinedTown(TownyUniverse.getInstance().getDataSource().getResident(String.valueOf(ruinedtowndata.get("mayor"))).getTown());
                        ruinedtown.delete();
                    } catch (NotRegisteredException e) {
                        e.printStackTrace();
                    }
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Feudalism]&7 No files found to purge"));
        }
    }

    /**
     * Purges current expired ruined towns in the database.
     */
    public static void PurgeExpiredRuinedTowns() {
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        if (ruinedtowns != null) {
            for (File ruinedtown : ruinedtowns) {
                try {
                    ruinedtowndata.load(ruinedtown);
                    if (ruinedtowndata.contains("time-fallen")) {
                        long time = (long) ruinedtowndata.get("time-fallen");
                        if (System.currentTimeMillis() - time >= FeudalismMain.plugin.getConfig().getLong("time-till-expiration")) {
                            try {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[Feudalism] &7" + ruinedtowndata.get("name") + " has finally fallen into history"));
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
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Feudalism]&7 No files found to purge"));
        }
    }

    /**
     * Checks if town is ruined or not
     *
     * @param town town to check if ruined or not
     * @return Whether town is ruined or not
     */
    public static boolean isRuined(Town town) {
        String ruinedtownstring = town.getName() + ".yml";
        // file of the inputted town
        File townie = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        boolean result = false;
        if (townie.exists()) {
            result = true;
        }
        return result;
    }

    /**
     * Adds a town to the ruined town database
     *
     * @param town town entity
     * @param time current time (used to count when town will fall)
     */
    public static void SaveRuinedTown(Town town, long time) {
        String ruinedtownstring = town.getName() + ".yml";
        ruinedtown = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        if (!ruinedtown.exists()) {
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
        ruinedtowndata.set("name", town.getName());
        ruinedtowndata.set("mayor", town.getMayor().getName());
        try {
            ruinedtowndata.save(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
