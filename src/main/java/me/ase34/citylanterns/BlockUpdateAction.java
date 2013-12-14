package me.ase34.citylanterns;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;

public class BlockUpdateAction {

    private Location blockLocation;
    private Material newBlockMaterial;
    
    public BlockUpdateAction(Location blockLocation, Material newBlockMaterial) {
        super();
        this.blockLocation = blockLocation;
        this.newBlockMaterial = newBlockMaterial;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }
    
    public Material getNewBlockMaterial() {
        return newBlockMaterial;
    }
    
    public void execute() {
        blockLocation.getBlock().setType(newBlockMaterial);
        net.minecraft.server.v1_7_R1.World w = ((CraftWorld) blockLocation.getWorld()).getHandle();
        w.t(blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BlockUpdateAction) {
            BlockUpdateAction action = (BlockUpdateAction) o;
            return action.blockLocation.equals(this.blockLocation) && action.newBlockMaterial == this.newBlockMaterial;
        }
        return false;
    }
    
    
}
