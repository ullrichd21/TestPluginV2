package com.fallenmoons.mcctf.mechanics.cauldron;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class PotionRecipe {
    private ArrayList<Material> steps;
    private PotionType result;

    public PotionRecipe(ArrayList<Material> steps, PotionType result) {
        this.steps = steps;
        this.result = result;
    }

    public ArrayList<Material> getSteps() {
        return steps;
    }

    public PotionType getResult() {
        return result;
    }
}
