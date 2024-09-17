package org.hwabeag.craftingtable.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hwabeag.craftingtable.config.ConfigManager;
import org.hwabeag.craftingtable.inventorys.CraftingSettingGUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OPCommand implements CommandExecutor {

    FileConfiguration CraftingConfig = ConfigManager.getConfig("craftingtable");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CraftingConfig.getString("craftingtable.prefix")));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.isOp()) {
                if (args.length == 0) {
                    p.sendMessage(Prefix + " /특수조합대관리 생성 [제목] - 특수조합 방법을 제작합니다.");
                    p.sendMessage(Prefix + " /특수조합대관리 보상변경 [제목] - 특수조합 방법을 제작합니다.");
                    p.sendMessage(Prefix + " /특수조합대관리 세팅 [제목] - 특수조합 방법을 세팅합니다.");
                    p.sendMessage(Prefix + " /특수조합대관리 삭제 [제목] - 특수조합 방법을 삭제합니다.");
                    p.sendMessage(Prefix + " /특수조합대관리 리로드 - 특수조합 방법을 리로드 합니다.");
                    return true;
                }
                if (args[0].equals("생성")) {
                    if (args.length == 1) {
                        p.sendMessage(Prefix + " /특수조합대관리 생성 [제목] - 특수조합 방법을 제작합니다.");
                        return true;
                    }
                    ItemStack itemStack = p.getItemInHand().clone();
                    itemStack.setAmount(1);

                    if (itemStack.getType().equals(Material.AIR)) {
                        p.sendMessage(Prefix + " 손에 아이템을 들고 작업해주세요.");
                        return true;
                    }

                    if (CraftingConfig.getString("특수조합법." + args[1]) == null) {
                        @NotNull ItemStack item = p.getItemInHand().clone();
                        CraftingConfig.addDefault("특수조합법." + args[1] + ".보상", item);
                        p.sendMessage(Prefix + " " + args[1] + " 이름으로 특수조합법을 제작했습니다.");
                        ConfigManager.saveConfigs();
                    } else {
                        p.sendMessage(Prefix + " " + args[1] + " 이름으로 특수조합법이 이미 존재합니다.");
                    }
                    return true;
                }
                if (args[0].equals("보상변경")) {
                    if (args.length == 1) {
                        p.sendMessage(Prefix + " /특수조합대관리 보상변경 [제목] - 특수조합 방법을 제작합니다.");
                        return true;
                    }
                    ItemStack itemStack = p.getItemInHand().clone();
                    itemStack.setAmount(1);

                    if (itemStack.getType().equals(Material.AIR)) {
                        p.sendMessage(Prefix + " 손에 아이템을 들고 작업해주세요.");
                        return true;
                    }

                    if (CraftingConfig.getString("특수조합법." + args[1]) != null) {
                        @Nullable ItemStack item = p.getItemInHand().clone();
                        CraftingConfig.set("특수조합법." + args[1] + ".보상", item);
                        p.sendMessage(Prefix + " " + args[1] + " 이름의 특수조합법 보상을 수정했습니다.");
                        ConfigManager.saveConfigs();
                    } else {
                        p.sendMessage(Prefix + " " + args[1] + " 이름으로 특수조합법이 존재하지 않습니다.");
                    }
                    return true;
                }
                if (args[0].equals("세팅")) {
                    if (args.length == 1) {
                        p.sendMessage(Prefix + " /특수조합대관리 세팅 [제목] - 특수조합 방법을 세팅합니다.");
                        return true;
                    }
                    if (CraftingConfig.getString("특수조합법." + args[1]) != null) {
                        String name = p.getName();
                        CraftingConfig.addDefault("특수조합세팅." + name, args[1]);
                        CraftingSettingGUI inv = new CraftingSettingGUI(p);
                        inv.open(p);
                    } else {
                        p.sendMessage(Prefix + " " + args[1] + " 이름으로 특수조합법이 존재하지 않습니다.");
                    }
                    return true;
                }
                if (args[0].equals("삭제")) {
                    if (args.length == 1) {
                        p.sendMessage(Prefix + " /특수조합대관리 삭제 [제목] - 특수조합 방법을 제작합니다.");
                        return true;
                    }
                    if (CraftingConfig.getString("특수조합법." + args[1]) != null) {
                        CraftingConfig.set("특수조합법." + args[1], null);
                        ConfigManager.saveConfigs();
                        p.sendMessage(Prefix + " " + args[1] + " 이름으로 특수조합법을 삭제했습니다.");
                    } else {
                        p.sendMessage(Prefix + " " + args[1] + " 이름으로 특수조합법이 존재하지 않습니다.");
                    }
                    return true;
                }
                if (args[0].equals("리로드")) {
                    CraftingListLoading();
                    p.sendMessage(Prefix + " 특수조합대 정보를 리로드 했습니다.");
                    return true;
                }
                p.sendMessage(Prefix + " /특수조합대관리 생성 [제목] - 특수조합 방법을 제작합니다.");
                p.sendMessage(Prefix + " /특수조합대관리 보상변경 [제목] - 특수조합 방법을 제작합니다.");
                p.sendMessage(Prefix + " /특수조합대관리 세팅 [제목] - 특수조합 방법을 세팅합니다.");
                p.sendMessage(Prefix + " /특수조합대관리 삭제 [제목] - 특수조합 방법을 삭제합니다.");
                p.sendMessage(Prefix + " /특수조합대관리 리로드 - 특수조합 방법을 리로드 합니다.");
            } else {
                p.sendMessage(Prefix + " 당신은 권한이 없습니다.");
            }
            return true;
        }
        return false;
    }

    private void CraftingListLoading() {
        // 6 7 8
        // 15 16 17
        // 24 25 26
        // 33 34 35
        if (CraftingConfig.getString("특수조합리스트") != null) {
            CraftingConfig.set("특수조합리스트", null);
            ConfigManager.saveConfigs();
        }
        int N = 0;
        int P = 1;
        if (CraftingConfig.getConfigurationSection("특수조합법") != null) {
            for (String key : Objects.requireNonNull(CraftingConfig.getConfigurationSection("특수조합법")).getKeys(false)) {
                int[] Number = {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35};
                int Slot = Number[N];
                N += 1;
                if (N >= 11){
                    N = 0;
                    P += 1;
                }
                if (CraftingConfig.getString("특수조합리스트") == null) {
                    CraftingConfig.addDefault("특수조합리스트." + P + "." + Slot, key);
                    ConfigManager.saveConfigs();
                } else {
                    CraftingConfig.set("특수조합리스트." + P + "." + Slot, key);
                    ConfigManager.saveConfigs();
                }
            }
        }
    }
}