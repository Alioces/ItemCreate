package org.example.wudu.itemCreate.config;

import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.util.List;

public interface LoadFileConfig {

    List<CustomItem> readFileFolder(File file);

    CustomItem readFile(File file);

    void saveFile(CustomItem customItem);
}
