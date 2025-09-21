package com.elementalaffinity.listener;

import com.elementalaffinity.item.ItemManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ItemAbilitiesListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        ItemStack itemInHand = damager.getInventory().getItemInMainHand();

        if (itemInHand.isSimilar(ItemManager.FIRE_SWORD)) {
            event.getEntity().setFireTicks(200); // 10 seconds
        } else if (itemInHand.isSimilar(ItemManager.EARTH_HAMMER)) {
            for (Entity entity : damager.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof LivingEntity && entity != event.getEntity()) {
                    ((LivingEntity) entity).damage(5.0, damager);
                    Vector direction = entity.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
                    entity.setVelocity(entity.getVelocity().add(direction));
                }
            }
        } else if (itemInHand.isSimilar(ItemManager.VOID_SCYTHE)) {
            damager.teleport(event.getEntity().getLocation());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.isSimilar(ItemManager.WATER_TRIDENT)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
            player.sendMessage("You feel the healing power of water.");
        } else if (itemInHand.isSimilar(ItemManager.LIGHTNING_STAFF)) {
            player.getWorld().strikeLightning(player.getTargetBlock(null, 100).getLocation());
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (event.getBow().isSimilar(ItemManager.AIR_BOW)) {
            Arrow arrow = (Arrow) event.getProjectile();
            arrow.setVelocity(arrow.getVelocity().multiply(1.5));
            arrow.setKnockbackStrength(arrow.getKnockbackStrength() + 1);
        }
    }
}
