package io.github.hollypacker.sgstats;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Statistics {

    private static Statistics instance;

    public Statistics() {
        instance = this;
    }

    public static Statistics getInstance() {
        return instance;
    }

    public void addPoints(Player player, int points) {
        addPoints(player.getName(), points, true);
    }

    public void addPoints(String player, int points, boolean broadcast) {
        int currentPoints = LeaderboardManager.getInstance().getPoints(player);
        LeaderboardManager.getInstance().setPoints(player, currentPoints + points);
        SGStats.getInstance().getLogger().info("DEBUG: Added " + (currentPoints + points) + " points to " + player);
        // Should we broadcast the points?
        FileConfiguration config = SGStats.getInstance().getConfig();
        if (config.getBoolean("broadcast-points") && broadcast) {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                SGStats.getInstance().sendMessage("add-point", player1);
            }
        }
    }
}
