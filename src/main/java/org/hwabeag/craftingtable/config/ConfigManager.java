package org.hwabeag.craftingtable.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.hwabeag.craftingtable.Craftingtable;

import java.util.HashMap;

public class ConfigManager {
    private static final Craftingtable plugin = Craftingtable.getPlugin();

    private static final HashMap<String, ConfigMaker> configSet = new HashMap<>();


    public ConfigManager() {
        String path = plugin.getDataFolder().getAbsolutePath();
        configSet.put("craftingtable", new ConfigMaker(path, "Craftingtable.yml"));
        loadSettings();
        saveConfigs();
    }

    public static void reloadConfigs() {
        for (String key : configSet.keySet()){
            plugin.getLogger().info(key);
            configSet.get(key).reloadConfig();
        }
    }

    public static void saveConfigs(){
        for (String key : configSet.keySet())
            configSet.get(key).saveConfig();
    }

    public static FileConfiguration getConfig(String fileName) {
        return configSet.get(fileName).getConfig();
    }

    public static void loadSettings(){
        FileConfiguration CraftingConfig = getConfig("craftingtable");
        CraftingConfig.options().copyDefaults(true);

        CraftingConfig.addDefault("craftingtable.prefix", "&a&l[Craftingtable]&7");
    }


}