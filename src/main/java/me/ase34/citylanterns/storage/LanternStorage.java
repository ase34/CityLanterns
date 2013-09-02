package me.ase34.citylanterns.storage;

import me.ase34.citylanterns.LocationToLanternMap;

public interface LanternStorage {

    public abstract void save(LocationToLanternMap lanterns) throws Exception;

    public abstract LocationToLanternMap load() throws Exception;

}
