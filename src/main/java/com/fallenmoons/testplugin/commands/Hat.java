package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.TreeSet;

public class Hat implements CommandExecutor {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack item = player.getInventory().getItemInMainHand();

            if (item.getType() != null ) {
                player.getEquipment().setHelmet(item);
            } else {
                player.sendMessage("You must be holding an item!");
            }
        } else {
            plugin.getLogger().info("You must be a player to use this command!");
        }
        return true;
    }
}
