package io.github.hollypacker.sgstats.listeners;

import com.thundergemios10.survivalgames.Game;
import com.thundergemios10.survivalgames.GameManager;
import com.thundergemios10.survivalgames.api.PlayerKilledEvent;
import io.github.hollypacker.sgstats.SGStats;
import io.github.hollypacker.sgstats.Statistics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class KillListener implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerKilledEvent event) {
        Player killer = event.getKiller();
        Player victim = event.getPlayer();
        // Get the game
        Game game = event.getGame();
        // Get the remaining players
        int remainingPlayers = game.getActivePlayers();
        // If the killer isn't null, give them points
        if (killer != null) {
            Statistics.getInstance().addPoints(killer, SGStats.getInstance().getConfig().getInt("points-per-kill"));
        }
        // Calculate points for the player
        int points = 24 - remainingPlayers;
        // Add those points to the player
        Statistics.getInstance().addPoints(victim, points);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        // Get the game ID
        int gameId = GameManager.getInstance().getPlayerGameId(event.getPlayer());
        if (gameId == -1) return;
        // Check for the game
        Game game = GameManager.getInstance().getGame(gameId);
        if (game == null) return;
        // Calculate points
        int points = 24 - game.getActivePlayers();
        // Add those points to the player
        Statistics.getInstance().addPoints(event.getPlayer(), points);
    }
}
