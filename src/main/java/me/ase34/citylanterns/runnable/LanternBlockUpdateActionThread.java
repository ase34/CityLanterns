package me.ase34.citylanterns.runnable;

import me.ase34.citylanterns.BlockUpdateAction;
import me.ase34.citylanterns.CityLanterns;

public class LanternBlockUpdateActionThread implements Runnable {

    private CityLanterns plugin;
    
    public LanternBlockUpdateActionThread(CityLanterns plugin) {
        super();
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        BlockUpdateAction action = plugin.getBlockUpdateQueue().poll();
        if (action == null) {
            return;
        }
        action.getBlockLocation().getBlock().setType(action.getNewBlockMaterial());
    }

}
