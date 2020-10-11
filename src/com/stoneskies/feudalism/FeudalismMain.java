package com.stoneskies.feudalism;

import com.stoneskies.feudalism.Commands.Feudalism;
import com.stoneskies.feudalism.Ruin.RuinAPI;
import com.stoneskies.feudalism.Ruin.TownRuin;
import com.stoneskies.feudalism.Util.ChatInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class FeudalismMain extends JavaPlugin {
    public static FeudalismMain plugin;
    public static void setPlugin(FeudalismMain plugin) {
        FeudalismMain.plugin = plugin;
    } // plugin var setter
    public File configFile = new File(this.getDataFolder(), "config.yml"); // config.yml file var

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatInfo.msg("&7Plugin loaded successfully!"));
        // set the plugin var
        setPlugin(this);
        registerStuff();
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatInfo.msg("&7Plugin unloaded successfully"));
    }
    public void registerStuff() {
        //commands
        this.getCommand("fd").setExecutor(new Feudalism());
        //schedules
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, RuinAPI.ExpiredRuinedTownPurge, 0L, 72000L); // schedule for purging expired ruined towns
        //events
        getServer().getPluginManager().registerEvents(new TownRuin(), this); // register ruined town events
        //config and settings
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
