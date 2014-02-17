package me.ase34.citylanterns;

import java.util.Comparator;

import org.bukkit.entity.Player;

public class LanternToPlayerDistanceComparator implements Comparator<BlockUpdateAction> {

    @Override
    public int compare(BlockUpdateAction action1, BlockUpdateAction action2) {
        if (!action1.getBlockLocation().getWorld().equals(action2.getBlockLocation().getWorld()))
            return 0;

        double distance1 = 0;
        double distance2 = 0;

        for (Player player : action1.getBlockLocation().getWorld().getPlayers()) {
            if (distance1 == 0)
                distance1 = player.getLocation().distanceSquared(action1.getBlockLocation());
            if (distance2 == 0)
                distance2 = player.getLocation().distanceSquared(action2.getBlockLocation());

            distance1 = Math.min(player.getLocation().distanceSquared(action1.getBlockLocation()), distance1);
            distance2 = Math.min(player.getLocation().distanceSquared(action2.getBlockLocation()), distance2);
        }

        return (int) (distance1 - distance2);
    }

}
