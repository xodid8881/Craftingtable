package org.hwabeag.craftingtable.events;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.hwabeag.craftingtable.Craftingtable;
import org.hwabeag.craftingtable.config.ConfigManager;
import org.hwabeag.craftingtable.inventorys.CraftingGUI;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import static org.bukkit.Bukkit.getServer;

public class InvClickEvent implements Listener {
    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CraftingConfig.getString("craftingtable.prefix")));

    public void GiveRemoveItem(Player player, int[] slot) {
        String name = player.getName();
        String CraftingName = CraftingConfig.getString("시도조합." + name);
        int N = 0;
        if(slot.length != 0) {
            while (N <= slot.length-1) {
                int ItemSlot = slot[N];
                @Nullable ItemStack item = CraftingConfig.getItemStack("특수조합법." + CraftingName + ".재료." + ItemSlot);
                player.getInventory().addItem(item);
                N += 1;
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() != null) {
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("특수 조합대")) {
                String clickitem = e.getCurrentItem().getItemMeta().getDisplayName();
                Player player = (Player) e.getWhoClicked();
                String name = player.getName();
                e.setCancelled(true);
                if (CraftingConfig.getString("특수조합법." + clickitem) != null) {

                    int N = 0;
                    while (N <= 8){
                        int[] Number = {10, 11, 12, 19, 20, 21, 28, 29, 30};
                        int Slot = Number[N];
                        e.getInventory().setItem(Slot,null);
                        N += 1;
                    }
                    N = 0;
                    while (N <= 8){
                        int[] Number = {10, 11, 12, 19, 20, 21, 28, 29, 30};
                        int Slot = Number[N];
                        int[] ItemNumber = {12, 13, 14, 21, 22, 23, 30, 31, 32};
                        int ItemSlot = ItemNumber[N];
                        @Nullable ItemStack item = CraftingConfig.getItemStack("특수조합법." + clickitem + ".재료." + ItemSlot);
                        e.getInventory().setItem(Slot,item);
                        N += 1;
                    }

                    if(CraftingConfig.getString("시도조합." + name) != null){
                        CraftingConfig.set("시도조합." + name, clickitem);
                        ConfigManager.saveConfigs();
                    }
                }
                if (clickitem.equals(ChatColor.translateAlternateColorCodes('&', "&a이전 페이지"))) {
                    e.getInventory().clear();
                    player.closeInventory();
                    int page = CraftingConfig.getInt("플레이어페이지." + name);
                    int plus = page - 1;
                    CraftingConfig.set("플레이어페이지." + name, plus);
                    CraftingConfig.set("시도조합." + name, "없음");
                    ConfigManager.saveConfigs();
                    getServer().getScheduler().scheduleSyncDelayedTask(Craftingtable.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            CraftingGUI inv = new CraftingGUI(player);
                            inv.open(player);
                        }
                    }, 20);
                }
                if (clickitem.equals(ChatColor.translateAlternateColorCodes('&', "&a조합하기"))) {
                    e.getInventory().clear();
                    player.closeInventory();
                    if(CraftingConfig.getString("시도조합." + name) != null){
                        if (CraftingConfig.getString("시도조합." + name) != "없음") {
                            String CraftingName = CraftingConfig.getString("시도조합." + name);

                            int N = 0;
                            int[] ItemRemove = {};
                            while (N <= 8) {
                                int[] Number = {12, 13, 14, 21, 22, 23, 30, 31, 32};
                                int Slot = Number[N];
                                CustomStack stack = CustomStack.getInstance(CraftingConfig.getString("특수조합법." + CraftingName + ".재료." + Slot));
                                if (stack != null) {
                                    ItemStack itemStack = stack.getItemStack();
                                    if (!player.getInventory().contains(itemStack.getType())) {
                                        player.sendMessage(Prefix + " 커스텀 조합 아이템이 부족함에 조합이 불가능 합니다.");
                                        GiveRemoveItem(player, ItemRemove);
                                        return;
                                    } else {
                                        @Nullable ItemStack item = CraftingConfig.getItemStack("특수조합법." + CraftingName + ".재료." + Slot);
                                        player.getInventory().removeItem(item);
                                        int[] newArray = Arrays.copyOf(ItemRemove, ItemRemove.length + 1);
                                        newArray[newArray.length - 1] = Slot;
                                        ItemRemove = newArray;
                                    }
                                } else if (CraftingConfig.getItemStack("특수조합법." + CraftingName + ".재료." + Slot) != null) {
                                    if (!player.getInventory().contains(CraftingConfig.getItemStack("특수조합법." + CraftingName + ".재료." + Slot).getType())) {
                                        player.sendMessage(Prefix + " 조합 아이템이 부족함에 조합이 불가능 합니다.");
                                        GiveRemoveItem(player, ItemRemove);
                                        return;
                                    } else {
                                        @Nullable ItemStack item = CraftingConfig.getItemStack("특수조합법." + CraftingName + ".재료." + Slot);
                                        player.getInventory().removeItem(item);
                                        int[] newArray = Arrays.copyOf(ItemRemove, ItemRemove.length + 1);
                                        newArray[newArray.length - 1] = Slot;
                                        ItemRemove = newArray;
                                    }
                                }
                                N += 1;
                            }
                            @Nullable ItemStack item = CraftingConfig.getItemStack("특수조합법." + CraftingName + ".보상");
                            player.getInventory().addItem(item);
                            player.sendMessage(Prefix + " " + CraftingName + " 조합을 성공했습니다.");
                            return;
                        }
                    }
                }
                if (clickitem.equals(ChatColor.translateAlternateColorCodes('&', "&a다음 페이지"))) {
                    e.getInventory().clear();
                    player.closeInventory();
                    int page = CraftingConfig.getInt("플레이어페이지." + name);
                    int plus = page + 1;
                    CraftingConfig.set("플레이어페이지." + name, plus);
                    CraftingConfig.set("시도조합." + name, "없음");
                    ConfigManager.saveConfigs();
                    getServer().getScheduler().scheduleSyncDelayedTask(Craftingtable.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            CraftingGUI inv = new CraftingGUI(player);
                            inv.open(player);
                        }
                    }, 20);
                }
            }
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("특수 조합대 세팅")) {
                String clickitem = e.getCurrentItem().getItemMeta().getDisplayName();
                Player player = (Player) e.getWhoClicked();
                String name = player.getName();
                if (clickitem.equals(ChatColor.translateAlternateColorCodes('&', "&a라인"))){
                    e.setCancelled(true);
                }
            }
        }
    }
}
