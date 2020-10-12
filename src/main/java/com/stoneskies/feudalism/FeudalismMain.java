package com.stoneskies.feudalism;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.stoneskies.feudalism.Commands.Feudalism;
import com.stoneskies.feudalism.Interfaces.RuinAPI;
import com.stoneskies.feudalism.Listeners.RuinListener;
import com.stoneskies.feudalism.Util.ChatInfo;
import com.stoneskies.feudalism.Util.ConfigLoad;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileFormat;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
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
        try {
            registerStuff();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatInfo.msg("&7Plugin unloaded successfully"));
    }
    public void registerCompletions(Commodore commodore, PluginCommand command) throws IOException {
        LiteralCommandNode<?> fdCommand = CommodoreFileFormat.parse(plugin.getResource("fd.commodore"));
        commodore.register(command, fdCommand);
    }
    public void registerStuff() throws IOException {
        //commands
        PluginCommand command = getCommand("fd");
        command.setExecutor(new Feudalism());
        //schedules
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, RuinAPI.ExpiredRuinedTownPurge, 0L, 72000L); // schedule for purging expired ruined towns

        //events
        getServer().getPluginManager().registerEvents(new RuinListener(), this); // register ruined town events
        RuinAPI.clearresidentNPCs(); // clear all non-mayor npcs
        //config and settings
        ConfigLoad.LoadConfig();
        //other
        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {

            // get a commodore instance
            Commodore commodore = CommodoreProvider.getCommodore(this);

            // register your completions.
            registerCompletions(commodore, command);
        }
    }
}
