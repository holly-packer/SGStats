package io.github.hollypacker.sgstats;

import org.bukkit.plugin.java.JavaPlugin;

public final class SGStats extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SGStats getInstance() {
        return instance;
    }

    private void setupConfig() {
        getConfig().addDefault("points-per-kill", 3);
        getConfig().addDefault("prefix", "<bold><#9af20c>MCPH <#0d0d0d>»</bold> ");
        getConfig().addDefault("broadcast-points", true);
        getConfig().addDefault("messages.add-point", "<#939393>Player <#9af20c>{player} <#939393>has obtained " +
                "<#9af20c>{points} <#939393>points!");
        getConfig().addDefault("messages.points-self", "<#939393>You have <#9af20c>{points} <#939393>points!");
        getConfig().addDefault("messages.points-other", "<#9af20c>{player} <#939393>has <#9af20c>{points} " +
                "<#939393>points!");
        getConfig().addDefault("messages.specify-name", "<#939393>Please specify a player name.");
        getConfig().addDefault("messages.tracking-player", "<#939393>Started tracking <#9af20c>{player}<#939393>!");
        getConfig().addDefault("messages.no-permission", "<#939393>You do not have permission to do this.");
        getConfig().addDefault("messages.scoreboard-title", "<bold><#9af20c>MC<white>ProHosting</bold>");
        getConfig().addDefault("messages.stop-tracking", "<#939393>Stopped tracking <#9af20c>{player}<#939393>!");
        getConfig().addDefault("messages.reloaded-config", "<#939393>Reloaded the plugin config!");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void setupCommands() {
        getCommand("points").setExecutor(new PointsCommand());
        getCommand("statsreload").setExecutor(new ReloadCommand());
        getCommand("stoptrack").setExecutor(new StopTrackingCommand());
        getCommand("track").setExecutor(new TrackCommand());
    }

    private void setupListeners() {
        getServer().getPluginManager().registerEvents(new KillListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public Component getPrefix() {
        return MiniMessage.miniMessage().deserialize(getConfig().getString("prefix"));
    }

    public void sendMessage(String key, CommandSender sender, Placeholder... placeholders) {
        String message = getConfig().getString("messages." + key);
        if (message == null) throw new NullPointerException("Message key " + key + " is null.");
        for (Placeholder placeholder : placeholders) {
            message = message.replaceAll(placeholder.name, String.valueOf(placeholder.replacement));
        }
        Component parsedMessage = MiniMessage.miniMessage().deserialize(message);
        sender.sendMessage(getPrefix().append(parsedMessage));
    }

    public record Placeholder(String name, Object replacement) {
    }
}
