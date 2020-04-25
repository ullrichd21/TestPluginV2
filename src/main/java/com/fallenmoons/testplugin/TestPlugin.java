package com.fallenmoons.testplugin;

import com.fallenmoons.testplugin.commands.*;
import com.fallenmoons.testplugin.core.AntiLogStripper;
import com.fallenmoons.testplugin.core.CauldronManager;
import com.fallenmoons.testplugin.core.GuiClickEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;


public final class TestPlugin extends JavaPlugin implements Listener {
    float amount = 0.4f;
    ArrayList<Block> sc = new ArrayList<Block>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        //Register Our Log Anti-Stripper. None of that R rated stripping here!
        getServer().getPluginManager().registerEvents(new AntiLogStripper(), this);

        //Register Cauldron Mechanics
        getServer().getPluginManager().registerEvents(new CauldronManager(), this);

        //Register GUI
        getServer().getPluginManager().registerEvents(new GuiClickEvent(), this);

        //Register Commands
        getCommand("clear").setExecutor(new Clear());
        getCommand("setspawn").setExecutor(new Setspawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("startround").setExecutor(new Startround());
        getCommand("gui").setExecutor(new Gui());

        //Custom Recipes
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Diamond Sword");

        item.addEnchantment(Enchantment.DAMAGE_ALL, 3);

        NamespacedKey key = new NamespacedKey(this, "diamond_sword");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("  D", " D ", "S  ");

        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.GRAVEL) {
            event.setDropItems(false);
            ItemStack item = new ItemStack(Material.COBBLESTONE);
            event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
        }
    }

    @EventHandler
    public void onStoneCutterPlaced(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block.getType() == Material.STONECUTTER) {
            sc.add(block);
            System.out.println("StoneCutter Registered: " + block.getLocation());
        }
    }

    @EventHandler
    public void onStoneCutterUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasBlock()) {
            if (e.getClickedBlock().getType() == Material.STONECUTTER) {
                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

                    PlayerInventory inv = player.getInventory();

                    if (inv.getItemInMainHand().getType() == Material.OAK_LOG) {

                        if (player.getGameMode().equals(GameMode.CREATIVE)) {
                            e.setCancelled(true);
                        }

                        int dropAmount = 0;
                        int dropStacks = 0;

                        if (player.isSneaking()) {
                            dropAmount = inv.getItemInMainHand().getAmount();
                            inv.getItemInMainHand().setAmount(0);
                        } else {
                            inv.getItemInMainHand().setAmount(inv.getItemInMainHand().getAmount() - 1);
                            dropAmount = 1;
                        }

                        if (dropAmount >= 64) {
                            dropStacks = (dropAmount - (dropAmount % 64)) / 64;
                            dropAmount = dropAmount % 64;

                            for (int i = 0; i < dropStacks; i++) {
                                BukkitScheduler scheduler = getServer().getScheduler();
                                scheduler.runTaskLater(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        Entity item = e.getClickedBlock().getWorld().dropItem(
                                                e.getClickedBlock().getLocation().add(new Vector(0.5, 1, 0.5)),
                                                new ItemStack(Material.STRIPPED_OAK_LOG, 64));
                                        item.setVelocity(new Vector(0, 0.2, 0));
                                    }
                                }, Long.valueOf(i));
                            }
                        }

                        if (dropAmount > 0) {
                            Entity item = e.getClickedBlock().getWorld().dropItem(
                                    e.getClickedBlock().getLocation().add(new Vector(0.5, 1, 0.5)),
                                    new ItemStack(Material.STRIPPED_OAK_LOG, dropAmount));
                            item.setVelocity(new Vector(0, 0.2, 0));
                        }

                        //Particles
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().add(0.5, 0.75, 0.5), 20, Material.OAK_LOG.createBlockData());

                        //Sound
                        player.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 0.8f);

                        //Spawn XP
