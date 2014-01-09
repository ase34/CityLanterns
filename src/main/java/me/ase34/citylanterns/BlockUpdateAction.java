package me.ase34.citylanterns;

import net.minecraft.server.v1_7_R1.World;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;

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
        int x = blockLocation.getBlockX();
        int y = blockLocation.getBlockY();
        int z = blockLocation.getBlockZ();
        
        World w = ((CraftWorld) blockLocation.getWorld()).getHandle();
        w.setTypeAndData(x, y, z, CraftMagicNumbers.getBlock(newBlockMaterial), 0, 2);
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
