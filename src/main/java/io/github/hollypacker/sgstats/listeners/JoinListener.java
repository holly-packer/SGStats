package io.github.hollypacker.sgstats.listeners;

import io.github.hollypacker.sgstats.LeaderboardManager;
import io.github.hollypacker.sgstats.SGStats;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(SGStats.getInstance(), () ->
                event.getPlayer().setScoreboard(LeaderboardManager.getInstance().getScoreboard()), 1);
    }
}
