package me.ase34.citylanterns.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import me.ase34.citylanterns.LanternGroup;
import me.ase34.citylanterns.LanternGroupMap;

public abstract class GroupSQLStorage implements GroupStorage {
    
    protected String table;
    
    public GroupSQLStorage(String table) {
        this.table = table;
    }

    public abstract Connection getConnection() throws Exception;

    protected abstract void createTables() throws Exception;
    
    @Override
    public void save(LanternGroupMap groups) throws Exception {
        LanternGroupMap old = load();
        
        for (String groupname: groups.keySet()) {
            LanternGroup group = groups.get(groupname);
            if (old.remove(groupname) != null) {
                PreparedStatement pstmt = getConnection().prepareStatement(
                        "UPDATE `" + table + "` SET `daytime`= ?, `nighttime` = ?, " +
                		"`thundering` = ? WHERE `name` = ? ");
                pstmt.setString(4, group.getName());
                pstmt.setLong(1, group.getDaytime());
                pstmt.setLong(2, group.getNighttime());
                pstmt.setBoolean(3, group.isThunder());
                
                pstmt.execute();
            } else {
                PreparedStatement pstmt = getConnection().prepareStatement(
                        "INSERT INTO `" + table + "` VALUES (?, ?, ?, ?)");
                pstmt.setString(1, group.getName());
                pstmt.setLong(2, group.getDaytime());
                pstmt.setLong(3, group.getNighttime());
                pstmt.setBoolean(4, group.isThunder());
                
                pstmt.execute();
            }
        }
        
        for (LanternGroup groupToRemove : old.values()) {
            PreparedStatement pstmt = getConnection().prepareStatement(
                    "DELETE FROM `" + table + "` WHERE `name` = ?");
            pstmt.setString(1, groupToRemove.getName());
            
            pstmt.execute();
        }
    }

    @Override
    public LanternGroupMap load() throws Exception {
        createTables();
        
        LanternGroupMap groups = new LanternGroupMap();
        
        Statement stmt = getConnection().createStatement();        
        ResultSet rs = stmt.executeQuery("SELECT * FROM `" + table + "`");
        
        while (rs.next()) {
            String name = rs.getString("name");
            long daytime = rs.getLong("daytime");
            long nighttime = rs.getLong("nighttime");
            boolean thunder = rs.getBoolean("thundering");
            
            groups.put(name, new LanternGroup(name, daytime, nighttime, thunder));
        }
        
        return groups;
    }


}
