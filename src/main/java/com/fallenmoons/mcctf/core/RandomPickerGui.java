package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Random;

public class RandomPickerGui {

    private Plugin plugin = Main.getPlugin(Main.class);

    private ArrayList<Player> playerList;

    public RandomPickerGui(ArrayList<Player> playerList) {
        this.playerList = playerList;

        for(Player p : playerList) {
            Inventory inv = Bukkit.createInventory(p, 18, "Team Captain");
            inv.setItem(5, new ItemStack(Material.ARROW, 1));

            Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

                int time = 10;
                //ArrayList<Player> playerList = playerList;

                @Override
                public void run() {
                    if (this.time == 0) {
                        return;
                    }
                    Random r = new Random();
                    Player player = playerList.get(r.nextInt(playerList.size()));

                    ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);

                    SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                    skullMeta.setOwner(player.getName());
                    skullMeta.setDisplayName(player.getName());
                    playerHead.setItemMeta(skullMeta);
                    inv.setItem(4, playerHead);
                }
            },1l, 20l);

            p.openInventory(inv);
        }

    }


}
