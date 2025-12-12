package org.example.wudu.itemCreate.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.example.wudu.itemCreate.item.CustomItem;

import java.io.File;
import java.util.List;

public class YmlLoadFileConfig implements LoadFileConfig {

    private final YamlConfiguration yamlConfiguration;

    public YmlLoadFileConfig(YamlConfiguration yamlConfiguration){
        this.yamlConfiguration = yamlConfiguration;
    }
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
