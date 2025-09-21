package com.elementalaffinity.listener;

import com.elementalaffinity.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ProgressionListener implements Listener {

    private final PlayerManager playerManager;

    public ProgressionListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            playerManager.addXp(killer, 20); // 20 XP for a kill
            killer.sendMessage("You gained 20 XP for killing " + victim.getName());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        if (blockType == Material.DIAMOND_ORE || blockType == Material.EMERALD_ORE) {
            playerManager.addXp(player, 10); // 10 XP for mining
            player.sendMessage("You gained 10 XP for mining a valuable ore.");
        }
    }
}
