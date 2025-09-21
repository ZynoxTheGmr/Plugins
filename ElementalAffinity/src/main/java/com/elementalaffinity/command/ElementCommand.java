package com.elementalaffinity.command;

import com.elementalaffinity.element.Element;
import com.elementalaffinity.item.ItemManager;
import com.elementalaffinity.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ElementCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public ElementCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /element <choose|info|level|reset|giveitem>");
            return true;
        }

        if (args[0].equalsIgnoreCase("choose")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            Player player = (Player) sender;
            if (playerManager.hasElement(player)) {
                player.sendMessage("You have already chosen an element.");
                return true;
            }
            new com.elementalaffinity.gui.ElementGui().open(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            Player player = (Player) sender;
            Element element = playerManager.getPlayerElement(player);
            if (element == null) {
                player.sendMessage("You don't have an element. Use /element choose to pick one.");
                return true;
            }
            player.sendMessage("Your Element: " + element.name());
            player.sendMessage("Level: " + playerManager.getPlayerLevel(player));
            // You can add more detailed info about powers and weaknesses here.
            return true;
        }

        if (args[0].equalsIgnoreCase("level")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            Player player = (Player) sender;
            Element element = playerManager.getPlayerElement(player);
            if (element == null) {
                player.sendMessage("You don't have an element. Use /element choose to pick one.");
                return true;
            }
            int level = playerManager.getPlayerLevel(player);
            int xp = playerManager.getPlayerXp(player);
            int requiredXp = level * 100;
            player.sendMessage("Level: " + level);
            player.sendMessage("XP: " + xp + " / " + requiredXp);
            return true;
        }

        if (args[0].equalsIgnoreCase("reset")) {
            if (!sender.hasPermission("element.reset")) {
                sender.sendMessage("You don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage("Usage: /element reset <player>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            playerManager.setPlayerElement(target, null);
            playerManager.setPlayerLevel(target, 1);
            playerManager.setPlayerXp(target, 0);
            target.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            sender.sendMessage("You have reset " + target.getName() + "'s element.");
            target.sendMessage("Your element has been reset by an admin.");
            return true;
        }

        if (args[0].equalsIgnoreCase("giveitem")) {
            if (!sender.hasPermission("element.admin")) {
                sender.sendMessage("You don't have permission to use this command.");
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage("Usage: /element giveitem <player> <item>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            String itemName = args[2].toLowerCase();
            ItemStack item = null;
            switch (itemName) {
                case "fire_sword":
                    item = ItemManager.FIRE_SWORD;
                    break;
                case "water_trident":
                    item = ItemManager.WATER_TRIDENT;
                    break;
                case "earth_hammer":
                    item = ItemManager.EARTH_HAMMER;
                    break;
                case "air_bow":
                    item = ItemManager.AIR_BOW;
                    break;
                case "lightning_staff":
                    item = ItemManager.LIGHTNING_STAFF;
                    break;
                case "void_scythe":
                    item = ItemManager.VOID_SCYTHE;
                    break;
                default:
                    sender.sendMessage("Unknown item: " + itemName);
                    return true;
            }
            target.getInventory().addItem(item);
            sender.sendMessage("You have given " + target.getName() + " a " + itemName + ".");
            return true;
        }

        return true;
    }
}
