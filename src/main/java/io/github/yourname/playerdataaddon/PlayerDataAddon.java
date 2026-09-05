package io.github.yourname.playerdataaddon;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.yourname.playerdataaddon.api.PlayerDataApi;
import io.github.yourname.playerdataaddon.internal.PlayerDataService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataAddon extends JavaPlugin implements SlimefunAddon {
    private PlayerDataApi api;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        api = new PlayerDataService(this);
        Bukkit.getServicesManager().register(PlayerDataApi.class, api, this, ServicePriority.Normal);

        getLogger().info("PlayerDataAddon enabled. PlayerDataApi registered.");
        registerSlimefunContent();
    }

    @Override
    public void onDisable() {
        Bukkit.getServicesManager().unregisterAll(this);
        api = null;
    }

    private void registerSlimefunContent() {
        // Register Slimefun items/machines here.
        // Always modify player values through api.setData1(...) / api.setData2(...).
    }

    public PlayerDataApi getApi() {
        return api;
    }

    @Override
    public String getBugTrackerURL() {
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
}
