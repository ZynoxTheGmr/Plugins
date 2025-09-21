package com.elementalaffinity.player;

import com.elementalaffinity.data.PlayerDataManager;
import com.elementalaffinity.element.Element;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final PlayerDataManager dataManager;
    private final Map<UUID, PlayerData> playerDataCache = new HashMap<>();

    public PlayerManager(PlayerDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void loadPlayerData(Player player) {
        Element element = dataManager.getElement(player);
        int level = dataManager.getLevel(player);
        int xp = dataManager.getXp(player);
        playerDataCache.put(player.getUniqueId(), new PlayerData(element, level, xp));
    }

    public void unloadPlayerData(Player player) {
        playerDataCache.remove(player.getUniqueId());
    }

    public void setPlayerElement(Player player, Element element) {
        PlayerData data = getPlayerData(player);
        if (data != null) {
            data.setElement(element);
            dataManager.setElement(player, element);
        }
    }

    public Element getPlayerElement(Player player) {
        PlayerData data = getPlayerData(player);
        return data != null ? data.getElement() : null;
    }

    public boolean hasElement(Player player) {
        return getPlayerElement(player) != null;
    }

    public int getPlayerLevel(Player player) {
        PlayerData data = getPlayerData(player);
        return data != null ? data.getLevel() : 1;
    }

    public void setPlayerLevel(Player player, int level) {
        PlayerData data = getPlayerData(player);
        if (data != null) {
            data.setLevel(level);
            dataManager.setLevel(player, level);
        }
    }

    public int getPlayerXp(Player player) {
        PlayerData data = getPlayerData(player);
        return data != null ? data.getXp() : 0;
    }

    public void setPlayerXp(Player player, int xp) {
        PlayerData data = getPlayerData(player);
        if (data != null) {
            data.setXp(xp);
            dataManager.setXp(player, xp);
        }
    }

    private PlayerData getPlayerData(Player player) {
        return playerDataCache.get(player.getUniqueId());
    }

    public void addXp(Player player, int amount) {
        PlayerData data = getPlayerData(player);
        if (data == null || data.getElement() == null) {
            return;
        }

        int currentXp = data.getXp();
        int newXp = currentXp + amount;
        int currentLevel = data.getLevel();
        int requiredXp = currentLevel * 100;

        if (newXp >= requiredXp) {
            int newLevel = currentLevel + 1;
            if (newLevel <= 5) {
                data.setLevel(newLevel);
                data.setXp(newXp - requiredXp);
                dataManager.setLevel(player, newLevel);
                dataManager.setXp(player, newXp - requiredXp);
                player.sendMessage("Congratulations! You have reached level " + newLevel + " in the " + data.getElement().name() + " element!");
            }
        } else {
            data.setXp(newXp);
            dataManager.setXp(player, newXp);
        }
    }
}
