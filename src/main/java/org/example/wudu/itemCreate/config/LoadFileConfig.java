package org.example.wudu.itemCreate.config;

import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.util.List;

/**
 * 文件配置加载接口
 * 该接口定义了读取和保存文件的相关方法
 */
public interface LoadFileConfig {

    /**
     * 读取指定文件夹中的所有文件
     * @param file 要读取的文件夹
     * @return 包含文件夹中所有文件的CustomItem列表
     */
    List<CustomItem> readFileFolder(File file);

    /**
     * 读取单个文件的内容
     * @param file 要读取的文件
     * @return 包含文件内容的CustomItem对象
     */
    CustomItem readFile(File file);

    /**
     * 保存CustomItem对象到文件
     * @param customItem 要保存的CustomItem对象
     */
    void saveFile(CustomItem customItem);
}
