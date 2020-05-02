package com.fallenmoons.mcctf.mechanics.customdrops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomDrop {

    private Material sourceBlock;
    private ItemStack drop;

    public CustomDrop(Material sourceBlock, ItemStack drop) {
        this.sourceBlock = sourceBlock;
        this.drop = drop;
    }

    public Material getSourceBlock() {
        return sourceBlock;
    }

    public void setSourceBlock(Material sourceBlock) {
        this.sourceBlock = sourceBlock;
    }

    public ItemStack getDrop() {
        return drop;
    }

    public void setDrop(ItemStack drop) {
        this.drop = drop;
    }
}
