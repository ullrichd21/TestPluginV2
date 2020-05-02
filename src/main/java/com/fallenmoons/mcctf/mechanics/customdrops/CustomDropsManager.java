package com.fallenmoons.mcctf.mechanics.customdrops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CustomDropsManager implements Listener {

    private ArrayList<CustomDrop> dropsList;

    public CustomDropsManager() {
        this.dropsList = new ArrayList<>();

        dropsList.add(new CustomDrop(Material.GRAVEL, new ItemStack(Material.COBBLESTONE, 1)));
        dropsList.add(new CustomDrop(Material.OAK_LEAVES, new ItemStack(Material.STICK, 1)));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block blockBroken = e.getBlock();

        for(CustomDrop c : dropsList) {
            if (c.getSourceBlock().equals(blockBroken.getType())) {
                e.setDropItems(false);
                blockBroken.getWorld().dropItemNaturally(blockBroken.getLocation(), c.getDrop());
            }
        }
    }

}
