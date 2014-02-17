package me.ase34.citylanterns.storage;

import java.io.File;

import me.ase34.citylanterns.LanternGroup;
import me.ase34.citylanterns.LanternGroupMap;

import org.bukkit.configuration.file.YamlConfiguration;

public class GroupFileStorage implements GroupStorage {

    private File file;

    public GroupFileStorage(File file) {
        this.file = file;
    }

    @Override
    public void save(LanternGroupMap groups) throws Exception {
        YamlConfiguration config = new YamlConfiguration();
        for (LanternGroup group : groups.values()) {
            String name = group.getName();
            config.set(name + ".day_time", group.getDaytime());
            config.set(name + ".night_time", group.getNighttime());
            config.set(name + ".lamps_on_thundering", group.isThunder());
        }

        config.save(file);
    }

    @Override
    public LanternGroupMap load() throws Exception {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        LanternGroupMap groups = new LanternGroupMap();

        for (String groupname : config.getKeys(false)) {
            long daytime = config.getLong(groupname + ".day_time");
            long nighttime = config.getLong(groupname + ".night_time");
            boolean thunder = config.getBoolean(groupname + ".lamps_on_thundering");

            groups.put(groupname, new LanternGroup(groupname, daytime, nighttime, thunder));
        }

        return groups;
    }

}
