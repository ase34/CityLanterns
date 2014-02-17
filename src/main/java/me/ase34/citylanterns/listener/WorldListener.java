package me.ase34.citylanterns.listener;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldListener implements Listener {

    private CityLanterns plugin;

    public WorldListener(CityLanterns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLoadWorld(WorldLoadEvent ev) throws Exception {
        plugin.getLogger().info("World '" + ev.getWorld().getName() + "' loaded! Reloading lanterns");
        plugin.loadLanterns();
    }

}
