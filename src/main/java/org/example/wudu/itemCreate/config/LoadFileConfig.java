package org.example.wudu.itemCreate.config;

import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.util.List;

/**
 * 文件配置加载接口
 * 该接口定义了读取和保存文件的相关方法
 */
public interface LoadFileConfig {
    //读取文件夹中的所有YAML文件
    List<CustomItem> readFileFolder(File file);

    /**
     * 读取单个文件的内容
     * @param file 要读取的文件
     * @return 包含文件内容的CustomItem对象
     */
    CustomItem readFile(File file);

    //将CustomItem对象保存到YAML文件中
    void saveFile(CustomItem customItem);
}
