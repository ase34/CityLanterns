package me.ase34.citylanterns.listener;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class LanternRedstoneListener implements Listener {

    private CityLanterns plugin;
    
    private static final Material LAMP_OFF = Material.REDSTONE_LAMP_OFF;
    private static final Material LAMP_ON = Material.REDSTONE_LAMP_ON;
    
    public LanternRedstoneListener(CityLanterns plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onRedstoneChange(BlockRedstoneEvent ev) {
        Location loc = ev.getBlock().getLocation();
        if (plugin.getLanterns().contains(loc)) {
            if (loc.getBlock().getType() == LAMP_ON) {
                ev.setNewCurrent(13);
            } else if (loc.getBlock().getType() == LAMP_OFF) {
                ev.setNewCurrent(0);
            }
        }
    }
}
