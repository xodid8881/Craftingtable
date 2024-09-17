package org.hwabeag.craftingtable.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hwabeag.craftingtable.config.ConfigManager;

public class CraftingSettingGUI implements Listener {
    private final Inventory inv;

    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");

    private void initItemSetting(Player p) {
        ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = item.getItemMeta();   //검의 메타데이터
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a라인"));      //검의 이름 설정
        item.setItemMeta(itemMeta);
        int i = 0;
        while (53 >= i) {
            inv.setItem(i,item);
            i += 1;
        }
        item = new ItemStack(Material.AIR, 1);
        int N = 0;
        while (N <= 8){
            int[] Number = {12, 13, 14, 21, 22, 23, 30, 31, 32};
            int Slot = Number[N];
            inv.setItem(Slot,item);
            N += 1;
        }
        String name = p.getName();
        String craftingname = CraftingConfig.getString("특수조합세팅." + name);
        N = 0;
        while (N <= 8){
            int[] Number = {12, 13, 14, 21, 22, 23, 30, 31, 32};
            int Slot = Number[N];
            if (CraftingConfig.getItemStack("특수조합법." + craftingname + ".재료." + Slot) != null){
                item = CraftingConfig.getItemStack("특수조합법." + craftingname + ".재료." + Slot);
                inv.setItem(Slot,item);
            }
            N += 1;
        }
    }

    public CraftingSettingGUI(Player p) {
        this.inv = Bukkit.createInventory(null,54,"특수조합대 세팅");
        initItemSetting(p);
    }

    public void open(Player player){
        player.openInventory(inv);
    }

}
