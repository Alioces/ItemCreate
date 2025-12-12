package org.example.wudu.itemCreate.item;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemManager {

    private final Map<Integer,CustomItem> customItemMap = new HashMap<>();

    private final Map<String,Integer> itemNameToId = new HashMap<>();

    private final JavaPlugin plugin;

    public CustomItemManager(JavaPlugin plugin) {
        this.plugin = plugin;

    }
}
