package com.fallenmoons.testplugin;

import com.fallenmoons.testplugin.commands.*;
import com.fallenmoons.testplugin.core.*;
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
import org.bukkit.material.Wood;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;


public final class TestPlugin extends JavaPlugin implements Listener {
    float amount = 0.4f;
    ArrayList<Block> sc = new ArrayList<Block>();

    private TeamManager teamManager;

    @Override
    public void onEnable() {

        //Create Team Manager
        teamManager = new TeamManager(new String[] {"Blue", "Red", "Green"});

        getServer().getPluginManager().registerEvents(this, this);

        //Register Our Log Anti-Stripper. None of that R rated stripping here!
        getServer().getPluginManager().registerEvents(new AntiLogStripper(), this);

        //Register Cauldron Mechanics
        getServer().getPluginManager().registerEvents(new CauldronManager(), this);

        //Register GUI
        getServer().getPluginManager().registerEvents(new GuiClickEvent(), this);

        //Register WoodCutter Mechanics
        getServer().getPluginManager().registerEvents(new WoodCutter(), this);

        //Register Anti-Friendly Fire Mechanics
        getServer().getPluginManager().registerEvents(new AntiFriendlyFire( teamManager), this);

        //Register Commands
        getCommand("clear").setExecutor(new Clear());
        getCommand("setspawn").setExecutor(new Setspawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("startround").setExecutor(new Startround(teamManager));
        getCommand("gui").setExecutor(new Gui());
        getCommand("jointeam").setExecutor(new JoinTeam(teamManager));
        getCommand("viewteams").setExecutor(new ViewTeams(teamManager));




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

    public TeamManager getTeamManager() {
        return teamManager;
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
