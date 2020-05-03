package com.fallenmoons.mcctf.core.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiUseEvent extends Event implements Cancellable {
    private ItemStack clickedObject;
    private String invTitle;
    private InventoryClickEvent env;
    private boolean isCancelled;

    public GuiUseEvent(ItemStack clickedObject, String invTitle, InventoryClickEvent env) {
        this.clickedObject = clickedObject;
        this.invTitle = invTitle;
        this.env = env;
        this.isCancelled = false;
    }

    public InventoryClickEvent getClickEvent() {
        return env;
    }

    public String getInvTitle() {
        return invTitle;
    }

    public ItemStack getClickedObject() {
        return clickedObject;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
