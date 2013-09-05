package me.ase34.citylanterns.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.ase34.citylanterns.CityLanterns;
import me.ase34.citylanterns.Lantern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LanternSelectListener implements Listener {

    public static final Pattern pattern = Pattern.compile("Lantern Selection Tool for group '(.+)'");
    
    private CityLanterns plugin;

    public LanternSelectListener(CityLanterns plugin) {
        super();
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent ev) {
        ItemStack tool = ev.getItem();
        if (tool == null || !tool.hasItemMeta()) {
            return;
        }
        
        Matcher m = pattern.matcher(tool.getItemMeta().getDisplayName());
        if (!m.matches()) {
            return;
        }
        if (!ev.getPlayer().hasPermission("citylanterns.select")) {
            return;
        }
        if (ev.getClickedBlock() == null) {
            return;
        }
        if (ev.getClickedBlock().getType() != Material.REDSTONE_LAMP_OFF && ev.getClickedBlock().getType() != Material.REDSTONE_LAMP_ON) {
            ev.getPlayer().sendMessage(ChatColor.GOLD + "This is not a redstone lamp!");
            ev.setCancelled(true);
            return;
        }
        
        String group = m.group(1);
        Lantern selected = plugin.getLanterns().getLantern(ev.getClickedBlock().getLocation());
        
        if (ev.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (selected != null && selected.getGroup().equals(group)) {
                plugin.getLanterns().remove(selected);
                selected.getLanternBlock().setType(Material.REDSTONE_LAMP_OFF);
                ev.getPlayer().sendMessage(ChatColor.GOLD + "Removed this lantern from group "
                        + ChatColor.GRAY + selected.getGroup() + ChatColor.GOLD + "!");
            } else {
                if (selected == null) {
                    Lantern lantern = new Lantern(ev.getClickedBlock().getLocation(), group);
                    plugin.getLanterns().add(lantern);
                } else {
                    selected.setGroup(group);
                }
                ev.getPlayer().sendMessage(ChatColor.GOLD + "Added this lantern to group "
                        + ChatColor.GRAY + group + ChatColor.GOLD + "!");
            }
        } else {
            if (selected == null) {
                ev.getPlayer().sendMessage(ChatColor.GOLD + "This lantern currently is in no group!");
            } else {
                ev.getPlayer().sendMessage(ChatColor.GOLD + "This lantern currently is in group "
                        + ChatColor.GRAY + selected.getGroup() + ChatColor.GOLD + "!");
            }
        }
        ev.setCancelled(true);
        
    }
}
