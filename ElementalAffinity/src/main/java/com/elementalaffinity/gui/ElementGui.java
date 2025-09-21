package com.elementalaffinity.gui;

import com.elementalaffinity.element.Element;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class ElementGui {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Choose your Element");

        ItemStack fire = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta fireMeta = fire.getItemMeta();
        fireMeta.setDisplayName("§cFire");
        fireMeta.setLore(Collections.singletonList("Click to choose the Fire element."));
        fire.setItemMeta(fireMeta);

        ItemStack water = new ItemStack(Material.WATER_BUCKET);
        ItemMeta waterMeta = water.getItemMeta();
        waterMeta.setDisplayName("§9Water");
        waterMeta.setLore(Collections.singletonList("Click to choose the Water element."));
        water.setItemMeta(waterMeta);

        ItemStack earth = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta earthMeta = earth.getItemMeta();
        earthMeta.setDisplayName("§2Earth");
        earthMeta.setLore(Collections.singletonList("Click to choose the Earth element."));
        earth.setItemMeta(earthMeta);

        ItemStack air = new ItemStack(Material.FEATHER);
        ItemMeta airMeta = air.getItemMeta();
        airMeta.setDisplayName("§fAir");
        airMeta.setLore(Collections.singletonList("Click to choose the Air element."));
        air.setItemMeta(airMeta);

        ItemStack lightning = new ItemStack(Material.NETHER_STAR);
        ItemMeta lightningMeta = lightning.getItemMeta();
        lightningMeta.setDisplayName("§eLightning");
        lightningMeta.setLore(Collections.singletonList("Click to choose the Lightning element."));
        lightning.setItemMeta(lightningMeta);

        ItemStack aVoid = new ItemStack(Material.ENDER_PEARL);
        ItemMeta voidMeta = aVoid.getItemMeta();
        voidMeta.setDisplayName("§5Void");
        voidMeta.setLore(Collections.singletonList("Click to choose the Void element."));
        aVoid.setItemMeta(voidMeta);

        gui.setItem(0, fire);
        gui.setItem(1, water);
        gui.setItem(2, earth);
        gui.setItem(3, air);
        gui.setItem(4, lightning);
        gui.setItem(5, aVoid);

        player.openInventory(gui);
    }
}
