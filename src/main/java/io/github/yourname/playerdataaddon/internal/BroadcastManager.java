package io.github.yourname.playerdataaddon.internal;

import io.github.yourname.playerdataaddon.PlayerDataAddon;
import io.github.yourname.playerdataaddon.api.PlayerDataKey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class BroadcastManager {
    private final PlayerDataAddon plugin;

    public BroadcastManager(PlayerDataAddon plugin) {
        this.plugin = plugin;
    }

    public void broadcast(Player player, PlayerDataKey key, String oldValue, String newValue) {
        if (!plugin.getConfig().getBoolean("broadcast.enabled", true)) {
            return;
        }

        List<String> messages = plugin.getConfig().getStringList("broadcast.messages");
        if (messages.isEmpty()) {
            return;
        }

        String template = messages.get(ThreadLocalRandom.current().nextInt(messages.size()));
        String message = template
            .replace("%player%", player.getName())
            .replace("%player_id%", player.getUniqueId().toString())
            .replace("%variable%", key.getId())
            .replace("%old%", printable(oldValue))
            .replace("%new%", printable(newValue));

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private String printable(String value) {
        return value == null ? "未设置" : value;
    }
}
