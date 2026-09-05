package io.github.yourname.playerdataaddon.api.event;

import io.github.yourname.playerdataaddon.api.PlayerDataKey;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class PlayerDataChangeEvent<T> extends PlayerEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    private final PlayerDataKey key;
    private final T oldValue;
    private final T newValue;

    public PlayerDataChangeEvent(Player player, PlayerDataKey key, T oldValue, T newValue) {
        super(player);
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public PlayerDataKey getKey() {
        return key;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
