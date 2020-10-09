package com.github.hafixion;

import com.github.hafixion.events.TownRuin;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

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

    public static void SaveRuinedTown(Town town, String originalname, long time) {
        String ruinedtownstring = originalname + ".yml";
        ruinedtown = new File("plugins/Feudalism", ruinedtownstring);
        ruinedtowndata = new YamlConfiguration();
        if(!ruinedtown.exists()) {
            try {
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
        try {
            ruinedtowndata.save(ruinedtown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
