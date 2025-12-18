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
    /*
    对于枚举类型实现 ConfigurationSerializable，实际上不需要显式实现 deserialize() 方法，这是因为：
    枚举类型的特殊性：
    枚举在 Java 中是特殊的类，它们有隐式的静态方法 valueOf(String)
    Bukkit 的序列化机制：
    对于实现了 ConfigurationSerializable 的枚举，Bukkit 会自动使用枚举的 valueOf() 方法进行反序列化
    它会尝试将配置中的 name 值转换为对应的枚举常量
    serialize() 方法正确地将枚举的 name 存入 Map
    Bukkit 系统在反序列化时会自动调用 ItemRarity.valueOf(name) 来获取对应的枚举值
    public static ItemRarity deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        return valueOf(name);
    }
    */
    public static ItemRarity deserialize(Map<String, Object> args) {
        System.out.println("反序列化ItemRarity");
        String name = (String) args.get("name");
        return valueOf(name);
    }
}

