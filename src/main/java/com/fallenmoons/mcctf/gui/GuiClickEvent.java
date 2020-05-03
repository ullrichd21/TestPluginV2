package com.fallenmoons.mcctf.gui;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.events.GuiUseEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiClickEvent implements Listener {
    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String invTitle = e.getView().getTitle();
        ItemStack clickedObject = e.getCurrentItem();

        GuiUseEvent event = new GuiUseEvent(clickedObject, invTitle, e);
        Bukkit.getPluginManager().callEvent(event);
    }
}
