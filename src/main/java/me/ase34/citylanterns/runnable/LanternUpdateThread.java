package me.ase34.citylanterns.runnable;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.Location;
import org.bukkit.Material;

public class LanternUpdateThread implements Runnable {

    private CityLanterns plugin;

    public LanternUpdateThread(CityLanterns plugin) {
        super();
        this.plugin = plugin;
    }

    public void run() {
        for (int i = 0; i < plugin.getLanterns().size(); i++) {
            Location loc = plugin.getLanterns().get(i);
            if (loc.getBlock().getType() != Material.REDSTONE_LAMP_ON
                    && loc.getBlock().getType() != Material.REDSTONE_LAMP_OFF) {
                plugin.getLanterns().remove(i);
                i--;
                continue;
            }
            if (loc.getWorld().getTime() % 24000 >= plugin.getConfig().getLong("night_time")) {
                if (loc.getBlock().getType() != Material.REDSTONE_LAMP_ON) {
                    loc.getBlock().setType(Material.REDSTONE_LAMP_ON);
                }
            } else if (loc.getWorld().getTime() % 24000 >= plugin.getConfig().getLong("day_time")) {
                if (loc.getBlock().getType() != Material.REDSTONE_LAMP_OFF) {
                    loc.getBlock().setType(Material.REDSTONE_LAMP_OFF);
                }
            }
        }
    }

}
