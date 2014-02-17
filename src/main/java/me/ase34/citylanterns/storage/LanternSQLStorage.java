package me.ase34.citylanterns.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.ase34.citylanterns.Lantern;
import me.ase34.citylanterns.LocationToLanternMap;

public abstract class LanternSQLStorage implements LanternStorage {

    protected String table;

    public LanternSQLStorage(String table) {
        this.table = table;
    }

    public abstract Connection getConnection() throws Exception;

    protected abstract void createTables() throws Exception;

    @Override
    public void save(LocationToLanternMap lanterns) throws Exception {
        LocationToLanternMap old = load();

        for (Lantern lantern : lanterns) {
            if (old.remove(lantern)) {
                continue;
            }

            PreparedStatement pstmt = getConnection().prepareStatement(
                "INSERT INTO `" + table + "` VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, lantern.getLocation().getWorld().getName());
            pstmt.setInt(2, lantern.getLocation().getBlockX());
            pstmt.setInt(3, lantern.getLocation().getBlockY());
            pstmt.setInt(4, lantern.getLocation().getBlockZ());
            pstmt.setString(5, lantern.getGroup());

            pstmt.execute();
        }

        for (Lantern lanternToRemove : old) {
            PreparedStatement pstmt = getConnection().prepareStatement(
                "DELETE FROM `" + table + "` WHERE `world` = ? AND `x` = ? "
                        + "AND `y` = ? AND `z` = ? AND `group` = ?");
            pstmt.setString(1, lanternToRemove.getLocation().getWorld().getName());
            pstmt.setInt(2, lanternToRemove.getLocation().getBlockX());
            pstmt.setInt(3, lanternToRemove.getLocation().getBlockY());
            pstmt.setInt(4, lanternToRemove.getLocation().getBlockZ());
            pstmt.setString(5, lanternToRemove.getGroup());

            pstmt.execute();
        }
    }

    @Override
    public LocationToLanternMap load() throws Exception {
        createTables();

        LocationToLanternMap lanterns = new LocationToLanternMap();

        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM `" + table + "`");

        while (rs.next()) {
            World world = Bukkit.getWorld(rs.getString("world"));
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int z = rs.getInt("z");
            String group = rs.getString("group");

            lanterns.add(new Lantern(new Location(world, x, y, z), group));
        }

        return lanterns;
    }

}
