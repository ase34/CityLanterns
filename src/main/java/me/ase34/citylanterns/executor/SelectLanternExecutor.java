package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectLanternExecutor implements CommandExecutor {

    private CityLanterns plugin;

    public SelectLanternExecutor(CityLanterns cityLanterns) {
        plugin = cityLanterns;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Please use this command in-game!");
            return true;
        }
        Player player = (Player) sender;
        if (plugin.getSelectingPlayers().contains(player.getName())) {
            plugin.getSelectingPlayers().remove(player.getName());
            player.sendMessage(ChatColor.GOLD + "You left the lantern-selection-mode!");
        } else {
            plugin.getSelectingPlayers().add(player.getName());
            player.sendMessage(ChatColor.GOLD + "You entered the lantern-selection-mode!");
        }
        return true;
    }

}
