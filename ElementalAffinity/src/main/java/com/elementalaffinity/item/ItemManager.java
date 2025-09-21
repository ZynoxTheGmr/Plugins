package com.elementalaffinity.item;

import com.elementalaffinity.ElementalAffinity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ItemManager {

    public static ItemStack FIRE_SWORD;
    public static ItemStack WATER_TRIDENT;
    public static ItemStack EARTH_HAMMER;
    public static ItemStack AIR_BOW;
    public static ItemStack LIGHTNING_STAFF;
    public static ItemStack VOID_SCYTHE;

    public static void init(ElementalAffinity plugin) {
        createFireSword(plugin);
        createWaterTrident(plugin);
        createEarthHammer(plugin);
        createAirBow(plugin);
        createLightningStaff(plugin);
        createVoidScythe(plugin);
    }

    private static void createFireSword(ElementalAffinity plugin) {
        FIRE_SWORD = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = FIRE_SWORD.getItemMeta();
        meta.setDisplayName("§cFire Sword");
        meta.setLore(Collections.singletonList("A sword forged in the heart of a volcano."));
        FIRE_SWORD.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "fire_sword"), FIRE_SWORD);
        recipe.shape(" B ", "BSB", " B ");
        recipe.setIngredient('S', Material.NETHERITE_SWORD);
        recipe.setIngredient('B', Material.BLAZE_ROD);
        Bukkit.addRecipe(recipe);
    }

    private static void createWaterTrident(ElementalAffinity plugin) {
        WATER_TRIDENT = new ItemStack(Material.TRIDENT);
        ItemMeta meta = WATER_TRIDENT.getItemMeta();
        meta.setDisplayName("§9Water Trident");
        meta.setLore(Collections.singletonList("A trident that commands the power of the ocean."));
        WATER_TRIDENT.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "water_trident"), WATER_TRIDENT);
        recipe.shape(" H ", "HTH", " H ");
        recipe.setIngredient('T', Material.TRIDENT);
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
        Bukkit.addRecipe(recipe);
    }

    private static void createEarthHammer(ElementalAffinity plugin) {
        EARTH_HAMMER = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = EARTH_HAMMER.getItemMeta();
        meta.setDisplayName("§2Earth Hammer");
        meta.setLore(Collections.singletonList("A hammer that can shake the very foundations of the world."));
        EARTH_HAMMER.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "earth_hammer"), EARTH_HAMMER);
        recipe.shape(" O ", "OAO", " O ");
        recipe.setIngredient('A', Material.NETHERITE_AXE);
        recipe.setIngredient('O', Material.OBSIDIAN);
        Bukkit.addRecipe(recipe);
    }

    private static void createAirBow(ElementalAffinity plugin) {
        AIR_BOW = new ItemStack(Material.BOW);
        ItemMeta meta = AIR_BOW.getItemMeta();
        meta.setDisplayName("§fAir Bow");
        meta.setLore(Collections.singletonList("A bow that shoots arrows with the speed of the wind."));
        AIR_BOW.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "air_bow"), AIR_BOW);
        recipe.shape(" F ", "FBF", " F ");
        recipe.setIngredient('B', Material.BOW);
        recipe.setIngredient('F', Material.FEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void createLightningStaff(ElementalAffinity plugin) {
        LIGHTNING_STAFF = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = LIGHTNING_STAFF.getItemMeta();
        meta.setDisplayName("§eLightning Staff");
        meta.setLore(Collections.singletonList("A staff that can call down the fury of a storm."));
        LIGHTNING_STAFF.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "lightning_staff"), LIGHTNING_STAFF);
        recipe.shape(" N ", "NBN", " N ");
        recipe.setIngredient('B', Material.BLAZE_ROD);
        recipe.setIngredient('N', Material.NETHER_STAR);
        Bukkit.addRecipe(recipe);
    }

    private static void createVoidScythe(ElementalAffinity plugin) {
        VOID_SCYTHE = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta meta = VOID_SCYTHE.getItemMeta();
        meta.setDisplayName("§5Void Scythe");
        meta.setLore(Collections.singletonList("A scythe that can cut through the fabric of reality."));
        VOID_SCYTHE.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "void_scythe"), VOID_SCYTHE);
        recipe.shape(" E ", "EHE", " E ");
        recipe.setIngredient('H', Material.NETHERITE_HOE);
        recipe.setIngredient('E', Material.ENDER_PEARL);
        Bukkit.addRecipe(recipe);
    }
}
