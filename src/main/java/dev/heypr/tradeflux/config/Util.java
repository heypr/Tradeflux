package dev.heypr.tradeflux.config;

import dev.heypr.tradeflux.Tradeflux;
import dev.heypr.tradeflux.scanner.VillagerScanner;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Util {

    Tradeflux plugin = Tradeflux.getInstance();
    VillagerScanner scanner = plugin.getScanner();
    FileConfiguration config = plugin.getConfig();

    public void reload() {
        Bukkit.getAsyncScheduler().runNow(plugin, (task) -> {
            plugin.reloadConfig();
            scanner.cancelTaskTimers();
            scanner.setVillagerBrainTaskLoopTime(getBrainTaskTime());
            scanner.setVillagerRestockingTaskLoopTime(getRestockingTaskTime());
            scanner.startTaskTimer();
        });
    }

    public int getBrainTaskTime() {
        return config.getInt("brain-task-time");
    }

    public int getRestockingTaskTime() {
        return config.getInt("restocking-task-time");
    }
}
