package com.fallenmoons.mcctf.gui;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.events.GuiUseEvent;
import com.fallenmoons.mcctf.core.events.TeamNameEnter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamNameEnterGui implements Listener {

    private Main main;

    public TeamNameEnterGui(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onEnterNameClick(GuiUseEvent e) {

        if (e.getInvTitle().equals("Please Enter a Team Name")) {

            ItemStack[] teamName = new ItemStack[3];

            int startIndex = 3;
            for(int i = 0; i < teamName.length; i++) {
                teamName[i] = e.getClickEvent().getClickedInventory().getItem(startIndex + i);
            }

            if (ChatColor.stripColor(e.getClickedObject().getItemMeta().getDisplayName()).length() == 1) {
                String itemName = e.getClickedObject().getItemMeta().getDisplayName();

                if (itemName.equals(ChatColor.GOLD + ChatColor.stripColor(itemName))) {
                    e.getClickEvent().setCancelled(true);
                    for(int i = 0; i < teamName.length; i++) {
                        if (teamName[i] == null) {
                            ItemStack item = new ItemStack(Material.WHITE_CONCRETE, 1);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.AQUA + ChatColor.stripColor(itemName));
                            item.setItemMeta(meta);

                            teamName[i] = item;
                            break;
                        }

                    }


                } else {
                    e.getClickEvent().setCancelled(true);
                }
            } else if (e.getClickedObject().getItemMeta().getDisplayName().equals(ChatColor.RED + "Clear")
                        || e.getClickedObject().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Enter")) {
                e.getClickEvent().setCancelled(true);
                String itemName = ChatColor.stripColor(e.getClickedObject().getItemMeta().getDisplayName());

                StringBuilder build = new StringBuilder();

                for (int i = 0; i < teamName.length; i++) {
                    ItemStack item = e.getClickEvent().getView().getTopInventory().getItem(3 + i);
                    if (item != null) {
                        char iName = ChatColor.stripColor(item.getItemMeta().getDisplayName()).charAt(0);

                        build.append(iName);
                    }
                }

                if (itemName.equals("Clear")) {
                    clearTeamName(teamName, e);
                } else {

                    if (main.getTeamManager().nameInUse(build.toString())) {
                        Player player;

                        if (e.getClickEvent().getWhoClicked() instanceof Player) {
                            player = Bukkit.getPlayer(e.getClickEvent().getWhoClicked().getName());
                        } else {
                            return;
                        }

                        player.sendMessage(ChatColor.RED + "Name already in use!");
                        return;
                    } else {

                        if (build.toString().length() != 3) {
                            Player player;

                            if (e.getClickEvent().getWhoClicked() instanceof Player) {
                                player = Bukkit.getPlayer(e.getClickEvent().getWhoClicked().getName());
                            } else {
                                return;
                            }

                            player.sendMessage(ChatColor.RED + "Name too short!");
                            return;
                        }

                        Player player;

                        if (e.getClickEvent().getWhoClicked() instanceof Player) {
                            player = (Player) e.getClickEvent().getWhoClicked();
                        } else {
                            return;
                        }

                        main.getLogger().info("Player: " + player);
                        TeamNameEnter event = new TeamNameEnter(build.toString(), player);
                        Bukkit.getPluginManager().callEvent(event);
                        //e.getClickEvent().getWhoClicked().closeInventory();
                        return;
                    }
                }
            }

            for (int i = 0; i < teamName.length; i++) {
                e.getClickEvent().getView().getTopInventory().setItem(3 + i, teamName[i]);
            }

        }
    }


    private void clearTeamName(ItemStack[] teamName, GuiUseEvent e) {
        for (int i = 0; i < teamName.length; i++) {
            teamName[i] = null;
            e.getClickEvent().getView().getTopInventory().setItem(3 + i, null);
        }
    }
}
