package me.ase34.citylanterns.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LanternFileStorage implements LanternStorage {

    private File file;

    public LanternFileStorage(File file) {
        this.file = file;
    }

    public void save(List<Location> lanterns) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        
        Map<UUID, List<Location>> map = orderByWorld(lanterns);
        
        for (UUID uid: map.keySet()) {
            dos.writeLong(uid.getMostSignificantBits());
            dos.writeLong(uid.getLeastSignificantBits());
            dos.writeInt(map.get(uid).size());
            for (Location loc: map.get(uid)) {
                dos.writeInt(loc.getBlockX());
                dos.writeInt(loc.getBlockY());
                dos.writeInt(loc.getBlockZ());
            }
        }
        
        dos.close();
        fos.close();
    }
    
    private Map<UUID, List<Location>> orderByWorld(List<Location> locs) {
        Map<UUID, List<Location>> map = new HashMap<UUID, List<Location>>();
        for (Location loc: locs) {
            UUID uid = loc.getWorld().getUID();
            if (!map.containsKey(uid)) {
                map.put(uid, new ArrayList<Location>());
            }
            map.get(uid).add(loc);
        }
        return map;
    }

    public List<Location> load() throws Exception {
        List<Location> lanterns = new ArrayList<Location>();
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        
        while (dis.available() > 0) {
            long msbs = dis.readLong();
            long lsbs = dis.readLong();
            UUID uid = new UUID(msbs, lsbs);
            int size = dis.readInt();
            World world = Bukkit.getWorld(uid);
            
            for (int i = 0; i < size; i++) {
                int x = dis.readInt();
                int y = dis.readInt();
                int z = dis.readInt();
                lanterns.add(new Location(world, x, y, z));
            }
        }
        
        dis.close();
        fis.close();
        return lanterns;
    }

}
