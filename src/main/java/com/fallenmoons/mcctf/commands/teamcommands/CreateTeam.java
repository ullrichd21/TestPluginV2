package com.fallenmoons.mcctf.commands.teamcommands;

import com.fallenmoons.mcctf.Main;
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

public class CreateTeam implements CommandExecutor {

    private Main main;

    public CreateTeam(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {

            if (args[0].length() > 3) {
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.RED + "Team name too long!");
                } else {
                    main.getLogger().info("Team name too long!");
                }

                return false;
            }

            String teamName = args[0];
            String teamColorName = args[1].toUpperCase();
            ChatColor teamColor;

            try {
                teamColor = ChatColor.valueOf(teamColorName);
            } catch (IllegalArgumentException e) {
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.RED + "Color not valid!");
                } else {
                    main.getLogger().info("Color not valid!");
                }

                return false;
            }

            main.getTeamManager().createTeam(teamName, teamColor);

            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "Team \"" + teamName
                        + "\" was created successfully with the color \"" + teamColorName.toLowerCase() + "\"!");
            } else {
                main.getLogger().info("Team \"" + teamName
                        + "\" was created successfully with the color \"" + teamColorName.toLowerCase() + "\"!");
            }

            return true;
        } else if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Inventory gui = Bukkit.createInventory(player, 36, "Please Enter a Team Name");

                ItemStack[] keys = new ItemStack[26];
                for(int i = 0; i < keys.length; i++) {
                    ItemStack item = new ItemStack(Material.GRAY_CONCRETE, 1);
                    ItemMeta meta = item.getItemMeta();

                    int ascii = 65 + i;
                    meta.setDisplayName(ChatColor.GOLD + Character.toString((char) ascii));

                    item.setItemMeta(meta);

                    keys[i] = item;
                }

                ItemStack clear = new ItemStack(Material.BARRIER, 1);
                ItemMeta meta = clear.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Clear");
                clear.setItemMeta(meta);

                ItemStack enter = new ItemStack(Material.LIME_CONCRETE, 1);
                meta = enter.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Enter");
                enter.setItemMeta(meta);

                gui.setItem(0, clear);
                gui.setItem(8, enter);

                for(int i = 0; i < keys.length; i++) {
                    gui.setItem(9 + i, keys[i]);
                }

                player.openInventory(gui);
                return true;
            } else {
                main.getLogger().info("You need to be a player to use this command!");
            }
        }

        return false;
    }
}
