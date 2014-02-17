package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;
import me.ase34.citylanterns.LanternGroup;

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

    private void showDetails(CommandSender sender, String groupnname) {
        int size = plugin.getLanterns().getLanterns(groupnname).size();

        LanternGroup group = plugin.getGroups().get(groupnname);
        long daytime = group.getDaytime();
        long nighttime = group.getNighttime();
        boolean thunder = group.isThunder();

        sender.sendMessage(ChatColor.GOLD + "Group " + ChatColor.GRAY + groupnname + ChatColor.GOLD + ": ");
        sender.sendMessage(ChatColor.GOLD + "Count - " + ChatColor.WHITE + size);
        sender.sendMessage(ChatColor.GOLD + "Daytime - " + ChatColor.WHITE + daytime);
        sender.sendMessage(ChatColor.GOLD + "Nighttime - " + ChatColor.WHITE + nighttime);
        sender.sendMessage(ChatColor.GOLD + "Thunder - " + ChatColor.WHITE + thunder);
    }

    private void showOverview(CommandSender sender) {
        for (String group : plugin.getLanterns().getGroups()) {
            int size = plugin.getLanterns().getLanterns(group).size();
            sender.sendMessage(ChatColor.GOLD + "Group " + ChatColor.GRAY + group + ChatColor.GOLD + ": "
                    + ChatColor.WHITE + size + ChatColor.GOLD + " Lanterns");
        }
    }

}
