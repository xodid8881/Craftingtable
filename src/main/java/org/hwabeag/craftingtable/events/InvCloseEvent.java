package org.hwabeag.craftingtable.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.hwabeag.craftingtable.config.ConfigManager;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class InvCloseEvent implements Listener {

    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        String name = p.getName();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("특수조합대 세팅")) {
            if (CraftingConfig.getString("특수조합세팅." + name) != null) {
                String craftingname = CraftingConfig.getString("특수조합세팅." + name);
                if (CraftingConfig.getString("특수조합법." + craftingname) != null) {
                    int N = 0;
                    while (N <= 8){
                        int[] Number = {12, 13, 14, 21, 22, 23, 30, 31, 32};
                        int Slot = Number[N];
                        if (e.getInventory().getItem(Slot) != null) {
                            if (CraftingConfig.getItemStack("특수조합법." + craftingname + ".재료." + Slot) != null) {
                                @Nullable ItemStack item = e.getInventory().getItem(Slot);
                                CraftingConfig.set("특수조합법." + craftingname + ".재료." + Slot, item);
                            } else {
                                @Nullable ItemStack item = e.getInventory().getItem(Slot);
                                CraftingConfig.addDefault("특수조합법." + craftingname + ".재료." + Slot, item);
                            }
                        } else {
                            CraftingConfig.set("특수조합법." + craftingname + ".재료." + Slot, null);
                        }
                        N += 1;
                    }
                    CraftingConfig.set("특수조합세팅." + name, null);
                    ConfigManager.saveConfigs();
                }
            }
        }
    }
}
