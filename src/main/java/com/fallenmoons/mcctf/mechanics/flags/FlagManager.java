package com.fallenmoons.mcctf.mechanics.flags;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FlagManager implements Listener {

    private Main main;
    private TeamManager teamManager;
    private ArrayList<Player> flagCarriers;

    public FlagManager(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
        this.flagCarriers = new ArrayList<Player>();
    }

    @EventHandler
    public void onFlagPickup(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType() == Material.RED_BANNER) {
            if (teamManager.getTeamFromPlayer(player) != null && !teamManager.getTeamFromPlayer(player).getTeamName().equalsIgnoreCase("red")) {
                e.getClickedBlock().setType(Material.AIR);

                ArmorStand entity = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                entity.setSmall(true);
                entity.setVisible(false);
                entity.setCollidable(false);
                entity.setSilent(true);
                entity.setBasePlate(false);
                entity.setMarker(true);
                entity.getEquipment().setHelmet(new ItemStack(Material.RED_BANNER, 1));
                entity.setInvulnerable(true);
                player.addPassenger(entity);
                player.sendMessage(ChatColor.GREEN + "Flag Picked Up!");
                flagCarriers.add(player);
                player.setGlowing(true);
                e.setCancelled(true);
            } else if (teamManager.getTeamFromPlayer(player) != null && teamManager.getTeamFromPlayer(player).getTeamName().equalsIgnoreCase("red")){
                e.getClickedBlock().setType(Material.AIR);
                player.sendMessage("Flag Returned");
            }
        } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType() == Material.DISPENSER) {
            e.setCancelled(true);

            if (flagCarriers.contains(player)) {

                player.setGlowing(false);
                player.sendMessage(ChatColor.GREEN + "Flag Captured!");
                player.getPassengers().get(0).remove();
                flagCarriers.remove(player);

                e.getClickedBlock().getRelative(BlockFace.UP).setType(Material.RED_BANNER);
            }
        }
    }
}
