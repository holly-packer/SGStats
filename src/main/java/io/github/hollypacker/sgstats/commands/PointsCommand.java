package io.github.hollypacker.sgstats.commands;

import io.github.hollypacker.sgstats.LeaderboardManager;
import io.github.hollypacker.sgstats.SGStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PointsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args) {
        // /points
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot use the command without arguments in the console.");
                return true;
            }
            SGStats.getInstance().sendMessage("points-self", sender, new SGStats.Placeholder("{points}",
                    LeaderboardManager.getInstance().getPoints(sender.getName())));
            return true;
        }

        // /points Notch
        if (!sender.hasPermission("sgstats.points.others")) {
            SGStats.getInstance().sendMessage("no-permission", sender);
            return true;
        }

        int points = LeaderboardManager.getInstance().getPoints(args[0]);
        SGStats.getInstance().sendMessage("points-other", sender, new SGStats.Placeholder("{player}", args[0]),
                new SGStats.Placeholder("{points}", points));
        return true;
    }
}
