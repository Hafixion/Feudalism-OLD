package com.github.hafixion;

import com.github.hafixion.events.TownRuin;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FeudalismMain<DataFolder> extends JavaPlugin {
    public static File ruinedtown;
    public static FileConfiguration ruinedtowndata;

    @Override
    public void onEnable() {
        // register ruined towns
        getServer().getPluginManager().registerEvents(new TownRuin(), this);
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Loaded Successfully.");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Unloaded Successfully.");
    }

    public static void SaveRuinedTown(Town town, String originalname, long time, String name) {
        String ruinedtownstring = originalname + ".yml";
        ruinedtown = new File("plugins/Feudalism/database/ruinedtowns", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        if(!ruinedtown.exists()) {
            try {
                ruinedtown.mkdirs();
                ruinedtown.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ruinedtowndata.load(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        ruinedtowndata.set("time-fallen", time);
        ruinedtowndata.set("original-name", originalname);
        ruinedtowndata.set("name", name);
        try {
            ruinedtowndata.save(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PurgeRuinedTowns() {
        Path datafolder = Paths.get("plugins/Feudalism/database/ruinedtowns");
        File[] ruinedtowns = datafolder.toFile().listFiles();
        ruinedtowndata = new YamlConfiguration();
        for(File ruinedtown : ruinedtowns) {
            try {
                ruinedtowndata.load(ruinedtown);
                if(ruinedtowndata.contains("time-fallen")) {
                    long time = (long) ruinedtowndata.get("time-fallen");
                    if(System.currentTimeMillis() - time > 86400000) {
                        TownRuin.deleteRuinedTown((String) ruinedtowndata.get("name"));
                       ruinedtown.delete();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
}
