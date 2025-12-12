package org.example.wudu.itemCreate;

import org.bukkit.plugin.java.JavaPlugin;

public final class ItemCreate extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getLogger().info("hello-world");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
