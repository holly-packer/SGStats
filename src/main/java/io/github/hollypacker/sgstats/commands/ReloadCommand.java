package io.github.hollypacker.sgstats.commands;

import io.github.hollypacker.sgstats.LeaderboardManager;
import io.github.hollypacker.sgstats.SGStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        SGStats.getInstance().reloadConfig();
        LeaderboardManager.getInstance().updateScoreboardName();
        SGStats.getInstance().sendMessage("reloaded-config", sender);
        return false;
    }
}
