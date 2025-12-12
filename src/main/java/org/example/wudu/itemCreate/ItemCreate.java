package org.example.wudu.itemCreate;

import lombok.val;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.item.CustomItem;
import org.example.wudu.itemCreate.item.CustomItemManager;
import org.example.wudu.itemCreate.item.ItemRarity;
import org.example.wudu.itemCreate.itemEvent.ItemEventListener;

import java.io.File;
import java.io.IOException;

public final class ItemCreate extends JavaPlugin {

    private CustomItemManager customItemManager;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        FileConfiguration config = getConfig();
        getLogger().info("物品配置目录"+ config.getString("configureFolders"));
        File itemConfigDir = new File(getDataFolder()+"/"+config.getString("configureFolders"));
        // 假设用户第一次使用创建一个模板配置文件
        if (!itemConfigDir.isDirectory() || !itemConfigDir.exists()){
            itemConfigDir.mkdirs();
            initItemDir(itemConfigDir);
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ItemEventListener(),this);
        customItemManager = new CustomItemManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * {@inheritDoc}
     *
     * @param sender
     * @param command
     * @param label
     * @param args
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 根据命令名称进行不同的处理
        switch (command.getName()){
            case "openCookingPotMenu":
                if (!(sender instanceof Player)){
                    break;
                }

                return true;
        }
        return false;
    }

    private void initItemDir(File itemConfigDir) {
        File itemFile = new File(itemConfigDir, "模板.yml");
        YamlConfiguration loaded = YamlConfiguration.loadConfiguration(itemFile);
        CustomItem customItem = new CustomItem(
                "CustomItem",
                "testItem",
                0,
                ItemRarity.Paper,
                new ItemStack(Material.APPLE)
        );
        loaded.addDefaults(customItem.serialize());
        loaded.options().copyDefaults(true);
        try {
            loaded.save(itemFile);
        } catch (IOException e) {
            getLogger().info("无法创建一个模板配置文件");
        }
    }
}
