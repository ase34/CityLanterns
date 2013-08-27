package me.ase34.citylanterns;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SelectingPlayer {

    private String playerName;
    private String group;
    
    public SelectingPlayer(String playerName, String group) {
        this.playerName = playerName;
        this.group = group;
    }
    
    public SelectingPlayer(Player player) {
        this(player, "main");
    }
    
    public SelectingPlayer(Player player, String group) {
        this(player.getName(), group);
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayerExact(playerName);
    }
    
    public String getGroup() {
        return group;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SelectingPlayer)) {
            return false;
        }
        SelectingPlayer other = (SelectingPlayer) obj;
        if (!group.equals(other.group)) {
            return false;
        }
        if (!playerName.equals(other.playerName)) {
            return false;
        }
        return true;
    }
    
}
