package io.github.hollypacker.sgstats.commands;

import io.github.hollypacker.sgstats.LeaderboardManager;
import io.github.hollypacker.sgstats.SGStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StopTrackingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (args.length < 1) {
            SGStats.getInstance().sendMessage("specify-name", sender);
            return false;
        }
        // Stop tracking the player
        LeaderboardManager.getInstance().removePlayer(args[0]);
        SGStats.getInstance().sendMessage("stop-tracking", sender, new SGStats.Placeholder("{player}", args[0]));
        return false;
    }
}
