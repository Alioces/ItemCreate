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
    Paper(Material.PAPER,"Paper");  // 定义一个名为Paper的稀有度，使用纸张材质和名称

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

    /**
     * Creates a Map representation of this class.
     * <p>
     * This class must provide a method to restore this class, as defined in
     * the {@link ConfigurationSerializable} interface javadocs.
     * <p>
     * nb: It is not intended for this method to be called directly, this will
     * be called by the {@link ConfigurationSerialization} class.
     *
     * @return Map containing the current state of this class
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String,Object> keyMap = new HashMap<>();
        keyMap.put("name",name);
        return keyMap;
    }
}
