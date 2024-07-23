package dev.heypr.tradeflux.config;

import dev.heypr.tradeflux.Tradeflux;
import dev.heypr.tradeflux.scanner.VillagerScanner;
import org.bukkit.Bukkit;

public class Util {

    Tradeflux plugin = Tradeflux.getInstance();
    VillagerScanner scanner = plugin.getScanner();

    public void save() {
        Bukkit.getAsyncScheduler().runNow(plugin, (task) -> {
            plugin.saveConfig();
        });
    }

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
        return plugin.getConfig().getInt("brain-task-time");
    }

    public int getRestockingTaskTime() {
        return plugin.getConfig().getInt("restocking-task-time");
    }
}
