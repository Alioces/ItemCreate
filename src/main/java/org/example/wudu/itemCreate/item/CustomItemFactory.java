package org.example.wudu.itemCreate.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * 自定义物品工厂类，用于创建和管理自定义物品
 */
public class CustomItemFactory {

    /**
     * 自定义物品管理器，用于管理所有自定义物品
     */
    private final CustomItemManager customItemManager;
    /**
     * 构造函数，初始化自定义物品工厂
     * @param customItemManager 自定义物品管理器
     */
    public CustomItemFactory(CustomItemManager customItemManager){
        this.customItemManager = customItemManager;
    }

    /**
     * 创建物品堆栈
     * @param customItem 自定义物品对象
     * @return 创建后的物品堆栈
     */
    public ItemStack createItemStack(CustomItem customItem){
        // 从管理器中获取自定义物品
        CustomItem item = customItemManager.getCustomItemMap().get(customItem.getId());
        // 克隆物品堆栈
        ItemStack itemStack = item.getItemStack().clone();
        // 获取物品的元数据
        ItemMeta meta = itemStack.getItemMeta();

        // 设置物品显示名称
        meta.setDisplayName(customItem.getName());
        // 获取持久化数据容器
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        // 设置持久化数据
        pdc.set(customItemManager.getPlugin().getPluginNamespaced(), PersistentDataType.STRING, customItem.getName());
        // 应用修改后的元数据
        itemStack.setItemMeta(meta);

        // 更新物品堆栈
        item.setItemStack(itemStack);
        return itemStack;
    }

    /**
     * 从物品堆栈创建自定义物品
     * @param itemStack 物品堆栈
     * @return 自定义物品对象
     * @throws CloneNotSupportedException 如果克隆操作不被支持
     */
    public CustomItem createCustomItem(ItemStack itemStack) throws CloneNotSupportedException {
        // 获取物品的元数据
        ItemMeta meta = itemStack.getItemMeta();
        // 获取持久化数据容器
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        // 从持久化数据中获取物品名称
        String name = pdc.get(customItemManager.getPlugin().getPluginNamespaced(),PersistentDataType.STRING);
        // 如果名称不存在，返回null
        if (name == null){
            return null;
        }

        // 从管理器中获取自定义物品并克隆
        CustomItem item = (CustomItem) customItemManager.getCustomItemMap()
                .get(customItemManager.getItemNameToId().get(name))
                .clone();
        // 设置物品堆栈
        item.setItemStack(itemStack);

        return  item;
    }
}
