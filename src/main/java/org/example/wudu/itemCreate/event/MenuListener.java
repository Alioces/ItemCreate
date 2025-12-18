package org.example.wudu.itemCreate.event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.PlayerInventory;
import org.example.wudu.itemCreate.page.PagedMenu;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.Material;
import static org.apache.logging.log4j.LogManager.getLogger;

@Setter
@Getter
@NoArgsConstructor
public class MenuListener implements Listener {
    private PagedMenu menu;
    public MenuListener(PagedMenu menu) {
        this.menu = menu;
    }
    //InventoryClickEvent是当玩家点击库存界面中的任何槽位时触发的事件，用于处理玩家与任何库存界面（包括自定义菜单、箱子、工作台等）的交互
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 获取被点击的物品栏
        Inventory clickedInventory = event.getClickedInventory();
        // 获取点击的玩家
        Player player = (Player) event.getWhoClicked();
        // 获取顶部的物品栏（菜单）
        InventoryView topInventory = event.getView();

        // 如果顶部不是自定义菜单，直接返回
        if (!isValidMenu(topInventory)) {
            return;
        }
        // 处理点击玩家自己的物品栏的情况
        if (isPlayerInventory(clickedInventory)) {
            handlePlayerInventoryClick(event);
            return;
        }
        // 处理点击自定义菜单的情况
        if (isMenuInventory(clickedInventory)) {
            handleMenuClick(event, player);
        }
    }
    /**
     * 处理拖拽事件
     */
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        // 获取顶部物品栏（通常是打开的GUI菜单）
        Inventory topInventory = event.getView().getTopInventory();
        // 检查是否是有效的菜单,如果不是有效菜单，直接返回，不做处理
        if (!isValidMenu(event.getView())) return;
        // 检查是否有物品被拖到菜单中,如果有物品被拖到菜单，取消这个拖拽操作
        if (hasDragToMenu(event, topInventory)) event.setCancelled(true);
    }

    /**
     * 检查是否是有效的菜单
     */
    private boolean isValidMenu(InventoryView inventory) {
        return !menu.getTitle().equals(inventory.getTitle());
    }

    /**
     * 检查是否是玩家物品栏
     */
    private boolean isPlayerInventory(Inventory inventory) {
        return inventory != null && inventory.getType() == InventoryType.PLAYER;
    }

    /**
     * 检查是否是菜单物品栏
     */
    private boolean isMenuInventory(Inventory inventory) {
        return inventory != null && inventory.getType() == InventoryType.CHEST;
    }

    /**
     * 处理玩家物品栏点击事件
     */
    private void handlePlayerInventoryClick(InventoryClickEvent event) {
        // 如果是shift点击，阻止物品移动到菜单
        if (event.isShiftClick()) {
            event.setCancelled(true);
        }
        // 其他操作允许
    }

    /**
     * 处理菜单点击事件
     */
    private void handleMenuClick(InventoryClickEvent event, Player player) {
        Inventory clickedInventory = event.getClickedInventory();

        // 处理翻页按钮
        if (handleNavigationButtons(event, player)) {
            return;
        }

        // 获取点击的物品
        ItemStack clickedItem = event.getCurrentItem();
        //Material.AIR 是一个特殊的材料类型，代表空气/空位当物品栏的某个槽位是空的（没有物品）时，该槽位的物品类型就是 AIR
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            event.setCancelled(true);
            return;
        }

        // 处理不同类型的点击
        if (event.isShiftClick() && event.isLeftClick()) {
            // Shift+左键：添加一组
            addStackToPlayer(player, clickedItem);
        } else if (event.isRightClick()) {
            // 右键：添加一个
            addItemToPlayer(player, clickedItem);
        } else if (event.isLeftClick()) {
            // 左键：添加物品
            addItemToPlayer(player, clickedItem);
        }

        // 取消事件，防止物品被拿走
        event.setCancelled(true);
    }

    /**
     * 处理翻页按钮
     */
    private boolean handleNavigationButtons(InventoryClickEvent event, Player player) {
        Inventory inventory = event.getClickedInventory();
        int size = inventory.getSize();

        if (event.getRawSlot() == size - 9) { // 上一页
            menu.previousPage();
            player.openInventory(menu.createPage());
            event.setCancelled(true);
            return true;
        } else if (event.getRawSlot() == size - 1) { // 下一页
            menu.nextPage();
            player.openInventory(menu.createPage());
            event.setCancelled(true);
            return true;
        }
        return false;
    }

    /**
     * 检查是否有物品被拖到菜单中
     */
    private boolean hasDragToMenu(InventoryDragEvent event, Inventory topInventory) {
        return event.getRawSlots().stream()  // 1. 获取所有被拖拽的槽位并创建流
                .anyMatch(slot -> slot < topInventory.getSize()); // 2. 检查是否有槽位在菜单范围内
    }

    /**
     * 添加单个物品到玩家背包
     */
    private void addItemToPlayer(Player player, ItemStack item) {
        ItemStack clone = item.clone();
        clone.setAmount(1);
        player.getInventory().addItem(clone);
        player.sendMessage("已获得: " + item.getType().name());
    }

    /**
     * 添加一组物品到玩家背包
     */
    private void addStackToPlayer(Player player, ItemStack item) {
        //创建了一个新的 ItemStack 对象，它是原始物品的副本。避免修改原始物品的数量。
        ItemStack clone = item.clone();
        //item.getMaxStackSize() 会返回该物品类型的最大堆叠数，而 clone.setAmount() 则设置物品的实际数量。
        clone.setAmount(item.getMaxStackSize());
        player.getInventory().addItem(clone);
        player.sendMessage("已获得一组: " + item.getType().name());
    }
}