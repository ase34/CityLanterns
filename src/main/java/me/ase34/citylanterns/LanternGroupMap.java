package me.ase34.citylanterns;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;

public class LanternGroupMap extends AbstractMap<String, LanternGroup> {

    private HashMap<String, LanternGroup> delegate;
    private LanternGroup defaultGroup;

    public LanternGroupMap() {
        this.defaultGroup = null;
        this.delegate = new HashMap<String, LanternGroup>();
    }

    public LanternGroupMap(LanternGroup defaultGroup) {
        this.defaultGroup = defaultGroup;
        this.delegate = new HashMap<String, LanternGroup>();
    }

    @Override
    public Set<java.util.Map.Entry<String, LanternGroup>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public LanternGroup get(Object key) {
        LanternGroup group = super.get(key);

        if (group == null) {
            group = defaultGroup;
            group.setName((String) key);

            put((String) key, group);
        }

        return group;
    }

    @Override
    public LanternGroup put(String key, LanternGroup value) {
        return delegate.put(key, value);
    }

    public LanternGroup getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(LanternGroup defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

}
