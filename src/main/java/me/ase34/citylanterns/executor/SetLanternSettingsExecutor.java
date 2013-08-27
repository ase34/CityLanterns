package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SetLanternSettingsExecutor implements CommandExecutor {

    private CityLanterns plugin;

    public SetLanternSettingsExecutor(CityLanterns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }
        
        String group = (args.length > 2) ? args[0] : "main";
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("groups." + group);
        
        if (section == null) {
            sender.sendMessage(ChatColor.GOLD + "This group does not exist!");
        }
        
        String key = (args.length > 2) ? args[1] : args[0];
        String value = (args.length > 2) ? args[2] : args[1];
        
        if (key.equals("on")) {
            long time = getTime(sender, value);
            if (time == -1) {
                return true;
            }
            
            section.set("night_time", time);
            sender.sendMessage(ChatColor.GOLD + "Time for toggling on set to: " + ChatColor.WHITE + time);
        } else if (key.equals("off")) {
            long time = getTime(sender, value);
            if (time == -1) {
                return true;
            }
            
            section.set("day_time", time);
            sender.sendMessage(ChatColor.GOLD + "Time for toggling off set to: " + ChatColor.WHITE + time);
        } else if (key.equals("thunder")) {
            boolean bool = Boolean.parseBoolean(value);
            section.set("lamps_on_thundering", bool);
            sender.sendMessage(ChatColor.GOLD + "Lanterns " + ChatColor.WHITE + (bool ? "will" : "won't" )
                    + ChatColor.GOLD + " toggle on while thunder!");
        }
        
        plugin.saveConfig();
        return true;
    }

    public long getTime(CommandSender sender, String value) {
        long time;
        if (value.equals("now")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.GOLD + "The value " + ChatColor.GRAY + "now"
                        + ChatColor.GOLD + " requires that you are in a world!");
                return -1;
            }
            time = ((Player)sender).getWorld().getTime() % 24000;
        } else {
            try {
                time = Long.parseLong(value) % 24000;
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.GOLD + "The value " + ChatColor.GRAY
                        + value + ChatColor.GOLD + " needs to be a number!");
                return -1;
            }
        }
        return time;
    }

}
