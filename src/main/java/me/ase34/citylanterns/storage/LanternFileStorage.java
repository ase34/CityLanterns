package me.ase34.citylanterns.storage;

import java.io.BufferedInputStream;
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

import me.ase34.citylanterns.Lantern;
import me.ase34.citylanterns.LocationToLanternMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LanternFileStorage implements LanternStorage {

    public static final String magicNumber = "CL1";

    private File file;

    public LanternFileStorage(File file) {
        this.file = file;
    }

    public void save(LocationToLanternMap lanterns) throws Exception {
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);

        dos.write(magicNumber.getBytes());

        Map<String, List<Lantern>> groups = mapToGroup(lanterns);
        for (String group : groups.keySet()) {
            dos.writeUTF(group);

            Map<UUID, List<Location>> worlds = mapToWorld(lanterns);
            dos.writeInt(worlds.size());
            for (UUID uid : worlds.keySet()) {
                dos.writeLong(uid.getMostSignificantBits());
                dos.writeLong(uid.getLeastSignificantBits());
                dos.writeInt(worlds.get(uid).size());
                for (Location loc : worlds.get(uid)) {
                    dos.writeInt(loc.getBlockX());
                    dos.writeInt(loc.getBlockY());
                    dos.writeInt(loc.getBlockZ());
                }
            }
        }
        dos.close();
        fos.close();
    }

    private Map<UUID, List<Location>> mapToWorld(List<Lantern> lanterns) {
        Map<UUID, List<Location>> map = new HashMap<UUID, List<Location>>();
        for (Lantern lantern : lanterns) {
            Location loc = lantern.getLocation();
            UUID uid = loc.getWorld().getUID();
            if (!map.containsKey(uid)) {
                map.put(uid, new ArrayList<Location>());
            }
            map.get(uid).add(loc);
        }
        return map;
    }

    private Map<String, List<Lantern>> mapToGroup(List<Lantern> lanterns) {
        Map<String, List<Lantern>> map = new HashMap<String, List<Lantern>>();
        for (Lantern lantern : lanterns) {
            String group = lantern.getGroup();
            if (!map.containsKey(group)) {
                map.put(group, new ArrayList<Lantern>());
            }
            map.get(group).add(lantern);
        }
        return map;
    }

    public LocationToLanternMap load() throws Exception {
        file.createNewFile();

        LocationToLanternMap lanterns = new LocationToLanternMap();
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);

        boolean skipFirst = false;
        dis.mark(4);
        if (dis.available() >= 3) {
            byte[] chars = new byte[3];
            dis.read(chars);

            if (!new String(chars).equals(magicNumber)) {
                skipFirst = true;
                dis.reset();
            }
        }

        while (dis.available() > 0) {
            String group;
            int worlds;
            if (!skipFirst) {
                group = dis.readUTF();
                worlds = dis.readInt();
            } else {
                group = "main";
                worlds = 1;
            }

            for (int i = 0; i < worlds; i++) {
                long msbs = dis.readLong();
                long lsbs = dis.readLong();
                UUID uid = new UUID(msbs, lsbs);
                int locs = dis.readInt();
                World world = Bukkit.getWorld(uid);

                for (int j = 0; j < locs; j++) {
                    int x = dis.readInt();
                    int y = dis.readInt();
                    int z = dis.readInt();
                    lanterns.add(new Lantern(new Location(world, x, y, z), group));
                }
            }
        }

        dis.close();
        fis.close();
        return lanterns;
    }

}
