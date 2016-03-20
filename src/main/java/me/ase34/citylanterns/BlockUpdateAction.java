package me.ase34.citylanterns;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.Location;
import org.bukkit.Material;

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
        Block lantern = blockLocation.getWorld().getBlockAt(blockLocation);
        Block above = lantern.getRelative(BlockFace.UP, 1);
        BlockState aboveState = above.getState();

        above.setType(Material.REDSTONE_BLOCK);
        lantern.setType(newBlockMaterial);
        aboveState.update(true, false);
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
