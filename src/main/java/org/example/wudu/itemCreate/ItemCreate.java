package org.example.wudu.itemCreate;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.config.YmlLoadFileConfig;
import org.example.wudu.itemCreate.event.BoxListener;
import org.example.wudu.itemCreate.event.MenuListener;
import org.example.wudu.itemCreate.item.CustomItem;
import org.example.wudu.itemCreate.item.CustomItemManager;
import org.example.wudu.itemCreate.item.ItemRarity;
import org.example.wudu.itemCreate.itemEvent.ItemEventListener;
import org.example.wudu.itemCreate.page.PagedMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ItemCreate extends JavaPlugin {
    private YmlLoadFileConfig ymlLoadFileConfig = new YmlLoadFileConfig(new YamlConfiguration());
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

        // 注册CustomItem的序列化
        ConfigurationSerialization.registerClass(CustomItem.class);
        //反序列化为CustomItem对象
        // ymlLoadFileConfig.readFileFolder(new File("C:/MineCraft/demo.yml"));
         ymlLoadFileConfig.readFile(new File("C:/MineCraft/demo.yml"));
        //将CustomItem对象序列化为yml文件
         CustomItem item = new CustomItem();
         item.setId(1);
         item.setName("Custom Item");
         item.setType("custom");
         item.setItemRarity(ItemRarity.LEGENDARY);
         //item.setItemStack(new ItemStack(Material.DIAMOND_SWORD)); 无参方法默认为Material.AIR
          ymlLoadFileConfig.saveFile(item);


        //菜单插件
        getLogger().info("菜单插件已启用！");
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            //使用 LiteralArgumentBuilder 来构建的命令树
            //"menu"：命令的名称，玩家需要输入 /menu 来触发这个命令
            LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("menu").executes(ctx -> {
                //执行命令的逻辑
                //获取命令执行者
                CommandSourceStack source = ctx.getSource();
                //判断命令执行者是否为玩家
                if (source.getSender() instanceof Player player) { //获取玩家
                    List<ItemStack> list = new ArrayList<>(
                            Arrays.asList(
                                    new ItemStack(Material.ACACIA_BOAT), new ItemStack(Material.ACACIA_BUTTON), new ItemStack(Material.ACACIA_CHEST_BOAT),
                                    new ItemStack(Material.ACACIA_DOOR), new ItemStack(Material.ACACIA_FENCE), new ItemStack(Material.ACACIA_FENCE_GATE),
                                    new ItemStack(Material.ACACIA_HANGING_SIGN), new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.ACACIA_LOG),
                                    new ItemStack(Material.ACACIA_PLANKS), new ItemStack(Material.ACACIA_PRESSURE_PLATE), new ItemStack(Material.ACACIA_SAPLING),
                                    new ItemStack(Material.ACACIA_SHELF), new ItemStack(Material.ACACIA_SIGN), new ItemStack(Material.ACACIA_SLAB),
                                    new ItemStack(Material.ACTIVATOR_RAIL), new ItemStack(Material.ACACIA_STAIRS), new ItemStack(Material.ACACIA_TRAPDOOR),
                                    new ItemStack(Material.ACACIA_WOOD),
                                    new ItemStack(Material.ACACIA_BOAT), new ItemStack(Material.ACACIA_BUTTON), new ItemStack(Material.ACACIA_CHEST_BOAT),
                                    new ItemStack(Material.ACACIA_DOOR), new ItemStack(Material.ACACIA_FENCE), new ItemStack(Material.ACACIA_FENCE_GATE),
                                    new ItemStack(Material.ACACIA_HANGING_SIGN), new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.ACACIA_LOG),
                                    new ItemStack(Material.ACACIA_PLANKS), new ItemStack(Material.ACACIA_PRESSURE_PLATE), new ItemStack(Material.ACACIA_SAPLING),
                                    new ItemStack(Material.ACACIA_SHELF), new ItemStack(Material.ACACIA_SIGN), new ItemStack(Material.ACACIA_SLAB),
                                    new ItemStack(Material.ACTIVATOR_RAIL), new ItemStack(Material.ACACIA_STAIRS), new ItemStack(Material.ACACIA_TRAPDOOR),
                                    new ItemStack(Material.ACACIA_WOOD),
                                    new ItemStack(Material.ACACIA_BOAT), new ItemStack(Material.ACACIA_BUTTON), new ItemStack(Material.ACACIA_CHEST_BOAT),
                                    new ItemStack(Material.ACACIA_DOOR), new ItemStack(Material.ACACIA_FENCE), new ItemStack(Material.ACACIA_FENCE_GATE),
                                    new ItemStack(Material.ACACIA_HANGING_SIGN), new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.ACACIA_LOG),
                                    new ItemStack(Material.ACACIA_PLANKS), new ItemStack(Material.ACACIA_PRESSURE_PLATE), new ItemStack(Material.ACACIA_SAPLING),
                                    new ItemStack(Material.ACACIA_SHELF), new ItemStack(Material.ACACIA_SIGN), new ItemStack(Material.ACACIA_SLAB),
                                    new ItemStack(Material.ACTIVATOR_RAIL), new ItemStack(Material.ACACIA_STAIRS), new ItemStack(Material.ACACIA_TRAPDOOR),
                                    new ItemStack(Material.ACACIA_WOOD),
                                    new ItemStack(Material.ACACIA_BOAT), new ItemStack(Material.ACACIA_BUTTON), new ItemStack(Material.ACACIA_CHEST_BOAT),
                                    new ItemStack(Material.ACACIA_DOOR), new ItemStack(Material.ACACIA_FENCE), new ItemStack(Material.ACACIA_FENCE_GATE),
                                    new ItemStack(Material.ACACIA_HANGING_SIGN), new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.ACACIA_LOG),
                                    new ItemStack(Material.ACACIA_PLANKS), new ItemStack(Material.ACACIA_PRESSURE_PLATE), new ItemStack(Material.ACACIA_SAPLING),
                                    new ItemStack(Material.ACACIA_SHELF), new ItemStack(Material.ACACIA_SIGN), new ItemStack(Material.ACACIA_SLAB),
                                    new ItemStack(Material.ACTIVATOR_RAIL), new ItemStack(Material.ACACIA_STAIRS), new ItemStack(Material.ACACIA_TRAPDOOR),
                                    new ItemStack(Material.ACACIA_WOOD)
                            )
                    );
                    // 创建分页菜单
                    PagedMenu menu = new PagedMenu("§6分页菜单", list, 45); // 每页 54-9 个物品
                    //注册菜单事件监听器
                    getServer().getPluginManager().registerEvents(new MenuListener(menu), this);
                    //打开菜单
                    player.openInventory(menu.createPage());
                    //返回一个整数，表示命令执行的结果。
                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                }
                return com.mojang.brigadier.Command.SINGLE_SUCCESS;
            });
            //将构建的命令树转换为一个可执行的 LiteralCommandNode<CommandSourceStack>。返回一个 LiteralCommandNode，这是 Brigadier 命令树的根节点，可以注册到服务器。
            LiteralCommandNode<CommandSourceStack> buildCommand = command.build();
            //.registrar()：返回一个命令注册器（CommandRegistrar），用于将命令节点注册到服务器的命令系统中。
            //.register()：将 buildCommand 注册到服务器，使其可以被玩家或控制台执行。
            commands.registrar().register(buildCommand);
            //注册箱子监听器
            getServer().getPluginManager().registerEvents(new BoxListener(), this);

        });

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
                ItemRarity.RARE,
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
