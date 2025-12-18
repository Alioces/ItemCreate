package org.example.wudu.itemCreate.item;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;


/**
 * 物品库类，用于管理和展示自定义物品，添加物品
 */
public class ItemLibrary {

    /**
     * 自定义物品管理器，使用@Getter注解自动生成getter方法
     */
    @Getter
    private final CustomItemManager customItemManager;

    /**
     * 自定义物品列表，存储所有物品
     */
    private List<CustomItem> customItems;

    /**
     * 构造函数，初始化物品库
     * @param customItemManager 自定义物品管理器，用于获取物品数据
     */
    public ItemLibrary(CustomItemManager customItemManager) {
        this.customItemManager = customItemManager;

        // 从物品管理器获取所有物品，按ID排序后转换为列表
        customItems = customItemManager.getCustomItemMap().values().stream()
                .sorted(Comparator.comparing(CustomItem::getId))
                .toList();
    }

    /**
     * 查看所有物品
     * @return 返回所有物品的列表
     */
    public List<CustomItem> seeAllItems(){
        return customItems;
    }

    /**
     * 查看指定ID范围内的物品
     * @param to 起始ID
     * @param from 结束ID
     * @return 返回在指定ID范围内的物品列表
     */
    public List<CustomItem> seeAllItems(int to,int from){
        return customItems.stream()
                .filter(customItem -> to <= customItem.getId() && customItem.getId() <= from)
                .toList();
    }
}
