package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Gui implements CommandExecutor {
    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 9, "GUI");

            ItemStack log = new ItemStack(Material.OAK_LOG, 1);
            ItemMeta logmeta = log.getItemMeta();
            logmeta.setDisplayName("Buy 64 Logs - $10");
            log.setItemMeta(logmeta);

            gui.setItem(4, log);

            player.openInventory(gui);
        } else {
            plugin.getLogger().info("You need to be a player to use this command!");
        }

        return true;
    }
}
