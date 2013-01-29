package me.ase34.citylanterns.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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
        for (Location loc : lanterns) {
            dos.writeInt(loc.getWorld().getName().getBytes().length);
            dos.write(loc.getWorld().getName().getBytes());
            dos.writeInt(loc.getBlockX());
            dos.writeInt(loc.getBlockY());
            dos.writeInt(loc.getBlockZ());
        }
        dos.close();
        fos.close();
    }

    public List<Location> load() throws Exception {
        List<Location> lanterns = new ArrayList<Location>();
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        while (dis.available() > 0) {
            byte[] worldname = new byte[dis.readInt()];
            dis.read(worldname);
            World world = Bukkit.getWorld(new String(worldname));
            int x = dis.readInt();
            int y = dis.readInt();
            int z = dis.readInt();
            lanterns.add(new Location(world, x, y, z));
        }
        dis.close();
        fis.close();
        return lanterns;
    }

}
