package org.example.wudu.itemCreate.config;

import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.util.List;

public interface LoadFileConfig {
    //读取文件夹中的所有YAML文件
    List<CustomItem> readFileFolder(File file);

    CustomItem readFile(File file);

    //将CustomItem对象保存到YAML文件中
    void saveFile(CustomItem customItem);
}
