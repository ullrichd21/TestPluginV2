package com.fallenmoons.mcctf.mechanics.cauldron;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class PotionRecipeManager {
    private ArrayList<PotionRecipe> recipes;

    public PotionRecipeManager() {
        recipes = new ArrayList<PotionRecipe>();
    }

    public void registerRecipe(PotionRecipe recipe) {
        recipes.add(recipe);
    }

    public PotionType getPotion(ArrayList<Material> ingredients) {
        for(PotionRecipe recipe : recipes) {
            if (recipe.getSteps().equals(ingredients)) {
                return recipe.getResult();
            }
        }

        return PotionType.MUNDANE;
    }
}
