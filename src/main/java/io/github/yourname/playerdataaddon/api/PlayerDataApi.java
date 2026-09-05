package io.github.yourname.playerdataaddon.api;

import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public interface PlayerDataApi {
    <T> T get(Player player, PlayerDataKey key, PersistentDataType<T, T> type, T defaultValue);
    <T> boolean set(Player player, PlayerDataKey key, T value, PersistentDataType<T, T> type);
    <T> boolean clear(Player player, PlayerDataKey key, PersistentDataType<T, T> type);

    default short getHValue(Player player) {
        return get(player, PlayerDataKey.HValue, PersistentDataType.SHORT, (short) 0);
    }

    default void addHValue(Player player, short value) {
        short currentValue = getHValue(player);
        currentValue += value;
        currentValue = (short) Math.max(Math.min(currentValue, 0), 1000);
        set(player, PlayerDataKey.HValue, currentValue, PersistentDataType.SHORT);
    }
}
