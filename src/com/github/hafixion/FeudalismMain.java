package com.github.hafixion;

import com.github.hafixion.events.Ruin.TownRuin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static com.github.hafixion.events.Ruin.RuinAPI.PurgeRuinedTowns;

public class FeudalismMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // register ruined towns
        getServer().getPluginManager().registerEvents(new TownRuin(), this);
        // purge current ruined towns in addition to new day event
        PurgeRuinedTowns();
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Loaded Successfully.");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Unloaded Successfully.");
    }
}
