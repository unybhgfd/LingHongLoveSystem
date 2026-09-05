package io.github.yourname.playerdataaddon.internal;

import io.github.yourname.playerdataaddon.PlayerDataAddon;
import io.github.yourname.playerdataaddon.api.PlayerDataApi;
import io.github.yourname.playerdataaddon.api.PlayerDataKey;
import io.github.yourname.playerdataaddon.api.event.PlayerDataChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class PlayerDataService implements PlayerDataApi {
    private final BroadcastManager broadcastManager;
    private final Map<PlayerDataKey, NamespacedKey> keys = new EnumMap<PlayerDataKey, NamespacedKey>(PlayerDataKey.class);

    public PlayerDataService(PlayerDataAddon plugin) {
        this.broadcastManager = new BroadcastManager(plugin);
        for (PlayerDataKey key : PlayerDataKey.values()) {
            keys.put(key, new NamespacedKey(plugin, key.getId()));
        }
    }

    @Override
    public String get(Player player, PlayerDataKey key) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(key, "key");
        return container(player).get(namespacedKey(key), PersistentDataType.STRING);
    }

    @Override
    public boolean set(Player player, PlayerDataKey key, String value) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        ensureMainThread();

        PersistentDataContainer container = container(player);
        String oldValue = container.get(namespacedKey(key), PersistentDataType.STRING);

        if (Objects.equals(oldValue, value)) {
            return false;
        }

        container.set(namespacedKey(key), PersistentDataType.STRING, value);
        Bukkit.getPluginManager().callEvent(new PlayerDataChangeEvent(player, key, oldValue, value));
        broadcastManager.broadcast(player, key, oldValue, value);
        return true;
    }

    @Override
    public boolean clear(Player player, PlayerDataKey key) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(key, "key");
        ensureMainThread();

        PersistentDataContainer container = container(player);
        String oldValue = container.get(namespacedKey(key), PersistentDataType.STRING);

        if (oldValue == null) {
            return false;
        }

        container.remove(namespacedKey(key));
        Bukkit.getPluginManager().callEvent(new PlayerDataChangeEvent(player, key, oldValue, null));
        broadcastManager.broadcast(player, key, oldValue, null);
        return true;
    }

    private PersistentDataContainer container(Player player) {
        return player.getPersistentDataContainer();
    }

    private NamespacedKey namespacedKey(PlayerDataKey key) {
        return keys.get(key);
    }

    private void ensureMainThread() {
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("PlayerDataApi must be called from the Bukkit server thread");
        }
    }
}
