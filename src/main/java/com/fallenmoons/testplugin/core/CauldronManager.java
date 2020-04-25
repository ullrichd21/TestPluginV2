package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;

public class CauldronManager implements Listener {
    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    private ArrayList<Cauldron> registeredCauldrons;

    private PotionRecipeManager manager;

    public CauldronManager() {
        registeredCauldrons = new ArrayList<Cauldron>();
        manager = new PotionRecipeManager();
        PotionRecipe speedPotion = new PotionRecipe(new ArrayList<Material>(Arrays.asList(Material.NETHER_WART, Material.SUGAR, Material.REDSTONE)), PotionType.SPEED);
        manager.registerRecipe(speedPotion);

    }

    @EventHandler
    public void onUseCauldron(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block cBlock = e.getClickedBlock();

        if (e.getAction() == Action.LEFT_CLICK_BLOCK && cBlock.getType() == Material.CAULDRON) {
            if (!isCauldronRegistered(cBlock)) {
                registerCauldron(cBlock);
                player.sendMessage(ChatColor.GREEN + "Cauldron Registered!");
                plugin.getLogger().info("New cauldron registered!");
            }

            Cauldron cauldron = getCauldron(cBlock);

            //plugin.getLogger().info("Cauldron Found!");
            int lvl = ((Levelled) cBlock.getBlockData()).getLevel();
            if (e.getItem() == null) {
                player.sendMessage(ChatColor.GREEN + "Current Ingredients: " + cauldron.getIngredients() + " lvl: " + lvl);
            }



            if (lvl > 0) {
                if (player.getInventory().getItemInMainHand().getType() == Material.GLASS_BOTTLE && cauldron.getIngredientNum() > 0) {
                    if (cauldron.getIngredientNum() < 3) {
                        unregisterCauldron(cauldron);

//                        ItemStack potion = new ItemStack(Material.POTION, 1);
//                        PotionMeta meta = (PotionMeta) potion.getItemMeta();
//                        meta.setBasePotionData(new PotionData(PotionType.AWKWARD));
//                        cBlock.getWorld().dropItem(cBlock.getLocation().add(0.5,1,0.5), potion);
                        ItemStack i = new ItemStack(Material.RED_CONCRETE, 1);
                        cBlock.getWorld().dropItem(cBlock.getLocation().add(0.5,1,0.5), i);

                        //Set new water level
                        lvl--;
                        Levelled cData = (Levelled) cBlock.getBlockData();
                        cData.setLevel(lvl);
                        cBlock.setBlockData(cData);
                    } else {
//                        PotionType pot = manager.getPotion(cauldron.getIngredients());
//                        ItemStack potion = new ItemStack(Material.POTION, 1);
//                        PotionMeta meta = (PotionMeta) potion.getItemMeta();
//                        meta.setBasePotionData(new PotionData(pot));
//                        cBlock.getWorld().dropItem(cBlock.getLocation().add(0.5,1,0.5), potion);
                        ItemStack i = new ItemStack(Material.LIME_CONCRETE, 1);
                        cBlock.getWorld().dropItem(cBlock.getLocation().add(0.5,1,0.5), i);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);

                        //Set new water level
                        lvl--;
                        Levelled cData = (Levelled) cBlock.getBlockData();
                        cData.setLevel(lvl);
                        cBlock.setBlockData(cData);
                    }
                } else if (player.getInventory().getItemInMainHand().getType() == Material.NETHER_WART) {
                    if (cauldron.slotsRemaining() > 0) {
                        cauldron.addItem(Material.NETHER_WART);
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                } else if (player.getInventory().getItemInMainHand().getType() == Material.SUGAR) {
                    if (cauldron.slotsRemaining() > 0) {
                        cauldron.addItem(Material.SUGAR);
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                } else if (player.getInventory().getItemInMainHand().getType() == Material.REDSTONE) {
                    if (cauldron.slotsRemaining() > 0) {
                        cauldron.addItem(Material.REDSTONE);
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                }
            } else {
                //Unregister the Cauldron
                unregisterCauldron(cauldron);
            }
        }
    }

    public boolean isCauldronRegistered(Block block) {
        for(Cauldron cauldron : registeredCauldrons) {
            if (cauldron.getBlock().equals(block)) {
                return true;
            }
        }

        return false;
    }

    public Cauldron getCauldron(Block block) {
        for(Cauldron cauldron : registeredCauldrons) {
            if (cauldron.getBlock().equals(block)) {
                return cauldron;
            }
        }
        return null;
    }

    public void registerCauldron(Block block) {
        registeredCauldrons.add(new Cauldron(block));
    }

    public void unregisterCauldron(Cauldron cauldron) {
        registeredCauldrons.remove(cauldron);
    }


}
