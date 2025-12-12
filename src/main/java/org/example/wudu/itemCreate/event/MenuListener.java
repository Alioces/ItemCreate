package org.example.wudu.itemCreate.event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.example.wudu.itemCreate.page.PagedMenu;

public class MenuListener implements Listener {
    private final PagedMenu menu;
    public MenuListener(PagedMenu menu) {
        this.menu = menu;
    }
    //当玩家点击物品清单时，这个事件会被调用。
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        // 检查是否是只读菜单
        if (clickedInventory != null) {
            event.setCancelled(true); // 阻止所有点击,阻止拖拽
        }
        // 检查是否点击了翻页按钮
        if (event.getRawSlot() == clickedInventory.getSize() - 9) { // 上一页
            menu.previousPage();
            event.getWhoClicked().openInventory(menu.createPage());
        } else if (event.getRawSlot() == clickedInventory.getSize() - 1) { // 下一页
            menu.nextPage();
            event.getWhoClicked().openInventory(menu.createPage());
        }
    }
}
