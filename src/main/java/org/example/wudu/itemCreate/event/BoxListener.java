package org.example.wudu.itemCreate.event;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class BoxListener implements Listener {
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
                //player.give()
                //创建一个 Inventory 对象，表示一个 9x3 的箱子
                Inventory inventory = Bukkit.createInventory(null, 9*3, "§6自定义箱子");
                // 打开自定义菜单
                player.openInventory(inventory);
            }
        }
    }

}