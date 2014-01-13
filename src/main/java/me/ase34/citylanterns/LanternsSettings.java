package me.ase34.citylanterns;

import java.io.File;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class LanternsSettings {
    
    private Plugin plugin;
    private long lastModified = 0;

    public LanternsSettings(Plugin plugin) {
        this.plugin = plugin;
    }

    public long getDaytime(String group) {
        return getSection(group).getLong("day_time");
    }
    
    public long getNighttime(String group) {
        return getSection(group).getLong("night_time");
    }
    
    public boolean onThunder(String group) {
        return getSection(group).getBoolean("lamps_on_thundering");
    }
    
    public void setDaytime(String group, long daytime) {
        getSection(group).set("day_time", daytime);
        plugin.saveConfig();
    }
    
    public void setNighttime(String group, long daytime) {
        getSection(group).set("night_time", daytime);
        plugin.saveConfig();
    }
    
    public void setThunder(String group, boolean thunder) {
        getSection(group).set("lamps_on_thundering", thunder);
        plugin.saveConfig();
    }

    private ConfigurationSection getSection(String group) {
        Configuration config = getConfig();
        
        ConfigurationSection section = config.getConfigurationSection("groups." + group);
        if (section == null) {
            section = config.createSection("groups." + group);
            section.set("lamps_on_thundering", config.getBoolean("lamps_on_thundering"));
            section.set("night_time", config.getLong("night_time"));
            section.set("day_time", config.getLong("day_time"));
            plugin.saveConfig();
        }
        return section;
    }

    private Configuration getConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        long lastModified = file.lastModified();
        if (lastModified > this.lastModified) {
            this.lastModified = lastModified;
            plugin.reloadConfig();
        }
        
        return plugin.getConfig();
    }
    
}
