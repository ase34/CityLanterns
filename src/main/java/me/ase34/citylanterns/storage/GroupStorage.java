package me.ase34.citylanterns.storage;

import me.ase34.citylanterns.LanternGroupMap;

public interface GroupStorage {

    public abstract void save(LanternGroupMap groups) throws Exception;

    public abstract LanternGroupMap load() throws Exception;

}
