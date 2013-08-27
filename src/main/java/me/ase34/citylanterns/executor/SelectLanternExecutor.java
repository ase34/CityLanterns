package me.ase34.citylanterns.executor;

import me.ase34.citylanterns.CityLanterns;
import me.ase34.citylanterns.SelectingPlayer;

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
        SelectingPlayer selPlayer;
        
        if (args.length > 0) {
            removePlayer(player);
            selPlayer = new SelectingPlayer(player, args[0]);
        } else {
            if (removePlayer(player)) {
                player.sendMessage(ChatColor.GOLD + "You left the lantern-selection-mode!");
                return true;
            } else {
                selPlayer = new SelectingPlayer(player);
            }
        }
        
        plugin.getSelectingPlayers().add(selPlayer);
        player.sendMessage(ChatColor.GOLD + "You entered the lantern-selection-mode for the group " 
                + ChatColor.GRAY + selPlayer.getGroup() + ChatColor.GOLD + "!");
        return true;
    }
    
    private boolean removePlayer(Player player) {
        for (SelectingPlayer selPlayer: plugin.getSelectingPlayers()) {
            if (selPlayer.getPlayerName().equals(player.getName())) {
                plugin.getSelectingPlayers().remove(selPlayer);
                return true;
            }
        }
        return false;
    }

}