//                    for(int i = 0; i < dropAmount + (dropStacks * 64); i++) {
//                        System.out.println("Attempted to spawn XP");
//                        Random r = new Random();
//                        double rX = 0 + (0.2) * r.nextDouble();
//                        double rZ = 0 + (0.2) * r.nextDouble();
//                        ExperienceOrb orb = player.getWorld().spawn(e.getClickedBlock().getLocation().add(0.5 + rX,1,0.5 + rZ), ExperienceOrb.class);
//                        orb.setExperience(1 + r.nextInt(2));
//                    }
                        for (int i = 0; i <= dropAmount + (dropStacks * 64); i++) {
                            player.giveExp(1);
                        }


                    } else if (inv.getItemInMainHand().getType() == Material.STRIPPED_OAK_LOG) {
                        if (player.getGameMode().equals(GameMode.CREATIVE)) {
                            e.setCancelled(true);
                        }

                        int dropAmount = 0;
                        int dropStacks = 0;

                        if (player.isSneaking()) {
                            dropAmount = inv.getItemInMainHand().getAmount() * 4;
                            inv.getItemInMainHand().setAmount(0);
                        } else {
                            inv.getItemInMainHand().setAmount(inv.getItemInMainHand().getAmount() - 1);
                            dropAmount = 4;
                        }

                        if (dropAmount >= 64) {
                            dropStacks = (dropAmount - (dropAmount % 64)) / 64;
                            dropAmount = dropAmount % 64;

                            for (int i = 0; i < dropStacks; i++) {
                                BukkitScheduler scheduler = getServer().getScheduler();
                                scheduler.runTaskLater(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        Entity item = e.getClickedBlock().getWorld().dropItem(
                                                e.getClickedBlock().getLocation().add(new Vector(0.5, 1, 0.5)),
                                                new ItemStack(Material.OAK_PLANKS, 64));
                                        item.setVelocity(new Vector(0, 0.2, 0));
                                    }
                                }, Long.valueOf(i));
                            }
                        }

                        if (dropAmount > 0) {
                            Entity item = e.getClickedBlock().getWorld().dropItem(
                                    e.getClickedBlock().getLocation().add(new Vector(0.5, 1, 0.5)),
                                    new ItemStack(Material.OAK_PLANKS, dropAmount));
                            item.setVelocity(new Vector(0, 0.2, 0));
                        }

                        //Particles
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().add(0.5, 0.75, 0.5), 20, Material.STRIPPED_OAK_LOG.createBlockData());

                        //Sound
                        player.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 0.8f);
                    } else if (inv.getItemInMainHand().getType() == Material.OAK_PLANKS) {
                        if (player.getGameMode().equals(GameMode.CREATIVE)) {
                            e.setCancelled(true);
                        }

                        int dropAmount = 0;
                        int dropStacks = 0;

                        if (player.isSneaking()) {
                            dropAmount = inv.getItemInMainHand().getAmount() * 4;
                            inv.getItemInMainHand().setAmount(0);
                        } else {
                            inv.getItemInMainHand().setAmount(inv.getItemInMainHand().getAmount() - 1);
                            dropAmount = 2;
                        }

                        if (dropAmount >= 64) {
                            dropStacks = (dropAmount - (dropAmount % 64)) / 64;
                            dropAmount = dropAmount % 64;

                            for (int i = 0; i < dropStacks; i++) {
                                BukkitScheduler scheduler = getServer().getScheduler();
                                scheduler.runTaskLater(this, new Runnable() {
                                    @Override
                                    public void run() {
                                        Entity item = e.getClickedBlock().getWorld().dropItem(
                                                e.getClickedBlock().getLocation().add(new Vector(0.5, 1, 0.5)),
                                                new ItemStack(Material.STICK, 64));
                                        item.setVelocity(new Vector(0, 0.2, 0));
                                    }
                                }, Long.valueOf(i));
                            }
                        }

                        if (dropAmount > 0) {
                            Entity item = e.getClickedBlock().getWorld().dropItem(
                                    e.getClickedBlock().getLocation().add(new Vector(0.5, 1, 0.5)),
                                    new ItemStack(Material.STICK, dropAmount));
                            item.setVelocity(new Vector(0, 0.2, 0));
                        }

                        //Particles
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getClickedBlock().getLocation().add(0.5, 0.75, 0.5), 20, Material.OAK_PLANKS.createBlockData());

                        //Sound
                        player.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 0.8f);
                    }
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    e.setCancelled(true);
                } else {
                    System.out.println("Not sure how " + player.getName() + " did it but they broke something...");
                }
            }
        }
    }

//    @EventHandler
//    public void onWoodDrop(PlayerDropItemEvent event) {
//        Location loc = event.getItemDrop().getLocation();
//
//        for (Block block : sc) {
//            List<Entity> l = (List<Entity>) event.getItemDrop().getWorld().getNearbyEntities(block.getLocation(), 3,3, 3);
//            System.out.println("Items Dropped: " + l);
//
//            Item item = (Item) event.getItemDrop();
//            System.out.println("Item start location: " + item.getLocation());
//
//            for(Entity entity : l) {
//                if (entity.getType() == EntityType.DROPPED_ITEM) {
//                    System.out.println("Item is DROPPED_ITEM.");
//                    Item i = (Item) entity;
//                    if (i.getItemStack().getType() == Material.OAK_LOG) {
//                        System.out.println("Item is OAK_LOG!");
//                        i.remove();
//                        loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.OAK_PLANKS, 4));
//                    }
//                }
//            }
//        }


//        loc.setY(loc.getY() - 1);
//
//        if(loc.getWorld().getBlockAt(loc).getType() == Material.STONECUTTER) {
//            event.getItemDrop().remove();
//            loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.OAK_PLANKS, 4));
//            System.out.println("DROPPED WOOD!");
//        }
//    }

    @EventHandler
    public void onExplosiveBeds(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        world.spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
    }

    @EventHandler
    public void onShearSheep(PlayerShearEntityEvent event) {
        Entity entity = event.getEntity();
        System.out.println("SHEEP LAUNCHED WITH VELOCITY OF " + amount);
        entity.setVelocity(new Vector(0,amount,0));
    }

    @EventHandler
    public void onLaunchingStick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Inventory inv = player.getInventory();

        if (((PlayerInventory) inv).getItemInMainHand().getType() == Material.STICK) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Launcher")) {
                event.getRightClicked().setVelocity(new Vector(0, amount,0));
            }
        }
    }

    @EventHandler
    public void onMeatStick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.STICK && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Meat Stick")) {
            Item item = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.PORKCHOP, 1));
            item.setVelocity(player.getLocation().getDirection());
            item.setPickupDelay(10);


        }
    }
}
