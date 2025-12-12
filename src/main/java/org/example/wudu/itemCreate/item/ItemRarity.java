package org.example.wudu.itemCreate.item;

import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * ● 纸质级：
 * ● 木质级：
 * ● 石质级：
 * ● 铁质级：
 * ● 钻石级：
 * ● 黑曜石级:
 * ● 下界合金级：
 * ● 世界基岩级：
 * */
@Getter
public enum ItemRarity implements ConfigurationSerializable {
    Paper(Material.PAPER,"Paper");

    private Material rarityMaterial;
    private String name;

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
