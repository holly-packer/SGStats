package io.github.hollypacker.sgstats.commands;

import io.github.hollypacker.sgstats.SGStats;
import io.github.hollypacker.sgstats.Statistics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TrackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        if (args.length < 1) {
            SGStats.getInstance().sendMessage("specify-name", sender);
            return false;
        }
        // Start tracking the player
        Statistics.getInstance().addPoints(args[0], 0, false);
        SGStats.getInstance().sendMessage("tracking-player", sender, new SGStats.Placeholder("{player}", args[0]));
        return false;
    }
}
