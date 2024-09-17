package org.hwabeag.craftingtable.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hwabeag.craftingtable.config.ConfigManager;

import java.util.Objects;

public class JoinEvent implements Listener {

    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CraftingConfig.getString("craftingtable.prefix")));

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        if (CraftingConfig.getString("플레이어페이지." + name) == null) {
            CraftingConfig.addDefault("플레이어페이지." + name, 0);
            CraftingConfig.addDefault("시도조합." + name, "없음");
            ConfigManager.saveConfigs();
        }
    }
}
