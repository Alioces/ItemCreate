package org.example.wudu.itemCreate.item;

import lombok.*;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
/**
 * CustomItem 类实现了 ConfigurationSerializable 和 Cloneable 接口
 * 用于表示自定义物品，包含类型、名称、ID、稀有度以及物品堆栈等属性
 * 提供序列化和克隆功能
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomItem implements ConfigurationSerializable,Cloneable {
    // 物品类型
    private String type;
    // 物品名称
    private String name;
    // 物品ID，默认值为0
    private int id = 0;
    // 物品稀有度
    private ItemRarity itemRarity;

    // 物品堆栈对象
    private ItemStack itemStack;


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
                    item.itemRarity = ItemRarity.Paper;
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

/**
 * 重写clone方法，实现对象的深拷贝
 * @return 返回一个当前对象的副本
 * @throws CloneNotSupportedException 如果对象的类不支持Cloneable接口，则抛出此异常
 */
    @Override
    protected Object clone() throws CloneNotSupportedException {
    // 调用父类的clone方法创建对象的浅拷贝
        Object clone = super.clone();
    // 将克隆对象强制转换为CustomItem类型
        CustomItem customItem = (CustomItem) clone;

    // 为克隆对象设置新的ItemStack，实现深拷贝
        customItem.setItemStack(this.getItemStack());
    // 复制ID属性
        customItem.setId(this.getId());
    // 复制物品稀有度属性
        customItem.setItemRarity(this.getItemRarity());
    // 复制类型属性
        customItem.setType(getType());
    // 复制名称属性
        customItem.setName(this.getName());

    // 返回完成属性复制的克隆对象
        return customItem;
    }
}
