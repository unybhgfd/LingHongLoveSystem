# PlayerDataAddon

A minimal Slimefun addon core based on the official Addon-Template structure.

## Features

- Persistent per-player `data1` and `data2` values using Bukkit PDC
- Broadcasts a random configurable chat message whenever a value actually changes
- Public Bukkit ServicesManager API for other plugins
- `PlayerDataChangeEvent` for listeners
- No duplicate broadcast when setting the same value twice

## Build

```bash
mvn clean package
```

The jar will be generated in `target/`.

## Hook from another plugin

Add `PlayerDataAddon` as a dependency or soft-dependency, then obtain the service:

```java
RegisteredServiceProvider<PlayerDataApi> registration =
    Bukkit.getServicesManager().getRegistration(PlayerDataApi.class);

if (registration != null) {
    PlayerDataApi api = registration.getProvider();
    api.setData1(player, "123");
    api.setData2(player, "hello");
}
```

Listen for changes:

```java
@EventHandler
public void onPlayerDataChange(PlayerDataChangeEvent event) {
    getLogger().info(event.getPlayer().getName()
        + " changed " + event.getKey().getId()
        + " from " + event.getOldValue()
        + " to " + event.getNewValue());
}
```

## Placeholders

Broadcast messages support:

- `%player%`
- `%player_id%`
- `%variable%`
- `%old%`
- `%new%`

## Before publishing

Replace `io.github.yourname` / `yourname` with your own package and author information.
