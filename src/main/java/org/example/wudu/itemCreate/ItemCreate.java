package org.example.wudu.itemCreate;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.wudu.itemCreate.item.CustomItemFactory;
import org.example.wudu.itemCreate.item.CustomItemManager;
import org.example.wudu.itemCreate.item.ItemLibrary;
import org.example.wudu.itemCreate.itemEvent.ItemEventListener;
/**
 * 这是一个自定义物品创建的插件主类
 * 使用物品库委托存储
 * 使用了Lombok的@Getter注解自动生成getter方法
 * 继承自JavaPlugin，表示这是一个Bukkit/Spigot插件
 */
@Getter
public final class ItemCreate extends JavaPlugin {

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
        getServer().getPluginManager().registerEvents(new ItemEventListener(),this);

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
            case "openAllItemMenu":
                // 检查命令发送者是否为玩家
                if (!(sender instanceof Player)){
                    // 如果不是玩家，则不做任何处理（可以在这里发送消息给控制台）
                    break;
                }

                // 如果是玩家，打开物品菜单（代码中只返回true，实际实现可能需要添加菜单打开逻辑）
                return true;
        }
        // 如果没有匹配的命令或命令执行失败，返回false
        return false;
    }
}
