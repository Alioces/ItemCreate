package org.example.wudu.itemCreate;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.config.YmlLoadFileConfig;
import org.example.wudu.itemCreate.event.BoxListener;
import org.example.wudu.itemCreate.event.MenuListener;
import org.example.wudu.itemCreate.item.CustomItem;
import org.example.wudu.itemCreate.item.CustomItemManager;
import org.example.wudu.itemCreate.item.ItemLibrary;
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

    /**
     * 插件加载时调用
     * 初始化自定义物品管理器
     */
    @Override
    public void onLoad() {
        // 创建自定义物品库实例，传入自定义物品管理器
        this.customItemManager = new CustomItemManager(this);
    }

    /**
     * 插件启用时调用
     * 注册自定义物品配置和事件监听器
     */
    @Override
    public void onEnable() {
       /* 当 Bukkit 的配置系统读取到这种格式时，它会：
        1.看到 ==: 标记
        2.读取指定的类名
        3.使用该类的静态 deserialize() 方法创建对象实例*/
        //注册可序列化的类。不能的话系统看到 == 标记 无法读取指定的类名从而导致系统抛出异常。
        ConfigurationSerialization.registerClass(ItemRarity.class);
        ConfigurationSerialization.registerClass(CustomItem.class);

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ItemEventListener(),this);
        customItemManager = new CustomItemManager(this);


     /*   // 注册CustomItem的序列化
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

    /**
     * 插件禁用时调用
     * 执行插件关闭时的清理工作
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // 可以在此处添加插件关闭时的资源释放和清理代码
    }

    /**
     * 处理玩家输入的命令
     * {@inheritDoc}
     *
     * @param sender 命令发送者
     * @param command 执行的命令对象
     * @param label 命令标签（别名）
     * @param args 命令参数数组
     * @return 命令是否成功执行
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 根据命令名称进行不同的处理
        switch (command.getName()){
            // 处理打开所有物品菜单的命令
            case "menu":
                // 检查命令发送者是否为玩家
                if (!(sender instanceof Player)){
                    // 如果不是玩家，则不做任何处理（可以在这里发送消息给控制台）
                    break;
                }
                // 如果是玩家，打开物品菜单（代码中只返回true，实际实现可能需要添加菜单打开逻辑）
                getLogger().info("菜单插件已启用！");
                //创建物品集合
                List<ItemStack> list = itemLibrary.seeAllItems().stream().map(item -> customItemFactory.createItemStack(item)).toList();
                getLogger().info("物品库存："+list);
                getLogger().info("物品代码库存："+itemLibrary.seeAllItems());
                        /*new ArrayList<>(
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
                );*/
                // 创建分页菜单
                PagedMenu menu = new PagedMenu("§6分页菜单", list, 45); // 每页 54-9 个物品
                //注册菜单事件监听器
                getServer().getPluginManager().registerEvents(new MenuListener(menu), this);
                //注册箱子监听器
                getServer().getPluginManager().registerEvents(new BoxListener(), this);
                Player player = (Player) sender; //将 sender[CommandSender 可以是：玩家（Player），控制台（Console），命令方块（Command Block）] 强制转换为 Player 类型。这样做是因为我们需要使用 Player 类特有的方法（比如打开背包、传送等）
                player.openInventory(menu.createPage());//打开菜单
                return true;
        }
        // 如果没有匹配的命令或命令执行失败，返回false
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
