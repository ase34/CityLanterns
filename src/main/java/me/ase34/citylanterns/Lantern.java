package me.ase34.citylanterns;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Lantern {
    
    private Location lantern;
    private String group;
    
    public Lantern(Location lantern, String group) {
        this.lantern = lantern;
        this.group = group;
    }
    
    public Lantern(Location lantern) {
        this(lantern, "main");
    }

    public Block getLanternBlock() {
        return lantern.getBlock();
    }

    public String getGroup() {
        return group;
    }

    public Location getLocation() {
        return lantern;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((lantern == null) ? 0 : lantern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Lantern)) {
            return false;
        }
        Lantern other = (Lantern) obj;
        if (!group.equals(other.group)) {
            return false;
        }
        if (!lantern.equals(other.lantern)) {
            return false;
        }
        return true;
    }
    
    
}
