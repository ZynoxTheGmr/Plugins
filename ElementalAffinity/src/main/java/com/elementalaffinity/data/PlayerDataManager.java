package com.elementalaffinity.data;

import com.elementalaffinity.ElementalAffinity;
import com.elementalaffinity.element.Element;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDataManager {

    private final ElementalAffinity plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public PlayerDataManager(ElementalAffinity plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "playerdata.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config to " + this.configFile);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "playerdata.yml");
        if (!this.configFile.exists()) {
            this.plugin.saveResource("playerdata.yml", false);
        }
    }

    public void setElement(Player player, Element element) {
        getConfig().set("players." + player.getUniqueId() + ".element", element.name());
        saveConfig();
    }

    public Element getElement(Player player) {
        String elementString = getConfig().getString("players." + player.getUniqueId() + ".element");
        if (elementString == null) {
            return null;
        }
        return Element.valueOf(elementString);
    }

    public void setLevel(Player player, int level) {
        getConfig().set("players." + player.getUniqueId() + ".level", level);
        saveConfig();
    }

    public int getLevel(Player player) {
        return getConfig().getInt("players." + player.getUniqueId() + ".level", 1);
    }

    public void setXp(Player player, int xp) {
        getConfig().set("players." + player.getUniqueId() + ".xp", xp);
        saveConfig();
    }

    public int getXp(Player player) {
        return getConfig().getInt("players." + player.getUniqueId() + ".xp", 0);
    }
}
