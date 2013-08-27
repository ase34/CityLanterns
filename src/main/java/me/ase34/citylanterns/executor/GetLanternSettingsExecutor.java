package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class GetLanternSettingsExecutor implements CommandExecutor {

    private CityLanterns plugin;
    
    public GetLanternSettingsExecutor(CityLanterns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String group = (args.length > 0) ? args[0] : "main";
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("groups." + group);
        
        if (section == null) {
            sender.sendMessage(ChatColor.GOLD + "This group does not exist!");
            return true;
        }
        
        sender.sendMessage(ChatColor.GOLD + "Settings for group " + ChatColor.GRAY + group + ChatColor.GOLD + ":");
        sender.sendMessage(ChatColor.GOLD + "Time on toggling off: " + ChatColor.WHITE + section.getLong("day_time"));
        sender.sendMessage(ChatColor.GOLD + "Time on toggling on: " + ChatColor.WHITE + section.getLong("night_time"));
        sender.sendMessage(ChatColor.GOLD + "Should lanterns toggle on while thunder?: " + ChatColor.WHITE + section.getBoolean("lamps_on_thundering"));
        return true;
    }

}
