package org.example.wudu.itemCreate.config;

import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.util.List;

public class YmlLoadFileConfig implements LoadFileConfig {
    @Override
    public List<CustomItem> readFileFolder(File file) {
        return null;
    }

    @Override
    public CustomItem readFile(File file) {
        return null;
    }

    @Override
    public void saveFile(CustomItem customItem) {

    }
}
