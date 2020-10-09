package com.github.hafixion;

import com.github.hafixion.Ruin.DebugIsRuinedCommand;
import com.github.hafixion.Ruin.TownRuin;
import org.bukkit.plugin.java.JavaPlugin;

public class FeudalismMain extends JavaPlugin {

    @Override
    public void onEnable() {
        //register ruined commands
        this.getCommand("townruin").setExecutor(new DebugIsRuinedCommand());
        // register ruined towns
        getServer().getPluginManager().registerEvents(new TownRuin(), this);
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Loaded Successfully.");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Unloaded Successfully.");
    }
}
