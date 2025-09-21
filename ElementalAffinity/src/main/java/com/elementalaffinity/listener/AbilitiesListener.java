package com.elementalaffinity.listener;

import com.elementalaffinity.element.Element;
import com.elementalaffinity.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilitiesListener implements Listener {

    private final PlayerManager playerManager;
    private final com.elementalaffinity.ElementalAffinity plugin;
    private final HashMap<UUID, Long> lightningCooldown = new HashMap<>();

    public AbilitiesListener(PlayerManager playerManager, com.elementalaffinity.ElementalAffinity plugin) {
        this.playerManager = playerManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking() || !event.getAction().name().contains("RIGHT_CLICK")) {
            return;
        }

        Element element = playerManager.getPlayerElement(player);
        if (element == null) {
            return;
        }

        int level = playerManager.getPlayerLevel(player);

        // Active abilities
        switch (element) {
            case FIRE:
                handleFire(player, level);
                break;
            case WATER:
                handleWater(player, level);
                break;
            case EARTH:
                handleEarth(player, level);
                break;
            case AIR:
                handleAir(player, level);
                break;
            case LIGHTNING:
                handleLightning(player, level);
                break;
            case VOID:
                if (event.getItem() == null) { // Blink with empty hand
                    handleVoidBlink(player);
                } else { // Shadow Cloak with item in hand
                    handleVoidCloak(player, level);
                }
                break;
        }
    }

    private void handleFire(Player player, int level) {
        player.getWorld().createExplosion(player.getLocation(), 2.0f + 0.5f * level, true);
        player.sendMessage("You used Flame Burst!");
    }

    private void handleWater(Player player, int level) {
        Vector direction = player.getLocation().getDirection().multiply(2 + 0.5 * level);
        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (entity instanceof LivingEntity) {
                entity.setVelocity(entity.getVelocity().add(direction));
            }
        }
        player.sendMessage("You used Tidal Wave!");
    }

    private void handleEarth(Player player, int level) {
        Location loc = player.getLocation();
        List<Block> wall = new ArrayList<>();
        int width = 5 + level;
        int height = 3 + level / 2;
        for (int i = -width/2; i <= width/2; i++) {
            for (int j = 0; j < height; j++) {
                Block block = loc.clone().add(loc.getDirection().setY(0).normalize().multiply(2)).add(i, j, 0).getBlock();
                if (block.getType() == Material.AIR) {
                    block.setType(Material.STONE);
                    wall.add(block);
                }
            }
        }
        player.sendMessage("You used Rock Wall!");
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                for (Block block : wall) {
                    block.setType(Material.AIR);
                }
            }
        }.runTaskLater(plugin, 100L); // 5 seconds
    }

    private void handleAir(Player player, int level) {
        player.setVelocity(player.getVelocity().add(new Vector(0, 1.5 + 0.2 * level, 0)));
        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (entity instanceof LivingEntity) {
                Vector direction = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.0 + 0.2 * level);
                entity.setVelocity(entity.getVelocity().add(direction));
            }
        }
        player.sendMessage("You used Gust!");
    }

    private void handleLightning(Player player, int level) {
        long cooldownTime = 10 - level; // in seconds
        if (lightningCooldown.containsKey(player.getUniqueId())) {
            long secondsLeft = ((lightningCooldown.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                player.sendMessage("You must wait " + secondsLeft + " seconds before using Lightning Strike again.");
                return;
            }
        }

        Block targetBlock = player.getTargetBlock(null, 100);
        if (targetBlock.getType() != Material.AIR) {
            player.getWorld().strikeLightning(targetBlock.getLocation());
            player.sendMessage("You used Lightning Strike!");
            lightningCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }

    private void handleVoidBlink(Player player) {
        Location location = player.getLocation();
        Vector direction = location.getDirection();
        Location destination = location.add(direction.multiply(5));
        player.teleport(destination);
        player.sendMessage("You blinked forward.");
    }

    private void handleVoidCloak(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200 + 40 * level, 0));
        player.sendMessage("You used Shadow Cloak!");
    }
}
