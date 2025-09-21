package com.elementalaffinity.gui;

import com.elementalaffinity.element.Element;
import com.elementalaffinity.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    private final PlayerManager playerManager;

    public GuiListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Choose your Element")) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Element chosenElement = null;

        switch (clickedItem.getType()) {
            case LAVA_BUCKET:
                chosenElement = Element.FIRE;
                break;
            case WATER_BUCKET:
                chosenElement = Element.WATER;
                break;
            case GRASS_BLOCK:
                chosenElement = Element.EARTH;
                break;
            case FEATHER:
                chosenElement = Element.AIR;
                break;
            case NETHER_STAR:
                chosenElement = Element.LIGHTNING;
                break;
            case ENDER_PEARL:
                chosenElement = Element.VOID;
                break;
            default:
                break;
        }

        if (chosenElement != null) {
            playerManager.setPlayerElement(player, chosenElement);
            player.closeInventory();
            player.sendMessage("You have chosen the " + chosenElement.name() + " element!");
            if (chosenElement == Element.AIR) {
                player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(18.0);
                player.setHealth(18.0);
            } else {
                player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            }
        }
    }
}
