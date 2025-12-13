package org.example.wudu.itemCreate.event;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class BoxListener implements Listener {
    //创建一个标注为 @EventHandler 的方法。 这个方法可以命名为任何你想要的名称，但建议命名为与它正在监听的事件相关的有意义的名称。
    // 检查打开的是否是箱子
   /* @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        // 当玩家右键点击箱子并打开它时，会触发 InventoryOpenEvent
        if (event.getInventory().getHolder() instanceof Chest) {
            // 这里可以添加当玩家打开箱子时的逻辑
            Player player = (Player) event.getPlayer();
            // 打开自定义箱子
            player.openInventory(inventory);
            player.sendMessage("你打开了一个箱子！");
        }
    }*/

    //当玩家与已打开的箱子界面交互时（点击物品、移动物品等），会触发 InventoryClickEvent：
    /*@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 检查点击的是否是箱子界面
        if (event.getInventory().getHolder() instanceof Chest) {
            // 这里可以添加当玩家点击箱子内物品时的逻辑
            Player player = (Player) event.getWhoClicked();
            player.sendMessage("你点击了箱子里的物品！");

            // 如果需要阻止这个点击行为
            // event.setCancelled(true);
        }
    }*/
    //打开自定义箱子
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 检查是否是右键点击
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // 检查点击的方块是否是箱子
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
                // 取消原事件，防止打开原生箱子
                event.setCancelled(true);
                // 获取点击箱子的玩家
                Player player = event.getPlayer();
                //创建一个 Inventory 对象，表示一个 9x3 的箱子
                Inventory inventory = Bukkit.createInventory(null, 9*3, "§6自定义箱子");
                // 打开自定义菜单
                player.openInventory(inventory);
            }
        }
    }

}
