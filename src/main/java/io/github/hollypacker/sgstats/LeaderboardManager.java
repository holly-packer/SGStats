package io.github.hollypacker.sgstats;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class LeaderboardManager {

    private final Scoreboard scoreboard;
    private Objective objective;
    private static LeaderboardManager instance;

    public LeaderboardManager() {
        instance = this;
        Component title = MiniMessage.miniMessage().deserialize(SGStats.getInstance().getConfig().getString("messages" +
                ".scoreboard-title"));
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        objective = scoreboard.getObjective("sgstats_points");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("sgstats_points", "dummy", title);
        }
        objective.displayName(title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScoreboardName() {
        Component title = MiniMessage.miniMessage().deserialize(SGStats.getInstance().getConfig().getString("messages" +
                ".scoreboard-title"));
        objective.displayName(title);
    }

    public void setPoints(String name, int points) {
        objective.getScore(name).setScore(points);
    }

    public int getPoints(String name) {
        return objective.getScore(name).getScore();
    }

    public void removePlayer(String name) {
        objective.getScore(name).resetScore();
    }

    public static LeaderboardManager getInstance() {
        return instance;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
