package com.fallenmoons.mcctf.mechanics.cauldron;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;

// Arbitrary Comment
public class Cauldron {
    private Block block;
    private Material[] inv = new Material[3];
    private int step = 0;

    public Cauldron(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void addItem(Material i) {
        inv[step] = i;
        step++;
    }

    public int getIngredientNum() {
        return step;
    }

    public ArrayList<Material> getIngredients() {
        ArrayList<Material> ingredients = new ArrayList<Material>();
        for(Material i : inv) {
            ingredients.add(i);
        }

        return ingredients;
    }

    public int slotsRemaining() {
        return inv.length - step;
    }
}
