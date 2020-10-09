package com.github.hafixion;

import com.github.hafixion.Ruin.DebugIsRuinedCommand;
import com.github.hafixion.Ruin.TownRuin;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeudalismMain extends JavaPlugin {

    private static FeudalismMain plugin;

    public static FeudalismMain getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Loaded Successfully.");
        registerStuff();
        this.plugin = this;
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Unloaded Successfully.");
        this.plugin = null;
    }
    public void registerStuff() {
        //commands
        this.getCommand("townruin").setExecutor(new DebugIsRuinedCommand());

        //events
        getServer().getPluginManager().registerEvents(new TownRuin(), this);
    }
}
