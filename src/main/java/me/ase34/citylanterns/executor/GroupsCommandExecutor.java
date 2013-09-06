package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GroupsCommandExecutor implements CommandExecutor {
    
    private CityLanterns plugin;

    public GroupsCommandExecutor(CityLanterns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            showDetails(sender, args[0]);
        } else {
            showOverview(sender);
        }
        
        return true;
    }

    private void showDetails(CommandSender sender, String group) {
        int size = plugin.getLanterns().getLanterns(group).size();
        long daytime = plugin.getSettings().getDaytime(group);
        long nighttime = plugin.getSettings().getNighttime(group);
        boolean thunder = plugin.getSettings().onThunder(group);
        
        sender.sendMessage(ChatColor.GOLD + "Group " + ChatColor.GRAY + 
                group + ChatColor.GOLD + ": ");
        sender.sendMessage(ChatColor.GOLD + "Count - " + ChatColor.WHITE + size);
        sender.sendMessage(ChatColor.GOLD + "Daytime - " + ChatColor.WHITE + daytime);
        sender.sendMessage(ChatColor.GOLD + "Nighttime - " + ChatColor.WHITE + nighttime);
        sender.sendMessage(ChatColor.GOLD + "Thunder - " + ChatColor.WHITE + thunder);
    }

    private void showOverview(CommandSender sender) {
        for (String group: plugin.getLanterns().getGroups()) {
            int size = plugin.getLanterns().getLanterns(group).size();
            sender.sendMessage(ChatColor.GOLD + "Group " + ChatColor.GRAY + 
                    group + ChatColor.GOLD + ": " + ChatColor.WHITE + size + 
                    ChatColor.GOLD + " Lanterns");
        }
    }
    
}
