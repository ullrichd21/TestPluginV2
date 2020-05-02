package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GiveCustom implements CommandExecutor {
    private Main main;

    public GiveCustom(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            Player player = null;
            for(Player p : main.getServer().getOnlinePlayers()) {
                if (p.getName().equals(args[0])) {
                    player = p;
                }
            }

            if (player == null) {
                return false;
            }

            ItemStack i = null;

            if (args[1].equalsIgnoreCase("grappling_hook")) {
                i = new ItemStack(Material.FISHING_ROD, 1);
                ItemMeta meta = i.getItemMeta();
                meta.setDisplayName("Grappling Hook");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.DARK_PURPLE + "Mechanical Spider Webs!");
                meta.setLore(lore);
                i.setItemMeta(meta);
            }

            if (i != null) {
                player.getInventory().addItem(i);
            } else {
                player.sendMessage(ChatColor.RED + "Item Doesn't Exist");
                return true;
            }
        }

        return false;
    }
}
