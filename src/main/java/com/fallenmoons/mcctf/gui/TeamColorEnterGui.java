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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeamColorEnterGui implements Listener {

    private Main main;
    private Player player;
    private String teamName;


    public TeamColorEnterGui(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onEnteredName(TeamNameEnter e) {
        player = e.getPlayer();
        player.closeInventory();
        Inventory gui = Bukkit.createInventory(player, 9, "Please Select a Team Color");

        teamName = e.getName();

        ItemStack[] colors = new ItemStack[9];

        colors[0] = getColorItem(Material.LIGHT_BLUE_CONCRETE, ChatColor.BLUE, "Light Blue", "BLUE");
        colors[1] = getColorItem(Material.BLUE_CONCRETE, ChatColor.DARK_BLUE, "Dark Blue", "DARK_BLUE");
        colors[2] = getColorItem(Material.GREEN_CONCRETE, ChatColor.DARK_GREEN, "Green", "DARK_GREEN");
        colors[3] = getColorItem(Material.LIME_CONCRETE, ChatColor.GREEN, "Lime", "GREEN");
        colors[4] = getColorItem(Material.RED_CONCRETE, ChatColor.DARK_RED, "Red", "DARK_RED");
        colors[5] = getColorItem(Material.PURPLE_CONCRETE, ChatColor.DARK_PURPLE, "Purple", "DARK_PURPLE");
        colors[6] = getColorItem(Material.YELLOW_CONCRETE, ChatColor.YELLOW, "Yellow", "YELLOW");
        colors[7] = getColorItem(Material.WHITE_CONCRETE, ChatColor.WHITE, "White", "WHITE");
        colors[8] = getColorItem(Material.BLACK_CONCRETE, ChatColor.BLACK, "Black", "BLACK");

        for(int i = 0; i < colors.length; i++) {
            gui.setItem(i, colors[i]);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onEnterNameClick(GuiUseEvent e) {
        if (e.getInvTitle().equals("Please Select a Team Color")) {
            String itemName = e.getClickedObject().getItemMeta().getDisplayName();

            if (ChatColor.stripColor(itemName).length() > 0) {
                e.getClickEvent().setCancelled(true);

                ChatColor selectedColor = ChatColor.valueOf(e.getClickedObject().getItemMeta().getLore().get(0));

                if (!main.getTeamManager().colorInUse(selectedColor)) {
                    main.getTeamManager().createTeam(teamName, selectedColor);
                    e.getClickEvent().setCancelled(true);
                    player.sendMessage(ChatColor.GREEN + "Team Successfully Created! Use \"/jointeam"
                            + teamName + "\" to join it!");
                    player.closeInventory();
                } else {
                    player.sendMessage(ChatColor.RED + "This color is already taken!");
                    e.getClickEvent().setCancelled(true);
                }

            } else {
                e.getClickEvent().setCancelled(true);
            }
        }
    }

    private ItemStack getColorItem(Material mat, ChatColor color, String name, String colorCode) {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(colorCode.toUpperCase());
        meta.setLore(lore);
        meta.setDisplayName(color + name);
        item.setItemMeta(meta);

        return item;
    }

}
