package org.example.wudu.itemCreate;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.itemEvent.ItemEventListener;

public final class ItemCreate extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ItemEventListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
