package org.example.wudu.itemCreate.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.LogManager.getLogger;

public class YmlLoadFileConfig implements LoadFileConfig {

    private final YamlConfiguration yamlConfiguration;


    public YmlLoadFileConfig(YamlConfiguration yamlConfiguration){
        this.yamlConfiguration = yamlConfiguration;
    }

    //检查ItemCreate下config.yml中configureFolders对应的文件夹是否存在，如果存在读取对应文件夹下所有yml文件到
    @Override
    public List<CustomItem> readFileFolder(File folder) {
        List<CustomItem> items = new ArrayList<>();

        if (!folder.exists()) {
            System.out.println("文件或文件夹不存在");
            return items;
        }

        // 处理文件夹
        if (folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".yml"));
            if (files != null) {
                for (File file : files) {
                    processFile(file, items);
                }
            }
        }
        System.out.println("files反序列化成功");
        System.out.println(items);
        return items;
    }
    @Override
    public CustomItem readFile(File file) {
        CustomItem item = new CustomItem();
        // 处理单个文件
        if (file.isFile()) {
            if (file.getName().toLowerCase().endsWith(".yml")) {
                //加载YAML文件并将其转换为配置对象
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                //读取item节  当不存在节时，不需要获取，直接使用config对象来获取这些值
               // ConfigurationSection itemSection = config.getConfigurationSection("item");
                // 获取所有键值对,false参数表示不递归获取深层嵌套的值
                Map<String, Object> itemValues = config.getValues(false);
                // 将Map反序列化为CustomItem对象
                CustomItem customItem = CustomItem.deserialize(itemValues);
                // 将反序列化成功CustomItem对象返回
                System.out.println("file反序列化成功");
                System.out.println(customItem);
                return customItem;

            }
            return item;
        }
        return null;
    }
    private void processFile(File file, List<CustomItem> items) {
        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            // 获取所有键值对,false参数表示不递归获取深层嵌套的值
            Map<String, Object> itemValues = config.getValues(true);
            System.out.println(file + "文件中的map:" + itemValues);
            // 将Map反序列化为CustomItem对象
            CustomItem item = CustomItem.deserialize(itemValues);
            // 确保对象创建成功
            if (item != null) {
                // 将反序列化成功CustomItem对象添加到列表中
                items.add(item);
            }
        } catch (Exception e) {
            System.err.println("Error loading file " + file.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }




    //将CustomItem对象保存到YAML文件中
    @Override
    public void saveFile(CustomItem customItem,File targetFile) {
        try {
            // 创建YAML配置
            YamlConfiguration config = new YamlConfiguration();
            // 设置物品数据
            ConfigurationSection itemSection = config.createSection("item");
            itemSection.set("type", customItem.getType());
            itemSection.set("name", customItem.getName());
            itemSection.set("id", customItem.getId());
            itemSection.set("itemRarity", customItem.getItemRarity().name());
            // 保存ItemStack
            if (customItem.getItemStack() != null) {
                itemSection.set("itemStack", customItem.getItemStack().serialize());
            }
            // 保存文件
            config.save(targetFile);
            getLogger().info("成功保存物品: " + customItem.getName() + " 到文件: " + targetFile.getName());

        } catch (IOException e) {
            getLogger().info("保存物品文件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
