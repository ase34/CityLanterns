package me.ase34.citylanterns;

import java.util.ArrayList;

import org.bukkit.Location;

@SuppressWarnings("serial")
public class LocationToLanternMap extends ArrayList<Lantern> {

    @Override
    public Lantern set(int index, Lantern element) {
        checkInsert(index, element);
        return super.set(index, element);
    }

    public void checkInsert(int index, Lantern element) {
        for (int i = 0; index < size() && i < size(); i++) {
            if (i != index && this.get(i).getLocation().equals(element.getLocation())) {
                throw new IllegalArgumentException(
                        "LocationToLanternMap does not allow two lanterns at the same location");
            }
        }
    }

    @Override
    public void add(int index, Lantern element) {
        checkInsert(index, element);
        super.add(index, element);
    }

    public Lantern getLantern(Location loc) {
        for (Lantern lantern: this) {
            if (loc.equals(lantern.getLocation())) {
                return lantern;
            }
        }
        return null;
    }
    
}
