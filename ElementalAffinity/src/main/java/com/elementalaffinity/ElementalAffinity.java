package com.elementalaffinity;

import com.elementalaffinity.command.ElementCommand;
import com.elementalaffinity.element.Element;
import com.elementalaffinity.gui.GuiListener;
import com.elementalaffinity.listener.AbilitiesListener;
import com.elementalaffinity.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.elementalaffinity.data.PlayerDataManager;
import com.elementalaffinity.listener.PlayerConnectionListener;
import com.elementalaffinity.item.ItemManager;
import com.elementalaffinity.listener.ItemAbilitiesListener;
import com.elementalaffinity.listener.ProgressionListener;

public class ElementalAffinity extends JavaPlugin {

    private PlayerManager playerManager;
    private PlayerDataManager dataManager;

    @Override
    public void onEnable() {
        this.dataManager = new PlayerDataManager(this);
        this.playerManager = new PlayerManager(dataManager);
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(playerManager), this);
        this.getCommand("element").setExecutor(new ElementCommand(playerManager));
        getServer().getPluginManager().registerEvents(new GuiListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new AbilitiesListener(playerManager, this), this);
        getServer().getPluginManager().registerEvents(new com.elementalaffinity.listener.ClashListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new ProgressionListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new ItemAbilitiesListener(), this);

        ItemManager.init(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Element element = playerManager.getPlayerElement(player);
                    if (element == null) {
                        continue;
                    }

                    applyPassiveEffects(player, element);
                }
            }
        }.runTaskTimer(this, 0, 20);


        getLogger().info("ElementalAffinity has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ElementalAffinity has been disabled!");
    }

    private void applyPassiveEffects(Player player, Element element) {
        // Passives
        switch (element) {
            case FIRE:
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 21, 0, false, false));
                break;
            case WATER:
                if (player.isInWater()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 21, 0, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 21, 0, false, false));
                }
                break;
            case EARTH:
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 21, 0, false, false));
                break;
            case AIR:
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 21, 1, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 21, 0, false, false));
                break;
            case LIGHTNING:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 21, 1, false, false));
                break;
        }

        // Weaknesses
        switch (element) {
            case WATER:
                String biome = player.getLocation().getBlock().getBiome().name();
                if (biome.contains("DESERT") || biome.contains("NETHER")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 21, 0, false, false));
                }
                break;
            case EARTH:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 21, 0, false, false));
                break;
            case VOID:
                if (player.getHealth() > 1) {
                    player.setHealth(player.getHealth() - 0.5);
                }
                break;
        }
    }
}
