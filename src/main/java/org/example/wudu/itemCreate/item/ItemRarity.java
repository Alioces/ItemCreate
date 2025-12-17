package org.example.wudu.itemCreate.item;

import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 物品稀有度枚举类，定义了不同等级的物品稀有度
 * ● 纸质级：
 * ● 木质级：
 * ● 石质级：
 * ● 铁质级：
 * ● 钻石级：
 * ● 黑曜石级:
 * ● 下界合金级：
 * ● 世界基岩级：
 * */
@Getter  // 使用Lombok的@Getter注解，自动为所有字段生成getter方法
public enum ItemRarity implements ConfigurationSerializable {  // 实现ConfigurationSerializable接口，使对象可被序列化为配置文件格式
    // 稀有度等级从低到高
    Paper(Material.PAPER, "Paper"),          // 纸质级
    Wood(Material.OAK_PLANKS, "Wood"),        // 木质级
    Stone(Material.STONE, "Stone"),          // 石质级
    Iron(Material.IRON_INGOT, "Iron"),        // 铁质级
    Diamond(Material.DIAMOND, "Diamond"),    // 钻石级
    Obsidian(Material.OBSIDIAN, "Obsidian"), // 黑曜石级
    Netherite(Material.NETHERITE_INGOT, "Nether"), // 下界合金级
    Bedrock(Material.BEDROCK, "Bedrock");     // 世界基岩级

    private Material rarityMaterial;  // 稀有度对应的材质
    private String name;  // 稀有度的名称

    /**
     * 构造函数，用于创建新的稀有度实例
     * @param rarityMaterial 用于表示稀有度的材质
     * @param name 稀有度的名称
     */
    ItemRarity(Material rarityMaterial, String name) {
        this.rarityMaterial = rarityMaterial;
        this.name = name;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String,Object> keyMap = new HashMap<>();
        keyMap.put("name",name);
        return keyMap;
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
