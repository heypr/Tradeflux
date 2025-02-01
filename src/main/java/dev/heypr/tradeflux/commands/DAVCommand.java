package dev.heypr.tradeflux.commands;

import dev.heypr.tradeflux.Tradeflux;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class DAVCommand implements CommandExecutor {

    private final Tradeflux plugin;

    public DAVCommand(Tradeflux plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        plugin.getScanner().cancelTaskTimers();
        int simulationDistance = Bukkit.getServer().getSimulationDistance();
        int scanRadius = simulationDistance * 8;
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    for (Villager villager : player.getWorld().getEntitiesByClass(Villager.class)) {
                        if (villager.getLocation().distance(player.getLocation()) <= scanRadius && villager.getProfession() != Villager.Profession.NONE && !villager.isAware()) {
                            villager.setAware(false);
                        }
                    }
                }
            }
        };
        task.run();
        return true;
    }
}
