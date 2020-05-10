package com.fallenmoons.mcctf.mechanics.woodcutter;

import com.fallenmoons.mcctf.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getServer;

public class WoodCutter implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onStoneCutterUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block cBlock = e.getClickedBlock();

        if (e.hasBlock()) {
            if (cBlock.getType() == Material.STONECUTTER && e.getAction() == Action.LEFT_CLICK_BLOCK) {
                PlayerInventory inv = player.getInventory();

                if (inv.getItemInMainHand().getType() != null && e.getItem().getType().toString().toLowerCase().contains("log") && !e.getItem().toString().toLowerCase().contains("stripped")) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        e.setCancelled(true);
                    }

                    Material woodType = getNextWood(e.getItem().getType(), "stripped");
                    //Drop the correct items
                    int dropped = dropItems(player, cBlock, 1, 1, woodType);

                    //Particles
                    player.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().add(0.5, 0.75, 0.5), 20, e.getItem().getType().createBlockData());

                    //Sound
                    player.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 0.8f);

                    //Give XP
                    for (int i = 0; i <= dropped; i++) {
                        player.giveExp(1);
                    }
                } else if (e.getItem().getType().toString().toLowerCase().contains("stripped")) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        e.setCancelled(true);
                    }


                    Material woodType = getNextWood(e.getItem().getType(), "planks");
                    //Drop the correct items
                    dropItems(player, cBlock, 1, 4, woodType);

                    //Particles
                    player.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().add(0.5, 0.75, 0.5), 20, e.getItem().getType().createBlockData());

                    //Sound
                    player.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 0.8f);
                } else if (e.getItem().getType().toString().toLowerCase().contains("planks")) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        e.setCancelled(true);
                    }

                    //Drop the correct items
                    int dropped = dropItems(player, cBlock, 1, 4, Material.STICK);

                    //Particles
                    player.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().add(0.5, 0.75, 0.5), 20, e.getItem().getType().createBlockData());

                    //Sound
                    player.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 0.8f);
                } else {
                    //plugin.getLogger().info("Not sure how " + player.getName() + " did it but they broke something...");
                }
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.STONECUTTER) {
                e.setCancelled(true);
            }
        }
    }

    private int dropItems(Player player, Block cBlock, int singleUseAmount, int singleUseDropAmount, Material dropItem) {
        PlayerInventory inv = player.getInventory();

        int dropAmount = 0;
        int dropStacks = 0;

        if (player.isSneaking()) {
            dropAmount = inv.getItemInMainHand().getAmount() * singleUseDropAmount;
            inv.getItemInMainHand().setAmount(0);
        } else {
            inv.getItemInMainHand().setAmount(inv.getItemInMainHand().getAmount() - singleUseAmount);
            dropAmount = singleUseDropAmount;
        }

        if (dropAmount >= 64) {
            dropStacks = (dropAmount - (dropAmount % 64)) / 64;
            dropAmount = dropAmount % 64;

            for (int i = 0; i < dropStacks; i++) {
                BukkitScheduler scheduler = getServer().getScheduler();
                scheduler.runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Entity item = cBlock.getWorld().dropItem(
                                cBlock.getLocation().add(new Vector(0.5, 1, 0.5)),
                                new ItemStack(dropItem, 64));
                        item.setVelocity(new Vector(0, 0.2, 0));
                    }
                }, Long.valueOf(i));
            }
        }

        if (dropAmount > 0) {
            Entity item = cBlock.getWorld().dropItem(
                    cBlock.getLocation().add(new Vector(0.5, 1, 0.5)),
                    new ItemStack(dropItem, dropAmount));
            item.setVelocity(new Vector(0, 0.2, 0));
        }

        return dropAmount + (dropStacks * 64);
    }

    //Gets the proper next wood item based on the input
    private Material getNextWood(Material input, String returnTypeWanted) {
        int woodType = 0;

        /*
        * Dark Oak = 5
        * Acacia = 4
        * Jungle = 3
        * Birch = 2
        * Spruce = 1
        * Oak = 0
        */

        if (input.toString().toLowerCase().contains("dark")) {
            woodType = 5;
        } else if (input.toString().toLowerCase().contains("acacia")) {
            woodType = 4;
        } else if (input.toString().toLowerCase().contains("jungle")) {
            woodType = 3;
        } else if (input.toString().toLowerCase().contains("birch")) {
            woodType = 2;
        } else if (input.toString().toLowerCase().contains("spruce")) {
            woodType = 1;
        } else if (input.toString().toLowerCase().contains("oak")) {
            woodType = 0;
        }

        return getWood(woodType, returnTypeWanted);
    }

    private Material getWood(int woodType, String woodTypeWanted) {
        switch (woodTypeWanted.toLowerCase()) {
            case "log": {
                switch (woodType) {
                    case 0: {return Material.OAK_LOG;}
                    case 1: {return Material.SPRUCE_LOG;}
                    case 2: {return Material.BIRCH_LOG;}
                    case 3: {return Material.JUNGLE_LOG;}
                    case 4: {return Material.ACACIA_LOG;}
                    case 5: {return Material.DARK_OAK_LOG;}
                }
            }

            case "stripped": {
                switch (woodType) {
                    case 0: {return Material.STRIPPED_OAK_LOG;}
                    case 1: {return Material.STRIPPED_SPRUCE_LOG;}
                    case 2: {return Material.STRIPPED_BIRCH_LOG;}
                    case 3: {return Material.STRIPPED_JUNGLE_LOG;}
                    case 4: {return Material.STRIPPED_ACACIA_LOG;}
                    case 5: {return Material.STRIPPED_DARK_OAK_LOG;}
                }
            }

            case "planks": {
                switch (woodType) {
                    case 0: {return Material.OAK_PLANKS;}
                    case 1: {return Material.SPRUCE_PLANKS;}
                    case 2: {return Material.BIRCH_PLANKS;}
                    case 3: {return Material.JUNGLE_PLANKS;}
                    case 4: {return Material.ACACIA_PLANKS;}
                    case 5: {return Material.DARK_OAK_PLANKS;}
                }
            }
        }

        return Material.STICK;
    }
}
