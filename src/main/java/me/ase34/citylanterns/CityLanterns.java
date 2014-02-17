package me.ase34.citylanterns;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.logging.Level;

import me.ase34.citylanterns.executor.GroupsCommandExecutor;
import me.ase34.citylanterns.executor.ReloadCommandExecutor;
import me.ase34.citylanterns.executor.SelectCommandExecutor;
import me.ase34.citylanterns.executor.SettingsComandExecutor;
import me.ase34.citylanterns.listener.LanternRedstoneListener;
import me.ase34.citylanterns.listener.LanternSelectListener;
import me.ase34.citylanterns.listener.WorldListener;
import me.ase34.citylanterns.runnable.LanternBlockUpdateActionThread;
import me.ase34.citylanterns.runnable.LanternUpdateThread;
import me.ase34.citylanterns.storage.GroupFileStorage;
import me.ase34.citylanterns.storage.GroupMySQLStorage;
import me.ase34.citylanterns.storage.GroupSQLiteStorage;
import me.ase34.citylanterns.storage.GroupStorage;
import me.ase34.citylanterns.storage.LanternFileStorage;
import me.ase34.citylanterns.storage.LanternMySQLStorage;
import me.ase34.citylanterns.storage.LanternSQLiteStorage;
import me.ase34.citylanterns.storage.LanternStorage;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class CityLanterns extends JavaPlugin {

    private LocationToLanternMap lanterns;
    private LanternGroupMap groups;

    private LanternStorage lanternStorage;
    private GroupStorage groupStorage;

    private PriorityQueue<BlockUpdateAction> blockUpdateQueue;

    @Override
    public void onDisable() {
        try {
            saveLanters();
            saveGroups();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getLogger().info(getDescription().getFullName() + " by ase34 disabled!");
    }

    @Override
    public void onEnable() {
        try {
            getDataFolder().mkdir();
            saveDefaultConfig();

            loadGroups();
            loadLanterns();

            blockUpdateQueue = new PriorityQueue<BlockUpdateAction>(Math.max(lanterns.size(), 1),
                    new LanternToPlayerDistanceComparator());

            getCommand("citylanternsselect").setExecutor(new SelectCommandExecutor(this));
            getCommand("citylanternsgroups").setExecutor(new GroupsCommandExecutor(this));
            getCommand("citylanternssettings").setExecutor(new SettingsComandExecutor(this));
            getCommand("citylanternsreload").setExecutor(new ReloadCommandExecutor(this));

            getServer().getPluginManager().registerEvents(new LanternSelectListener(this), this);
            getServer().getPluginManager().registerEvents(new LanternRedstoneListener(this), this);
            getServer().getPluginManager().registerEvents(new WorldListener(this), this);

            getServer().getScheduler().scheduleSyncRepeatingTask(this, new LanternUpdateThread(this), 0, 1);
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new LanternBlockUpdateActionThread(this), 0,
                getConfig().getInt("toggle_delay"));

            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            } catch (IOException e) {
                getServer().getLogger().log(Level.WARNING, "Submitting plugin metrics failed: ", e);
            }

            getLogger().info(getDescription().getFullName() + " by ase34 enabled!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LocationToLanternMap getLanterns() {
        return lanterns;
    }

    public PriorityQueue<BlockUpdateAction> getBlockUpdateQueue() {
        return blockUpdateQueue;
    }

    public void loadLanterns() throws Exception {
        setupLanternStorage();

        lanterns = lanternStorage.load();
    }

    public void saveLanters() throws Exception {
        setupLanternStorage();

        lanternStorage.save(lanterns);
    }

    private void setupLanternStorage() {
        reloadConfig();

        String storageType = getConfig().getString("lanternsstorage.type");
        if (storageType.equalsIgnoreCase("file")) {
            File storageFile = new File(getDataFolder(), getConfig().getString("lanternsstorage.filepath"));
            lanternStorage = new LanternFileStorage(storageFile);
        } else if (storageType.equals("sqlite")) {
            File sqliteFile = new File(getDataFolder(), getConfig().getString("lanternsstorage.sqlitepath"));
            String table = getConfig().getString("lanternsstorage.sqlitetable");
            lanternStorage = new LanternSQLiteStorage(sqliteFile, table);
        } else if (storageType.equals("mysql")) {
            String url = getConfig().getString("lanternsstorage.mysqlurl");
            String table = getConfig().getString("lanternsstorage.mysqltable");
            lanternStorage = new LanternMySQLStorage(url, table);
        } else {
            throw new IllegalArgumentException(String.format("The lantern storage type '%s' is not supported!",
                storageType));
        }
    }

    public void loadGroups() throws Exception {
        setupGroupStorage();

        groups = groupStorage.load();

        long daytime = getConfig().getLong("day_time");
        long nighttime = getConfig().getLong("night_time");
        boolean thunder = getConfig().getBoolean("lamps_on_thundering");
        groups.setDefaultGroup(new LanternGroup(null, daytime, nighttime, thunder));
    }

    public void saveGroups() throws Exception {
        setupGroupStorage();

        groupStorage.save(groups);
    }

    private void setupGroupStorage() {
        reloadConfig();

        String storageType = getConfig().getString("groupsstorage.type");
        if (storageType.equalsIgnoreCase("file")) {
            File storageFile = new File(getDataFolder(), getConfig().getString("groupsstorage.filepath"));
            groupStorage = new GroupFileStorage(storageFile);
        } else if (storageType.equals("sqlite")) {
            File sqliteFile = new File(getDataFolder(), getConfig().getString("groupsstorage.sqlitepath"));
            String table = getConfig().getString("groupsstorage.sqlitetable");
            groupStorage = new GroupSQLiteStorage(sqliteFile, table);
        } else if (storageType.equals("mysql")) {
            String url = getConfig().getString("groupsstorage.mysqlurl");
            String table = getConfig().getString("groupsstorage.mysqltable");
            groupStorage = new GroupMySQLStorage(url, table);
        } else {
            throw new IllegalArgumentException(String.format("The group storage type '%s' is not supported!",
                storageType));
        }
    }

    public LanternGroupMap getGroups() {
        return groups;
    }

}
