package com.stoneskies.feudalism;

import com.stoneskies.feudalism.Ruin.DebugRuinCommands;
import com.stoneskies.feudalism.Ruin.RuinAPI;
import com.stoneskies.feudalism.Ruin.TownRuin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeudalismMain extends JavaPlugin {

    private static FeudalismMain plugin;

    public static void setPlugin(FeudalismMain plugin) {
        FeudalismMain.plugin = plugin;
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "§6[Feudalism]§7 Plugin loaded successfully!"));
        registerStuff();
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "§6[Feudalism]§7 Plugin unloaded successfully"));
    }
    public void registerStuff() {
        //commands
        this.getCommand("townruin").setExecutor(new DebugRuinCommands());
        //schedules
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, RuinAPI.ExpiredRuinedTownPurge, 0L, 72000L);
        //events
        getServer().getPluginManager().registerEvents(new TownRuin(), this);
    }
}
