package org.example.wudu.itemCreate;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.config.YmlLoadFileConfig;
import org.example.wudu.itemCreate.item.CustomItemFactory;
import org.example.wudu.itemCreate.item.CustomItemManager;
import org.example.wudu.itemCreate.item.ItemLibrary;
import org.example.wudu.itemCreate.itemEvent.ItemEventListener;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.example.wudu.itemCreate.event.BoxListener;
import org.example.wudu.itemCreate.event.MenuListener;
import org.example.wudu.itemCreate.item.CustomItem;
import org.example.wudu.itemCreate.item.ItemRarity;
import org.example.wudu.itemCreate.page.PagedMenu;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 这是一个自定义物品创建的插件主类
 * 使用物品库委托存储
 * 使用了Lombok的@Getter注解自动生成getter方法
 * 继承自JavaPlugin，表示这是一个Bukkit/Spigot插件
 */
@Getter
public final class ItemCreate extends JavaPlugin {
    // 插件实例
    private YmlLoadFileConfig ymlLoadFileConfig = new YmlLoadFileConfig(new YamlConfiguration());
    // 自定义物品库对象，用于管理所有自定义物品
    private ItemLibrary itemLibrary;
    private CustomItemFactory customItemFactory;
    // 插件的命名空间键，用于唯一标识插件中的物品、配方等
    private final NamespacedKey pluginNamespaced = new NamespacedKey(this,"ItemCreate");


    /**
     * 插件加载时调用
     * 初始化自定义物品管理器
     */
    @Override
    public void onLoad() {
        // 创建自定义物品库实例，传入自定义物品管理器
        itemLibrary = new ItemLibrary(new CustomItemManager(this));
    }

    /**
     * 插件启用时调用
     * 注册自定义物品配置和事件监听器
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        // 读取自定义物品配置文件，加载所有自定义物品
        itemLibrary.getCustomItemManager().readerCustomItemConfig();
        customItemFactory = new CustomItemFactory(itemLibrary.getCustomItemManager());
        // 注册物品事件监听器，处理与物品相关的事件
        //getServer().getPluginManager().registerEvents(new ItemEventListener(),this);

        // 注册CustomItem的序列化
        ConfigurationSerialization.registerClass(CustomItem.class);
        //反序列化为CustomItem对象
        CustomItem customItem = ymlLoadFileConfig.readFile(new File(getDataFolder() + "/demo.yml"));
        //List<CustomItem> customItems = ymlLoadFileConfig.readFileFolder(new File(getDataFolder() + "/" + getConfig().getString("configureFolders")));
        List<CustomItem> customItems = ymlLoadFileConfig.readFileFolder(new File(getDataFolder().toURI()));
        //将CustomItem对象序列化为yml文件
        CustomItem item = new CustomItem();
        item.setId(1);
        item.setName("测试文件");
        item.setType("custom");
        item.setItemRarity(ItemRarity.Paper);
        ymlLoadFileConfig.saveFile(item, new File(getDataFolder()+"/" + item.getName() + ".yml"));

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
                //注册箱子监听器
                getServer().getPluginManager().registerEvents(new BoxListener(), this);
                Player player = (Player) sender; //将 sender[CommandSender 可以是：玩家（Player），控制台（Console），命令方块（Command Block）] 强制转换为 Player 类型。这样做是因为我们需要使用 Player 类特有的方法（比如打开背包、传送等）
                player.openInventory(menu.createPage());//打开菜单
                return true;
        }
        // 如果没有匹配的命令或命令执行失败，返回false
        return false;
    }
}
