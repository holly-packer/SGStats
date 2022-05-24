package io.github.hollypacker.sgstats.listeners;

import com.thundergemios10.survivalgames.Game;
import com.thundergemios10.survivalgames.GameManager;
import io.github.hollypacker.sgstats.SGStats;
import io.github.hollypacker.sgstats.Statistics;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

public class KillListener implements Listener {

    @EventHandler
    public void onPlayerKill(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (victim.getHealth() > event.getFinalDamage()) return;
        if (event.getDamager() instanceof Arrow arrow) {
            ProjectileSource source = arrow.getShooter();
            if (!(source instanceof Player killer)) return;
            handleEntityDeath(victim, killer);
        } else {
            if (!(event.getDamager() instanceof Player killer)) return;
            handleEntityDeath(victim, killer);
        }
    }

    @EventHandler
    public void onDumbDie(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            return;
        }

        if (!(event.getEntity() instanceof Player victim)) return;

        if (victim.getHealth() > event.getFinalDamage()) return;

        handleEntityDeath(victim, null);
    }

    private void handleEntityDeath(Player victim, Player killer) {
        // Get the game
        int gameId = GameManager.getInstance().getPlayerGameId(victim);
        if (gameId == -1) return;
        // Check for the game
        Game game = GameManager.getInstance().getGame(gameId);
        if (game == null) return;

        if (game.getMode() != Game.GameMode.INGAME) return;
        // Extinguish the player
        victim.setFireTicks(0);
        // Get the remaining players
        int remainingPlayers = game.getActivePlayers();
        // If the killer isn't null, give them points
        if (killer != null) {
            Statistics.getInstance().addPoints(killer, SGStats.getInstance().getConfig().getInt("points-per-kill"));
        }
        // Calculate points for the player
        int points = game.getAllPlayers().size() - remainingPlayers + 1;
        // Add those points to the player
        Statistics.getInstance().addPoints(victim, points);
        // If the remaining players is equal to zero, give full points
        SGStats.getInstance().getLogger().info("Remaining players: " + remainingPlayers);
        if (remainingPlayers == 2) {
            Player winner = getWinner(game, victim);
            if (winner == null) throw new IllegalStateException("No winner found, wtf??");
            Statistics.getInstance().addPoints(winner, game.getAllPlayers().size());
        }
    }

    private Player getWinner(Game game, Player victim) {
        for (Player player : game.getAllPlayers()) {
            if (game.isPlayerActive(player) && victim != player) return player;
        }
        return null;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        // Get the game ID
        int gameId = GameManager.getInstance().getPlayerGameId(event.getPlayer());
        if (gameId == -1) return;
        // Check for the game
        Game game = GameManager.getInstance().getGame(gameId);
        if (game == null) return;
        if (game.getMode() != Game.GameMode.INGAME) return;
        // Calculate points
        int points = game.getAllPlayers().size() - game.getActivePlayers() + 1;
        // Add those points to the player
        Statistics.getInstance().addPoints(event.getPlayer(), points);
    }
}
