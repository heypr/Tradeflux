package dev.heypr.tradeflux.scanner;

import dev.heypr.tradeflux.Tradeflux;
import dev.heypr.tradeflux.enums.VillagerProfessionBlocks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class VillagerScanner {

    private final Tradeflux plugin;

    private int villagerBrainTaskId;
    private int villagerBrainTaskLoopTime;
    private int villagerRestockingTaskId;
    private int villagerRestockingTaskLoopTime;

    public VillagerScanner(Tradeflux plugin) {
        this.plugin = plugin;
    }

    public void startTaskTimer() {
        int simulationDistance = Bukkit.getServer().getSimulationDistance();
        int scanRadius = simulationDistance * 4;

        villagerBrainTaskId = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    for (Villager villager : player.getWorld().getEntitiesByClass(Villager.class)) {
                        if (villager.getLocation().distance(player.getLocation()) <= scanRadius && villager.getProfession() != Villager.Profession.NONE) {
                            if (villager.getMemory(MemoryKey.JOB_SITE) != null && villager.getMemory(MemoryKey.JOB_SITE).getBlock().getType() == VillagerProfessionBlocks.valueOf(villager.getProfession().name()).getBlock()) {
                                villager.setAware(false);
                            }
                            else {
                                villager.setAware(true);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, villagerBrainTaskLoopTime).getTaskId();

        villagerRestockingTaskId = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    for (Villager villager : player.getWorld().getEntitiesByClass(Villager.class)) {
                        if (villager.getLocation().distance(player.getLocation()) <= scanRadius && villager.getProfession() != Villager.Profession.NONE && villager.getMemory(MemoryKey.JOB_SITE) != null && !villager.isAware()) {
                            villager.setRestocksToday(0);
                            for (final MerchantRecipe recipe : villager.getRecipes()) {
                                if (recipe.getUses() > 0) {
                                    recipe.setUses(0);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, villagerRestockingTaskLoopTime).getTaskId();
    }

    public void cancelTaskTimers() {
        if (villagerBrainTaskId != -1) {
            Bukkit.getScheduler().cancelTask(villagerBrainTaskId);
            villagerBrainTaskId = -1;
        }
        if (villagerRestockingTaskId != -1) {
            Bukkit.getScheduler().cancelTask(villagerRestockingTaskId);
            villagerRestockingTaskId = -1;
        }
    }

    public void setVillagerBrainTaskLoopTime(int villagerBrainTaskLoopTime) {
        // Converts ticks to minutes.
        this.villagerBrainTaskLoopTime = (villagerBrainTaskLoopTime * 20);
    }

    public void setVillagerRestockingTaskLoopTime(int villagerRestockingTaskLoopTime) {
        // Converts ticks to minutes.
        this.villagerRestockingTaskLoopTime = (villagerRestockingTaskLoopTime * 20);
    }

    public int getVillagerBrainTaskLoopTime() {
        return villagerBrainTaskLoopTime;
    }

    public int getVillagerRestockingTaskLoopTime() {
        return villagerRestockingTaskLoopTime;
    }
}
