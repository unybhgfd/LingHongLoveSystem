package io.github.yourname.playerdataaddon.api;

import org.bukkit.entity.Player;

public interface PlayerDataApi {
    String get(Player player, PlayerDataKey key);
    boolean set(Player player, PlayerDataKey key, String value);
    boolean clear(Player player, PlayerDataKey key);

    default String getData1(Player player) {
        return get(player, PlayerDataKey.DATA1);
    }

    default String getData2(Player player) {
        return get(player, PlayerDataKey.DATA2);
    }

    default boolean setData1(Player player, String value) {
        return set(player, PlayerDataKey.DATA1, value);
    }

    default boolean setData2(Player player, String value) {
        return set(player, PlayerDataKey.DATA2, value);
    }

    default boolean clearData1(Player player) {
        return clear(player, PlayerDataKey.DATA1);
    }

    default boolean clearData2(Player player) {
        return clear(player, PlayerDataKey.DATA2);
    }
}
