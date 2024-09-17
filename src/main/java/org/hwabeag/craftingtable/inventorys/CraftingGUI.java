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

import java.util.Arrays;

public class CraftingGUI implements Listener {
    private final Inventory inv;

    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");

    private void initItemSetting() {

        int N = 0;
        while (N <= 15){
            int[] Number = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
            int Slot = Number[N];
            ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1);
            ItemMeta itemMeta = item.getItemMeta();   //검의 메타데이터
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a라인"));
            item.setItemMeta(itemMeta);
            inv.setItem(Slot,item);
            N += 1;
        }

        N = 0;
        while (N <= 13){
            int[] Number = {5, 14, 23, 32, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50};
            int Slot = Number[N];

            ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a라인"));
            item.setItemMeta(itemMeta);
            inv.setItem(Slot,item);
            N += 1;
        }

        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a이전 페이지"));
        itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&a- &f클릭 시 이전 페이지로 이동합니다.")));
        item.setItemMeta(itemMeta);
        inv.setItem(51,item);


        item = new ItemStack(Material.ANVIL, 1);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a조합하기"));
        itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&a- &f클릭 시 조합을 진행합니다.")));
        item.setItemMeta(itemMeta);   //메타데이터 저장
        inv.setItem(52,item);


        item = new ItemStack(Material.PAPER, 1);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a다음 페이지"));
        itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&a- &f클릭 시 다음 페이지로 이동합니다.")));
        item.setItemMeta(itemMeta);
        inv.setItem(53,item);
    }

    private void CraftingListSetting(Player player) {
        // 6 7 8
        // 15 16 17
        // 24 25 26
        // 33 34 35
        String name = player.getName();
        int page = CraftingConfig.getInt("플레이어페이지." + name);
        int i = 0;
        while (11 >= i){
            int[] slot = {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35};
            if (CraftingConfig.getString("특수조합리스트." + page + "." + slot[i]) != null){
                String ItemName = CraftingConfig.getString("특수조합리스트." + page + "." + slot[i]);
                ItemStack item = CraftingConfig.getItemStack("특수조합법." + ItemName + ".보상");
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ItemName);
                item.setItemMeta(itemMeta);
                inv.setItem(slot[i],item);
            }
            i += 1;
        }
    }

    public CraftingGUI(Player p) {
        this.inv = Bukkit.createInventory(null,54,"특수 조합대");
        initItemSetting();
        CraftingListSetting(p);
    }

    public void open(Player player){
        player.openInventory(inv);
    }

}
