package org.example.wudu.itemCreate.item;

import lombok.*;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomItem implements ConfigurationSerializable,Cloneable {
    private String type;
    private String name;
    private int id = 0;
    private ItemRarity itemRarity;

    private ItemStack itemStack;


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
        keyMap.put("type",type);
        keyMap.put("name",name);
        keyMap.put("id",id);
        keyMap.put("itemRarity",itemRarity);
        keyMap.put("itemStack",itemStack);
        return keyMap;
    }

    public static Object deserialize(@NotNull Map<String, Object> args) {
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        CustomItem customItem = (CustomItem) clone;

        customItem.setItemStack(this.getItemStack());
        customItem.setId(this.getId());
        customItem.setItemRarity(this.getItemRarity());
        customItem.setType(getType());
        customItem.setName(this.getName());

        return customItem;
    }
}
