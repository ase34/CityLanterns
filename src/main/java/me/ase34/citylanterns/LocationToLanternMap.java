package me.ase34.citylanterns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    
    public Set<String> getGroups() {
        HashSet<String> set = new HashSet<String>();
        for (Lantern lantern: this) {
            set.add(lantern.getGroup());
        }
        return set;
    }
    
    public Set<Lantern> getLanterns(String group) {
        HashSet<Lantern> set = new HashSet<Lantern>();
        for (Lantern lantern: this) {
            if (lantern.getGroup().equals(group)) {
                set.add(lantern);
            }
        }
        return set;
    }
    
}
