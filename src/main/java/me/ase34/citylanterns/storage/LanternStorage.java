package me.ase34.citylanterns.storage;

import java.util.List;

import org.bukkit.Location;

public interface LanternStorage {

    public abstract void save(List<Location> lanterns) throws Exception;

    public abstract List<Location> load() throws Exception;

}
