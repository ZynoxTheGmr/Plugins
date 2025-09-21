package com.elementalaffinity.listener;

import com.elementalaffinity.element.Element;
import com.elementalaffinity.player.PlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ClashListener implements Listener {

    private final PlayerManager playerManager;
    private final double damageMultiplier = 1.25; // 25% more damage

    public ClashListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        Element attackerElement = playerManager.getPlayerElement(attacker);
        Element victimElement = playerManager.getPlayerElement(victim);

        if (attackerElement == null || victimElement == null) {
            return;
        }

        // Elemental Clashes
        if (isStronger(attackerElement, victimElement)) {
            event.setDamage(event.getDamage() * damageMultiplier);
            attacker.sendMessage("Your " + attackerElement.name() + " powers are strong against " + victimElement.name() + "!");
            victim.sendMessage("You took extra damage from a " + attackerElement.name() + " user!");
        }

        // Weaknesses
        if (victimElement == Element.LIGHTNING && victim.getWorld().isThundering()) {
            event.setDamage(event.getDamage() * damageMultiplier);
            victim.sendMessage("You feel weaker during a storm!");
        }
    }

    private boolean isStronger(Element attacker, Element victim) {
        if (attacker == Element.WATER && victim == Element.FIRE) return true;
        if (attacker == Element.EARTH && victim == Element.LIGHTNING) return true;
        if (attacker == Element.AIR && victim == Element.EARTH) return true;
        if (attacker == Element.LIGHTNING && victim == Element.WATER) return true;
        return false;
    }
}
