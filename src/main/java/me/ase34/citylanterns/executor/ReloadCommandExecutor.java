package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommandExecutor implements CommandExecutor {

    private CityLanterns plugin;
    
    public ReloadCommandExecutor(CityLanterns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean force = args.length > 0 ? args[0].equalsIgnoreCase("force") : false;
        
        try {
            if (!force) {
                plugin.saveLanters();
                plugin.saveGroups();
            }
            
            plugin.loadLanterns();
            plugin.loadGroups();
            if (force) {
                sender.sendMessage(ChatColor.GOLD + "Lanterns and groups loaded and overwritten!");
            } else {
                sender.sendMessage(ChatColor.GOLD + "Lanterns and groups saved and reloaded!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return true;
    }

}
