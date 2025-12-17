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
    public @NotNull  Map<String, Object> serialize() {
        HashMap<String,Object> keyMap = new HashMap<>();
        keyMap.put("type",type);
        keyMap.put("name",name);
        keyMap.put("id",id);
        keyMap.put("itemRarity",itemRarity);
        keyMap.put("itemStack",itemStack);
        return keyMap;
    }
//    //反
//    public static CustomItem deserialize(@NotNull Map<String, Object> args) {
//        CustomItem item = new CustomItem();
//        if (args.containsKey("type")) item.type = (String) args.get("type");
//        if (args.containsKey("name")) item.name = (String) args.get("name");
//        if (args.containsKey("id"))   item.id = (int) args.get("id");
//        *//*==: 是一个特殊的键，用于告诉配置系统这个数据应该被反序列化成哪种类型的对象
//        org.example.wudu.itemCreate.item.ItemRarity 是完整的类名，指定了这个对象应该被反序列化为 ItemRarity 类的实例
//        这种格式通常出现在 Bukkit 插件中，用于保存和加载复杂的自定义对象*//*
//        if (args.containsKey("itemRarity")) {
//            Object rarityObj = args.get("itemRarity");
//            // 处理 ConfigurationSerializable 对象
//            if (rarityObj instanceof Map) {
//                Map<String, Object> rarityMap = (Map<String, Object>) rarityObj;
//                // 检查是否是 ConfigurationSerializable 对象
//                System.out.println(rarityMap);
//                if (rarityMap.containsKey("==")) {
//                    // 获取实际的类名
//                    String className = (String) rarityMap.get("==");
//                    if (className.equals("org.example.wudu.itemCreate.item.ItemRarity")) {
//                        // 从 name 字段获取值
//                        String rarityName = (String) rarityMap.get("name");
//                        try {
//                            //valueOf() 接收一个字符串参数,返回该字符串对应的枚举常量
//                            item.itemRarity = ItemRarity.valueOf(rarityName);
//                        } catch (IllegalArgumentException e) {
//                            item.itemRarity = ItemRarity.Paper;
//                            System.out.println("未知的稀有度: " + rarityName + ", 使用默认值 Paper");
//                        }
//                    }
//                } else {
//                    // 处理普通的 Map 结构
//                    String rarityName = (String) rarityMap.get("name");
//                    try {
//                        item.itemRarity = ItemRarity.valueOf(rarityName);
//                    } catch (IllegalArgumentException e) {
//                        item.itemRarity = ItemRarity.Paper;
//                        System.out.println("未知的稀有度: " + rarityName + ", 使用默认值 Paper");
//                    }
//                }
//            }
//            // 处理字符串类型
//            else if (rarityObj instanceof String) {
//                try {
//                    item.itemRarity = ItemRarity.valueOf((String) rarityObj);
//                } catch (IllegalArgumentException e) {
//                    item.itemRarity = ItemRarity.Paper;
//                    System.out.println("未知的稀有度: " + rarityObj + ", 使用默认值 Paper");
//                }
//            }
//            // 处理已经是 ItemRarity 类型的情况
//            else if (rarityObj instanceof ItemRarity) {
//                item.itemRarity = (ItemRarity) rarityObj;
//            }
//        }
//
//        if (args.containsKey("itemStack")) {
//            Object stackObj = args.get("itemStack");
//            if (stackObj instanceof Map) {
//                // 处理 itemStack 的 Map 结构
//                Map<String, Object> stackMap = (Map<String, Object>) stackObj;
//                // 创建 ItemStack
//                String material = (String) stackMap.get("id");
//                int count = (int) stackMap.get("count");
//                // 这里需要根据你的具体需求创建 ItemStack
//                // 示例：item.itemStack = new ItemStack(Material.valueOf(material.toUpperCase()), count);
//            } else if (stackObj instanceof ItemStack) {
//                item.itemStack = (ItemStack) stackObj;
//            }
//        }
//        return item;
//    }
public static CustomItem deserialize(@NotNull Map<String, Object> args) {
    CustomItem item = new CustomItem();
    // 基础属性设置
    item.type = getString(args, "type");
    item.name = getString(args, "name");
    item.id = getInt(args, "id");
    // 处理物品稀有度
    item.itemRarity = parseItemRarity(args.get("itemRarity"));
    // 处理物品堆叠
    item.itemStack = parseItemStack(args.get("itemStack"));
    return item;
}
    // 辅助方法：安全获取字符串
    private static String getString(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (String) map.get(key) : null;
    }
    // 辅助方法：安全获取整数
    private static int getInt(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (int) map.get(key) : 0;
    }
    // 辅助方法：解析物品稀有度
    private static ItemRarity parseItemRarity(Object rarityObj) {
        //默认Paper
        if (rarityObj == null) {
            return ItemRarity.Paper;
        }
        try {
            // 处理已经是 ItemRarity 类型的情况
            if (rarityObj instanceof ItemRarity) {
                return (ItemRarity) rarityObj;
            }
            // 处理字符串类型
            if (rarityObj instanceof String) {
                //valueOf() 接收一个字符串参数,返回该字符串对应的枚举常量
                return ItemRarity.valueOf((String) rarityObj);
            }
            // 处理Map类型
            if (rarityObj instanceof Map) {
                return parseRarityFromMap((Map<String, Object>) rarityObj);
            }
            return ItemRarity.Paper;
        } catch (IllegalArgumentException e) {
            System.out.println("未知的稀有度: " + rarityObj + ", 使用默认值 Paper");
            return ItemRarity.Paper;
        }
    }
    // 辅助方法：从Map解析物品稀有度
    private static ItemRarity parseRarityFromMap(Map<String, Object> rarityMap) {
        // 检查是否是 ConfigurationSerializable 对象
        if (rarityMap.containsKey("==")) {
            String className = (String) rarityMap.get("==");
            if (!"org.example.wudu.itemCreate.item.ItemRarity".equals(className)) {
                return ItemRarity.Paper;
            }
        }
        // 获取稀有度名称
        String rarityName = (String) rarityMap.get("name");
        if (rarityName == null) {
            return ItemRarity.Paper;
        }
        return ItemRarity.valueOf(rarityName);
    }
    // 辅助方法：解析物品堆叠
    private static ItemStack parseItemStack(Object stackObj) {
        if (stackObj == null) return null;
        // 处理已经是 ItemStack 类型的情况
        if (stackObj instanceof ItemStack) return (ItemStack) stackObj;
        // 处理Map类型
        if (stackObj instanceof Map) {
            Map<String, Object> stackMap = (Map<String, Object>) stackObj;
            String material = (String) stackMap.get("id");
            int count = getInt(stackMap, "count");
            try {
                return new ItemStack(Material.valueOf(material.toUpperCase()), count);
            } catch (IllegalArgumentException e) {
                System.out.println("未知的材料类型: " + material + ", 返回 null");
                return null;
            }
        }
        return null;
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
