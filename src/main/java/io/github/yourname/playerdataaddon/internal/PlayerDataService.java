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
    private final Map<PlayerDataKey, NamespacedKey> keys = new EnumMap<PlayerDataKey, NamespacedKey>(PlayerDataKey.class);

    public PlayerDataService(PlayerDataAddon plugin) {
        for (PlayerDataKey key : PlayerDataKey.values()) {
            keys.put(key, new NamespacedKey(plugin, key.getId()));
        }
    }

    @Override
    public <T> T get(Player player, PlayerDataKey key, PersistentDataType<T, T> type, T defaultValue) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(defaultValue, "defaultValue");
        T value = container(player).get(namespacedKey(key), type);
        return value != null ? value : defaultValue;
    }

    @Override
    public <T> boolean set(Player player, PlayerDataKey key, T value, PersistentDataType<T, T> type) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        ensureMainThread();

        PersistentDataContainer container = container(player);
        T oldValue = container.get(namespacedKey(key), type);

        if (Objects.equals(oldValue, value)) {
            return false;
        }

        container.set(namespacedKey(key), type, value);
        Bukkit.getPluginManager().callEvent(new PlayerDataChangeEvent<>(player, key, oldValue, value));
        return true;
    }

    @Override
    public <T> boolean clear(Player player, PlayerDataKey key, PersistentDataType<T, T> type) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(key, "key");
        ensureMainThread();

        PersistentDataContainer container = container(player);
        T oldValue = container.get(namespacedKey(key), type);

        if (oldValue == null) {
            return false;
        }

        container.remove(namespacedKey(key));
        Bukkit.getPluginManager().callEvent(new PlayerDataChangeEvent<>(player, key, oldValue, null));
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
