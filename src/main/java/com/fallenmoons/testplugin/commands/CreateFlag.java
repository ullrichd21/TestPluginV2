package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CreateFlag implements CommandExecutor {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {

                player.sendMessage("No flag in hand, creating new flag!");

                NamespacedKey key = new NamespacedKey(plugin, "team-color-flag");

                ItemStack flag = new ItemStack(Material.RED_BANNER, 1);
                ItemMeta flagMeta = flag.getItemMeta();
                flagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "RED");
                flag.setItemMeta(flagMeta);

                player.getWorld().dropItem(player.getLocation(), flag);
            } else {
                player.sendMessage("Item in hand: " + player.getInventory().getItemInMainHand());
                NamespacedKey key = new NamespacedKey(plugin, "team-color-flag");

                ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(key, PersistentDataType.STRING)) {
                    String val = container.get(key, PersistentDataType.STRING);

                    if (val.equalsIgnoreCase("RED")) {
                        player.sendMessage("This is a team flag of: " + val);
                    }
                }
            }



        } else {
            plugin.getLogger().info("You must be a player to use this command!");
        }

        return true;
    }
}
