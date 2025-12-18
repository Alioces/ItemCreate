package org.example.wudu.itemCreate.page;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class PagedMenu {

    @Getter
    private final String title;

    @Setter
    private List<ItemStack> items;
    private final int pageSize; // 每页显示的物品数量
    private int currentPage = 0; // 当前页码

    public PagedMenu(String title, List<ItemStack> items, int pageSize) {
        this.title = title;
        this.items = items;
        this.pageSize = pageSize;
    }

    // 计算总页数
    public int getTotalPages() {
        return (int) Math.ceil((double) items.size() / pageSize);
    }

    // 获取当前页的物品
    public List<ItemStack> getCurrentPageItems() {
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, items.size());
        return items.subList(fromIndex, toIndex);
    }

    // 创建当前页的 Inventory
    public Inventory createPage() {
        // 6 行（54 格），最后一行用于翻页按钮,第一行用于查找
        int size = 9 * 6;
        Inventory inventory = Bukkit.createInventory(null, size, title + " (页 " + (currentPage + 1) + "/" + getTotalPages() + ")");

        // 添加当前页的物品
        List<ItemStack> pageItems = getCurrentPageItems();
        //这里从10开始，空出第一行
        for (int i = 0; i < pageItems.size(); i++) {
            inventory.setItem(i+9, pageItems.get(i));
        }

        // 添加翻页按钮
        if (currentPage > 0) {
            inventory.setItem(size - 9, createButton(Material.ARROW, "§a上一页"));
        }
        if (currentPage < getTotalPages() - 1) {
            inventory.setItem(size - 1, createButton(Material.ARROW, "§a下一页"));
        }

        return inventory;
    }

    // 创建按钮
    private ItemStack createButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta meta = button.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            button.setItemMeta(meta);
        }
        return button;
    }

    // 翻到下一页
    public void nextPage() {
        if (currentPage < getTotalPages() - 1) {
            currentPage++;
        }
    }

    // 翻到上一页
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }
}

