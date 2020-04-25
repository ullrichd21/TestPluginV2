package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class GuiClickEvent implements Listener {

    Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Gui")) {
            e.setCancelled(true);

            if (e.getCurrentItem().equals(null)) {
                return;
            } else if (e.getCurrentItem().getType().equals(Material.OAK_LOG)) {
                e.getWhoClicked().getInventory().addItem(new ItemStack(Material.OAK_LOG, 64));
                player.closeInventory();
            }
        }


    }
}
