package org.hwabeag.craftingtable.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.hwabeag.craftingtable.config.ConfigManager;
import org.hwabeag.craftingtable.inventorys.CraftingGUI;
import org.jetbrains.annotations.NotNull;


public class MainCommand implements CommandExecutor {

    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            String name = p.getName();
            CraftingConfig.set("플레이어페이지." + name, 1);
            CraftingConfig.set("시도조합." + name, "없음");
            ConfigManager.saveConfigs();
            CraftingGUI inv = new CraftingGUI(p);
            inv.open(p);
            return true;
        }
        return false;
    }
}