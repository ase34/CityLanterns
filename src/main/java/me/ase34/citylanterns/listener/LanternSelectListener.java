package me.ase34.citylanterns.listener;

import me.ase34.citylanterns.CityLanterns;
import me.ase34.citylanterns.Lantern;
import me.ase34.citylanterns.SelectingPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LanternSelectListener implements Listener {

    private CityLanterns plugin;

    public LanternSelectListener(CityLanterns plugin) {
        super();
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent ev) {
        if (ev.isBlockInHand()) {
            return;
        }
        
        SelectingPlayer player = null;
        for (SelectingPlayer selPlayer: plugin.getSelectingPlayers()) {
            if (selPlayer.getPlayerName().equals(ev.getPlayer().getName())) {
                player = selPlayer;
                break;
            }
        }
        
        if (player != null) {
            if (ev.getClickedBlock() == null) {
                return;
            }
            if (ev.getClickedBlock().getType() != Material.REDSTONE_LAMP_OFF && ev.getClickedBlock().getType() != Material.REDSTONE_LAMP_ON) {
                ev.getPlayer().sendMessage(ChatColor.GOLD + "This is not a redstone lamp!");
                return;
            }
            
            Lantern lantern = new Lantern(ev.getClickedBlock().getLocation(), player.getGroup());
            if (plugin.getLanterns().contains(lantern)) {
                plugin.getLanterns().remove(lantern);
                ev.getPlayer().sendMessage(ChatColor.GOLD + "Removed this lantern from group "
                        + ChatColor.GRAY + player.getGroup() + ChatColor.GOLD + "!");
            } else {
                plugin.getLanterns().add(lantern);
                ev.getPlayer().sendMessage(ChatColor.GOLD + "Added this lantern to group "
                        + ChatColor.GRAY + player.getGroup() + ChatColor.GOLD + "!");
            }
            ev.setCancelled(true);
        }
    }
}
