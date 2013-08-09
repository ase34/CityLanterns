package me.ase34.citylanterns.runnable;

import me.ase34.citylanterns.BlockUpdateAction;
import me.ase34.citylanterns.CityLanterns;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class LanternUpdateThread implements Runnable {

    private static final Material LAMP_OFF = Material.REDSTONE_LAMP_OFF;
    private static final Material LAMP_ON = Material.REDSTONE_LAMP_ON;
    
    private CityLanterns plugin;

    public LanternUpdateThread(CityLanterns plugin) {
        super();
        this.plugin = plugin;
    }

    public void run() {
        for (int i = 0; i < plugin.getLanterns().size(); i++) {
            Location loc = plugin.getLanterns().get(i);
            Block block = loc.getBlock();
            if (block.getType() != LAMP_ON && block.getType() != LAMP_OFF) {
                plugin.getLanterns().remove(i);
                plugin.getBlockUpdateQueue().remove(new BlockUpdateAction(loc, LAMP_ON));
                plugin.getBlockUpdateQueue().remove(new BlockUpdateAction(loc, LAMP_OFF));
                i--;
                continue;
            }
            
            if (loc.getWorld().isThundering() && plugin.getConfig().getBoolean("lamps_on_thundering")) {
                addUpdateAction(block, true);
            } else if (loc.getWorld().getTime() % 24000 >= plugin.getConfig().getLong("night_time")) {
                addUpdateAction(block, true);
            } else if (loc.getWorld().getTime() % 24000 >= plugin.getConfig().getLong("day_time")) {
                addUpdateAction(block, false);
            }
        }
    }
    
    private void addUpdateAction(Block block, boolean state) {
    	Material type = state ? LAMP_ON : LAMP_OFF;
    	
    	if (plugin.getBlockUpdateQueue().contains(new BlockUpdateAction(block.getLocation(), type))) {
    		return;
    	}
    	
    	if (block.getType() != type) {
    		plugin.getBlockUpdateQueue().offer(new BlockUpdateAction(block.getLocation(), type));
    	}
    }

}
