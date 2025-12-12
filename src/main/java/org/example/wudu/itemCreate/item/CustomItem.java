package org.example.wudu.itemCreate.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class CustomItem implements ConfigurationSerializable {
    private String type;
    private String name;
    private int id;
    private ItemRarity itemRarity;
    private ItemStack itemStack;
    public CustomItem() {
        this.itemStack = new ItemStack(Material.AIR); // 默认值
    }
    //序列化
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
    //反
    public static CustomItem deserialize(@NotNull Map<String, Object> args) {
        CustomItem item = new CustomItem();

        if (args.containsKey("type")) {
            item.type = (String) args.get("type");
        }
        if (args.containsKey("name")) {
            item.name = (String) args.get("name");
        }
        if (args.containsKey("id")) {
            item.id = (int) args.get("id");
        }
        if (args.containsKey("itemRarity")) {
            Object rarityObj = args.get("itemRarity");
            if (rarityObj instanceof String) {
                try {
                    // 将字符串转换为大写后转换为枚举
                    item.itemRarity = ItemRarity.valueOf(((String) rarityObj).toUpperCase());
                } catch (IllegalArgumentException e) {
                    // 如果转换失败，使用默认值
                    item.itemRarity = ItemRarity.COMMON;
                    System.out.println("未知的稀有度: " + rarityObj + ", 使用默认值 COMMON");
                }
            } else if (rarityObj instanceof ItemRarity) {
                item.itemRarity = (ItemRarity) rarityObj;
            }
        }
        if (args.containsKey("itemStack")) {
            item.itemStack = (ItemStack) args.get("itemStack");
        }

        return item;
    }

}
