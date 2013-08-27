package me.ase34.citylanterns.storage;

import java.util.List;

import me.ase34.citylanterns.Lantern;

public interface LanternStorage {

    public abstract void save(List<Lantern> lanterns) throws Exception;

    public abstract List<Lantern> load() throws Exception;

}
