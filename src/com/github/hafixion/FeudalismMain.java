package com.github.hafixion;

import org.bukkit.plugin.java.JavaPlugin;

public class FeudalismMain extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Loaded Successfully.");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§6[Feudalism]§7 Plugin Unloaded Successfully.");
    }
}
