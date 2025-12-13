package org.example.wudu.itemCreate.item;

import lombok.Data;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public enum ItemRarity {
    COMMON("普通", ChatColor.WHITE),
    UNCOMMON("不普通", ChatColor.GREEN),
    RARE("稀有", ChatColor.BLUE),
    EPIC("史诗", ChatColor.DARK_PURPLE),
    LEGENDARY("传说", ChatColor.GOLD),
    MYTHIC("神话", ChatColor.LIGHT_PURPLE);

    private final String displayName;
    private final ChatColor color;

    ItemRarity(String displayName, ChatColor color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getColoredName() {
        return color + displayName;
    }


    // 添加根据ChatColor获取ItemRarity的方法
    public static ItemRarity fromChatColor(ChatColor color) {
        if (color == null) {
            return COMMON;
        }

        for (ItemRarity rarity : values()) {
            if (rarity.getColor() == color) {
                return rarity;
            }
        }

        return COMMON;
    }

    // 添加根据字符串获取ItemRarity的方法
    public static ItemRarity fromString(String name) {
        if (name == null) {
            return COMMON;
        }

        try {
            // 直接匹配枚举名
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 如果直接匹配失败，尝试匹配显示名
            for (ItemRarity rarity : values()) {
                if (rarity.getDisplayName().equalsIgnoreCase(name)) {
                    return rarity;
                }
            }
            return COMMON;
        }
    }
}

