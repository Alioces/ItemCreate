package org.example.wudu.itemCreate.item;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.ItemCreate;
import org.example.wudu.itemCreate.config.YmlLoadFileConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomItemManager类，用于管理自定义物品，提供对应的查找功能
 * 包含自定义物品的加载、存储和访问功能
 */
@Getter
public class CustomItemManager {

    /**
     * 存储自定义物品的ID到物品对象的映射表
     * 键为物品ID(Integer)，值为CustomItem对象
     */
    private final Map<Integer,CustomItem> customItemMap = new HashMap<>();

    /**
     * 存储自定义物品名称到ID的映射表
     * 键为物品名称(String)，值为物品ID(Integer)
     */
    private final Map<String,Integer> itemNameToId = new HashMap<>();

    /**
     * 插件主类的引用
     * 用于访问插件配置、日志记录等功能
     */
    private final ItemCreate plugin;

    /**
     * 自定义物品配置文件的存储目录
     * 用于存放所有自定义物品的配置文件
     */
    private final File itemConfigDir;

    /**
     * CustomItemManager的构造函数
     * @param plugin 插件主类的实例
     */
    public CustomItemManager(ItemCreate plugin) {
        this.plugin = plugin;
        // 保存默认配置文件
        plugin.saveDefaultConfig();
        // 获取插件配置
        FileConfiguration config = plugin.getConfig();
        // 记录物品配置目录信息
        plugin.getLogger().info("物品配置目录"+ config.getString("configureFolders"));
        // 初始化物品配置目录
        this.itemConfigDir = new File(plugin.getDataFolder()+"/"+config.getString("configureFolders"));
        // 假设用户第一次使用创建一个模板配置文件
        if (!itemConfigDir.isDirectory() || !itemConfigDir.exists()){
            itemConfigDir.mkdirs();
            initItemDir(itemConfigDir);
        }
    }

/**
 * 读取自定义物品配置文件的方法
 * 该方法会遍历指定目录下的所有配置文件，加载并处理自定义物品信息
 */
    public void readerCustomItemConfig(){
        // 创建YAML配置文件加载器实例
        YmlLoadFileConfig configuration = new YmlLoadFileConfig(new YamlConfiguration());
        // 读取指定目录下的配置文件，并对每个自定义物品进行处理
        configuration.readFileFolder(itemConfigDir).forEach(customItem ->{
            // 获取物品堆栈对象
            ItemStack itemStack = customItem.getItemStack();
            // 获取物品的元数据
            ItemMeta meta = itemStack.getItemMeta();
            // 设置物品的显示名称
            meta.setDisplayName(customItem.getName());
            // 获取持久化数据容器
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            // 向持久化数据容器中添加插件命名空间下的物品名称数据
            pdc.set(plugin.getPluginNamespaced(), PersistentDataType.STRING, customItem.getName());
            // 将更新后的元数据设置回物品堆栈
            itemStack.setItemMeta(meta);
            // 更新自定义物品的物品堆栈
            customItem.setItemStack(itemStack);

            // 将自定义物品添加到ID映射表中
            customItemMap.put(customItem.getId(), customItem);
            // 将自定义物品添加到名称到ID的映射表中
            itemNameToId.put(customItem.getName(), customItem.getId());
        });
    }

/**
 * 初始化物品配置目录
 * @param itemConfigDir 物品配置目录的File对象
 */
    private void initItemDir(File itemConfigDir) {
    // 创建模板配置文件
        File itemFile = new File(itemConfigDir, "模板.yml");
    // 加载YAML配置文件
        YamlConfiguration loaded = YamlConfiguration.loadConfiguration(itemFile);
    // 创建一个自定义物品实例
        CustomItem customItem = new CustomItem(
                "CustomItem",    // 物品名称
                "testItem",      // 物品标识符
                0,               // 物品数量
                ItemRarity.Paper, // 物品稀有度
                new ItemStack(Material.APPLE) // 物品材质
        );
    // 将自定义物品的序列化数据添加为默认值
        loaded.addDefaults(customItem.serialize());
    // 设置配置文件选项，自动复制默认值
        loaded.options().copyDefaults(true);
    // 尝试保存配置文件
        try {
            loaded.save(itemFile);
        } catch (IOException e) {
        // 如果保存失败，记录错误日志
            this.plugin.getLogger().info("无法创建一个模板配置文件");
        }
    }
}
