package org.hwabeag.craftingtable;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.hwabeag.craftingtable.commands.MainCommand;
import org.hwabeag.craftingtable.commands.OPCommand;
import org.hwabeag.craftingtable.config.ConfigManager;
import org.hwabeag.craftingtable.events.InvClickEvent;
import org.hwabeag.craftingtable.events.InvCloseEvent;
import org.hwabeag.craftingtable.events.JoinEvent;

import java.util.Objects;

public final class Craftingtable extends JavaPlugin {

    private static ConfigManager configManager;

    private FileConfiguration config;

    public static Craftingtable getPlugin() {
        return JavaPlugin.getPlugin(Craftingtable.class);
    }

    public static void getConfigManager() {
        if (configManager == null)
            configManager = new ConfigManager();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new InvClickEvent(), this);
        getServer().getPluginManager().registerEvents(new InvCloseEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getServer().getPluginCommand("특수조합대")).setExecutor(new MainCommand());
        Objects.requireNonNull(getServer().getPluginCommand("특수조합대관리")).setExecutor(new OPCommand());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("[Craftingtable] Enable");
        getConfigManager();
        registerCommands();
        registerEvents();
        CraftingListLoading();
    }

    private void CraftingListLoading() {
        // 6 7 8
        // 15 16 17
        // 24 25 26
        // 33 34 35
        if (ConfigManager.getConfig("craftingtable").getString("특수조합리스트") != null) {
            ConfigManager.getConfig("craftingtable").set("특수조합리스트", null);
            ConfigManager.saveConfigs();
        }
        int N = 0;
        int P = 1;
        if (ConfigManager.getConfig("craftingtable").getConfigurationSection("특수조합법") != null) {
            for (String key : Objects.requireNonNull(ConfigManager.getConfig("craftingtable").getConfigurationSection("특수조합법")).getKeys(false)) {
                int[] Number = {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35};
                int Slot = Number[N];
                N += 1;
                if (N >= 11){
                    N = 0;
                    P += 1;
                }
                if (ConfigManager.getConfig("craftingtable").getString("특수조합리스트") == null) {
                    ConfigManager.getConfig("craftingtable").addDefault("특수조합리스트." + P + "." + Slot, key);
                    ConfigManager.saveConfigs();
                } else {
                    ConfigManager.getConfig("craftingtable").set("특수조합리스트." + P + "." + Slot, key);
                    ConfigManager.saveConfigs();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("[Craftingtable] Disable");
        ConfigManager.saveConfigs();
    }
}
