package com.stoneskies.feudalism.Util;

import com.stoneskies.feudalism.FeudalismMain;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

import static com.palmergames.bukkit.util.BukkitTools.getServer;

public class ConfigLoad {
    public static File configFile = new File(FeudalismMain.plugin.getDataFolder(), "config.yml"); // config.yml file var
    public static Plugin plugin = FeudalismMain.plugin;

    public static void LoadConfig() {
        if(!configFile.exists()) {
            plugin.saveDefaultConfig();  // if it doesn't exist, save the default one
        }
        try {
            plugin.getConfig().load(configFile); // load the config
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) { // if config is invalid
            getServer().getConsoleSender().sendMessage(ChatInfo.msg("&7config.yml invalid! loading default config..."));
            // delete the configfile and load the default one
            configFile.delete();
            plugin.saveDefaultConfig();
        }
    }
}
