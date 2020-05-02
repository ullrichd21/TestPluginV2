package com.fallenmoons.mcctf.items;

import com.fallenmoons.mcctf.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class GrapplingHook implements Listener {

    private final Plugin plugin = Main.getPlugin(Main.class);
    private final ArrayList<Player> ACTIVEHOOKS;

    public GrapplingHook() {
        ACTIVEHOOKS = new ArrayList<>();
    }

    @EventHandler
    public void onGrapplingReelIn(PlayerFishEvent e) {
        Player player = e.getPlayer();

        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)
                && e.getCaught() != null && e.getCaught().getType() == EntityType.ARMOR_STAND ) {
            if (e.getCaught().getCustomName().equalsIgnoreCase("grapplehook")) {
                e.getHook().setBounce(false);
                ACTIVEHOOKS.add(player);
            }
        }


        if (ACTIVEHOOKS.contains(player)) {
            Location origin = player.getEyeLocation();
            Location target = e.getHook().getLocation();

            Vector dir = target.toVector().subtract(origin.toVector());

            int hookSpeed = 2;
            player.setVelocity(dir.normalize().multiply(hookSpeed));
            ACTIVEHOOKS.remove(player);
        }
    }

    @EventHandler
    public void onHookCreator(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand() != null) {
            ItemStack i = player.getInventory().getItemInMainHand();
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getItem() != null && e.getItem().equals(i)) {
                if (e.hasBlock() && i.hasItemMeta()) {
                    if (i.getItemMeta().hasDisplayName()) {
                        if (player.getInventory().getItemInMainHand().getItemMeta()
                                .getDisplayName().equalsIgnoreCase("hook placer")) {
                            Block cBlock = e.getClickedBlock();

                            assert cBlock != null;
                            ArmorStand armorStand =
                                    cBlock.getWorld().spawn(cBlock.getLocation().add(0.5, 0, 0.5), ArmorStand.class);
                            cBlock.setType(Material.AIR);

                            armorStand.setCollidable(true);
                            armorStand.setVisible(false);
                            armorStand.setGravity(false);
                            armorStand.setSmall(true);

                            ItemStack hookItem = new ItemStack(Material.SLIME_BLOCK);
                            armorStand.getEquipment().setHelmet(hookItem);
                            armorStand.setBasePlate(false);
                            armorStand.setCustomName("grapplehook");

                            player.getWorld().spawnParticle(Particle.SLIME, cBlock.getLocation().add(0.5,0,0.5), 30);
                            player.playSound(cBlock.getLocation(), Sound.BLOCK_METAL_PLACE, 1, 2.0F);

                            player.sendMessage(ChatColor.GREEN + "Grappling Hook Location Created Successfully!");
                            plugin.getLogger().info("New Grappling Hook Location Created Successfully!");
                        }
                    }
                }
            }
        }
    }
}
